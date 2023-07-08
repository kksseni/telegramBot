package com.example.telegrambot.service;

import com.example.telegrambot.config.BotConfig;
import com.example.telegrambot.model.Feed;
import com.example.telegrambot.model.User;
import com.example.telegrambot.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot implements TelegramBotInterface{

    final BotConfig config;
    final RssReaderService rssReaderService;
    final UserService userService;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            if ("/start".equals(text)) {
                String firstName = update.getMessage().getChat().getFirstName();
                User user = User.builder()
                        .chatId(String.valueOf(chatId))
                        .userName(firstName)
                        .build();
                userService.saveUser(user);
                startCommandReceived(chatId, firstName);
            } else {
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
//        sendMessage.setParseMode("HTML");
        Feed feed = rssReaderService.readFeed();
        sendMessage.setText(feed.getMessages().get(0).getDescription());
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
