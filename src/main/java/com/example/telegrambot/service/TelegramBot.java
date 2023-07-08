package com.example.telegrambot.service;

import com.example.telegrambot.config.BotConfig;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component
public class TelegramBot extends TelegramLongPollingBot implements TelegramBotInterface{

    final BotConfig config;
    final RssReaderService rssReaderService;

    public TelegramBot(BotConfig config, RssReaderService rssReaderService) {
        this.config = config;
        this.rssReaderService = rssReaderService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            switch (text) {
                case "/start":
                    startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    break;
                default:
                    sendMessage(chatId, "Sorry, command was not recognized!");
            }
        }
    }

    public void startCommandReceived(long chatId, String firstName) {
        String answer = "Hi, " + firstName + " here is last news:";
        sendMessage(chatId, answer);
    }

    public void sendMessage(Long chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        List<String> feed = rssReaderService.readRssFeed();
        sendMessage.setText(feed.get(0));
        try {
            execute(sendMessage);
        } catch (TelegramApiException ex) {

        }
    }

    @Override
    public String getBotUsername() {return config.getBotName();}

    @Override
    public String getBotToken() {
        return config.getToken();
    }
}
