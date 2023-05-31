package com.example.Project.message;

import com.example.Project.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Pair<?, HttpStatus> getMessagesForUser(String user) {
        List<Message> foundMessages = messageRepository.findAllByReceiverName(user);
        return Pair.of(foundMessages, HttpStatus.OK);
    }

    public Pair<?, HttpStatus> getMessagesBetweenUsers(String receiver, Long senderId) {
        List<Message> foundMessages = messageRepository.findAllByReceiverNameAndSenderId(receiver, senderId);
        return Pair.of(foundMessages, HttpStatus.OK);
    }
}
