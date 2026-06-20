package org.example.presentation.api.task_manager;

import com.google.gson.reflect.TypeToken;
import com.julienvey.trello.Trello;
import com.julienvey.trello.domain.Board;
import com.julienvey.trello.domain.Card;
import com.julienvey.trello.domain.CheckList;
import com.julienvey.trello.domain.TList;
import com.julienvey.trello.impl.TrelloImpl;
import com.julienvey.trello.impl.http.ApacheHttpClient;
import org.example.dto.CardDto;
import org.example.dto.CheckListDto;
import org.example.presentation.api.HttpClient;
import org.example.util.PropertiesUtil;

import java.util.List;
import java.util.Map;

public class TrelloApiClient implements TaskManagerApiClient {
    private final Trello TRELLO;
    private final String TRELLO_KEY;
    private final String TRELLO_TOKEN;
    private final String BASE_URL = "https://api.trello.com/1";
    private final HttpClient httpClient;

    public TrelloApiClient(HttpClient httpClient) {
        TRELLO_KEY = PropertiesUtil.getProperty("trelloKey");
        TRELLO_TOKEN = PropertiesUtil.getProperty("trelloToken");
        TRELLO = new TrelloImpl(TRELLO_KEY, TRELLO_TOKEN, new ApacheHttpClient());
        this.httpClient = httpClient;
    }

    @Override
    public Card createTask(Card card, String listId) {
        return TRELLO.createCard(listId, card);
    }

    @Override
    public Board getBoard(String id) {
        return TRELLO.getBoard(id);
    }

    @Override
    public CheckList getCheckList(String id) {
        CheckListDto dto = httpClient.get(
                BASE_URL + "/checklists/" + id,
                CheckListDto.class,
                Map.of(
                        "key", TRELLO_KEY,
                        "token", TRELLO_TOKEN
                )
        );
        return dto.createCheckList();
    }

    @Override
    public List<Card> getCards(String listId) {
        List<CardDto> dtos = httpClient.get(
                BASE_URL + "/lists/" + listId + "/cards",
                new TypeToken<List<Card>>() {
                }.getType(),
                Map.of(
                        "key", TRELLO_KEY,
                        "token", TRELLO_TOKEN
                )
        );

        return dtos.stream()
                .map(CardDto::createCard)
                .toList();
    }

    @Override
    public TList getList(String listName, String boardId) {
        Board board = getBoard(boardId);
        List<TList> lists = board.fetchLists();
        TList list = lists.stream()
                .filter(tList -> tList.getName().equals(listName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("TList not found with name = " + listName));

        list.setCards(getCards(list.getId()));
        list.setInternalTrello(TRELLO);

        return list;
    }

    @Override
    public TList getList(String id) {
        TList list = TRELLO.getList(id);
        list.setCards(getCards(id));
        return list;
    }
}
