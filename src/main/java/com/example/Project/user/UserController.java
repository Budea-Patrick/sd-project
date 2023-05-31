package com.example.Project.user;


import com.example.Project.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Pair<?, HttpStatus> pair = userService.login(user);
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        Pair<?, HttpStatus> pair = userService.register(user);
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody Long userId) {
        Pair<?, HttpStatus> pair = userService.logout(userId);
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @GetMapping("/list")
    public ResponseEntity<?> getOnlineUsers() {
        Pair<?, HttpStatus> pair = userService.findAllOnlineUsers();
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(@RequestBody Message message) {
        Pair<?, HttpStatus> pair = userService.sendMessage(message);
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }
}
