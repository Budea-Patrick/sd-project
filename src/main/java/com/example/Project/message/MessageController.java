package com.example.Project.message;

import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/{selectedUser}")
    public ResponseEntity<?> getMessagesForUsers(@RequestBody @PathVariable String selectedUser, @RequestParam("senderId") Long senderId) {
        Pair<?, HttpStatus> pair = messageService.getMessagesBetweenUsers(selectedUser, senderId);
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }
}
