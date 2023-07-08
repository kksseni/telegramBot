package com.example.telegrambot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RssReaderConfig {
    public static final String FEED_URL = "https://lifehacker.com/rss";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String CHANNEL = "channel";
    public static final String LANGUAGE = "language";
    public static final String COPYRIGHT = "copyright";
    public static final String LINK = "link";
    public static final String AUTHOR = "author";
    public static final String ITEM = "item";
    public static final String PUB_DATE = "pubDate";
    public static final String GUID = "guid";
}
