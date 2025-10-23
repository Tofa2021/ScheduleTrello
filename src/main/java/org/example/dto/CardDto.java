package org.example.dto;

import com.julienvey.trello.domain.Badges;
import com.julienvey.trello.domain.Card;
import com.julienvey.trello.domain.Label;

import java.util.Date;
import java.util.List;

public class CardDto {
    private String id;
    private String name;
    private String idList;
    private String desc;
    private String url;
    private Date due;
    private List<String> idMembers;
    private List<Label> labels;
    private Badges badges;
    private List<Card.CardCheckItem> checkItemStates;
    private boolean closed;
    private Date dateLastActivity;
    private String idBoard;
    private List<String> idChecklists;
    private List<String> idMembersVoted;
    private String idShort;
    private String idAttachmentCover;
    private boolean manualCoverAttachment;
    private double pos;
    private String shortLink;
    private String shortUrl;
    private boolean subscribed;

    public Card createCard() {
        Card card = new Card();
        card.setId(id);
        card.setName(name);
        card.setIdList(idList);
        card.setDesc(desc);
        card.setUrl(url);
        card.setDue(due);
        card.setIdMembers(idMembers);
        card.setLabels(labels);
        card.setBadges(badges);
        card.setCheckItemStates(checkItemStates);
        card.setClosed(closed);
        card.setDateLastActivity(dateLastActivity);
        card.setIdBoard(idBoard);
        card.setIdChecklists(idChecklists);
        card.setIdMembersVoted(idMembersVoted);
        card.setIdShort(idShort);
        card.setIdAttachmentCover(idAttachmentCover);
        card.setManualCoverAttachment(manualCoverAttachment);
        card.setPos(0); //
        card.setShortLink(shortLink);
        card.setShortUrl(shortUrl);
        card.setSubscribed(subscribed);
        return card;
    }
}
