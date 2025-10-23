package org.example.dto;

import com.julienvey.trello.domain.Card;
import com.julienvey.trello.domain.CheckItem;
import com.julienvey.trello.domain.CheckList;

import java.util.List;

public class CheckListDto {
    private String id;
    private String name;
    private String idBoard;
    private String idCard;
    private int position;
    private List<CheckItemDto> checkItems;
    private List<Card> cards;
    private long pos;

    public CheckList createCheckList() {
        CheckList checkList = new CheckList();
        checkList.setId(id);
        checkList.setName(name);
        checkList.setIdBoard(idBoard);
        checkList.setIdCard(idCard);
        checkList.setPosition(position);
        checkList.setCheckItems(checkItems.stream().map(CheckItemDto::createCheckItem).toList());
        checkList.setCards(cards);
        checkList.setPos(0);
        return checkList;
    }
}
