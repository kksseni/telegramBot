package com.example.telegrambot;

import com.example.telegrambot.config.RssReaderConfig;
import com.example.telegrambot.model.Feed;
import com.example.telegrambot.service.RssReaderService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TelegramBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(TelegramBotApplication.class, args);
    }

}
