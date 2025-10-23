package org.example.dto;

import com.julienvey.trello.domain.CheckItem;
import com.julienvey.trello.domain.CheckList;

public class CheckItemDto {
    private String state;
    private String id;
    private String name;
    private long pos;

    public CheckItem createCheckItem() {
        CheckItem checkItem = new CheckItem();
        checkItem.setState(state);
        checkItem.setId(id);
        checkItem.setName(name);
        checkItem.setPos(0);
        return checkItem;
    }
}
