package org.example;

import com.julienvey.trello.Trello;
import com.julienvey.trello.domain.Card;
import com.julienvey.trello.domain.TList;
import com.julienvey.trello.impl.TrelloImpl;
import com.julienvey.trello.impl.http.ApacheHttpClient;
import org.example.model.Lesson;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.ZoneId;
import java.util.Date;
import java.util.Properties;

public class TrelloService {
    private final Trello trelloApi;

    public TrelloService() throws IOException {
        Properties properties = new Properties();
        try (InputStream input = Main.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                throw new FileNotFoundException("application.properties not found in classpath");
            }
            properties.load(input);
        }

        trelloApi = new TrelloImpl(properties.getProperty("trelloKey"), properties.getProperty("trelloToken"), new ApacheHttpClient());
    }

    public Card createLabCard(Lesson lesson, String subjectName, int labNumber, String listId) {
        String name = "Лаб" + labNumber + " (" + subjectName + ")";
        Date due = Date.from(lesson.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant());

        Card card = new Card();
        card.setName(name);
        card.setDue(due);

        return trelloApi.createCard(listId, card);
    }
}
