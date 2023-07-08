package com.example.telegrambot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RssReaderConfig {
    public static final String FEED_URL = "https://lifehacker.com/rss";
}
