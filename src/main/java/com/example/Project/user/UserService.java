package com.example.Project.user;

import com.example.Project.attachment.FileAttachment;
import com.example.Project.attachment.FileAttachmentRepository;
import com.example.Project.message.Message;
import com.example.Project.message.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final FileAttachmentRepository fileAttachmentRepository;
    private final MessageRepository messageRepository;

    @Autowired
    public UserService(UserRepository userRepository, FileAttachmentRepository fileAttachmentRepository, MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.fileAttachmentRepository = fileAttachmentRepository;
        this.messageRepository = messageRepository;
    }

    public Boolean checkPasswordMatch(String password, String passwordHash) {
        return Base64.getEncoder().encodeToString(password.getBytes()).equals(passwordHash);
    }

    public Pair<?, HttpStatus> login(User user) {
        User foundUser = userRepository.findUserByUsername(user.getUsername());
        if(foundUser == null || !checkPasswordMatch(user.getPassword(), foundUser.getPassword())) {
            return Pair.of("Username or password incorrect", HttpStatus.BAD_REQUEST);
        }
        if(foundUser.isOnline()) {
            return Pair.of("User already logged in.", HttpStatus.BAD_REQUEST);
        }

        foundUser.setOnline(Boolean.TRUE);
        userRepository.save(foundUser);
        return Pair.of(foundUser, HttpStatus.OK);
    }

    public Pair<String, HttpStatus> logout(Long userId) {
        User foundUser = userRepository.findUserById(userId);
        foundUser.setOnline(Boolean.FALSE);
        userRepository.save(foundUser);
        return Pair.of("You have successfully logged out", HttpStatus.OK);
    }

    public Pair<?, HttpStatus> register(User user) {
        User newUser = new User(user.getUsername(), user.getPassword());
        newUser.setOnline(Boolean.TRUE);
        userRepository.save(newUser);
        return Pair.of(newUser, HttpStatus.OK);
    }

    public Pair<?, HttpStatus> findAllOnlineUsers() {
        List<User> onlineUsers = userRepository.findAllByOnline(Boolean.TRUE);
        return Pair.of(onlineUsers, HttpStatus.OK);
    }

    public Pair<?, HttpStatus> sendMessage(Message message) {
        User sender = userRepository.findUserById(message.getSenderId());
        User receiver = userRepository.findUserByUsername(message.getReceiverName());

        if (sender == null || receiver == null) {
            return Pair.of("Invalid sender or receiver", HttpStatus.BAD_REQUEST);
        }

        FileAttachment fileAttachment = null;
        if (message.getFileAttachment() != null) {
            fileAttachment = message.getFileAttachment();
            fileAttachmentRepository.save(fileAttachment);
        }

        message.setSenderId(sender.getId());
        message.setReceiverName(receiver.getUsername());
        message.setFileAttachment(fileAttachment);
        messageRepository.save(message);

        return Pair.of(message, HttpStatus.OK);
    }


}
