package com.example.Project.message;

import com.example.Project.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    public List<Message> findAllByReceiverName(String receiverName);

    public List<Message> findAllByReceiverNameAndSenderId(String receiverName, Long senderId);

}
