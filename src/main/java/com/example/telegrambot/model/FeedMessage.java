package com.example.telegrambot.model;

import lombok.Data;

@Data
public class FeedMessage {
    String title;
    String description;
    String link;
    String author;
    String guid;

    @Override
    public String toString() {
        return "FeedMessage [title=" + title + ", description=" + description
                + ", link=" + link + ", author=" + author + ", guid=" + guid
                + "]";
    }
}
