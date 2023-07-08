package com.example.telegrambot.service;

import com.example.telegrambot.model.User;
import com.example.telegrambot.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    final UserRepository userRepository;

    @Override
    public void saveUser(User user) {
        if (!userRepository.existsByChatId(user.getChatId())){
            userRepository.save(user);
        }
    }
}
