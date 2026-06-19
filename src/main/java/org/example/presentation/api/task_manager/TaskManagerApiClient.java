package org.example.presentation.api.task_manager;

import com.julienvey.trello.domain.Board;
import com.julienvey.trello.domain.Card;
import com.julienvey.trello.domain.CheckList;
import com.julienvey.trello.domain.TList;

import java.util.List;

public interface TaskManagerApiClient {
    Card createTask(Card card, String listId);

    Board getBoard(String id);

    CheckList getCheckList(String id);

    List<Card> getCards(String listId);

    TList getList(String listName, String boardId);

    TList getList(String id);
}
