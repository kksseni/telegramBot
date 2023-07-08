package com.example.telegrambot.service;


public interface TelegramBotInterface {
    void startCommandReceived(long chatId, String firstName);

    void sendMessage(Long chatId, String message);
}
