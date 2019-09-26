package pl.inyourface.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.inyourface.model.User;
import pl.inyourface.service.UserService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/users")
    public ResponseEntity getUsers() throws JsonProcessingException {
        return userService.getUsers();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/users")
    public ResponseEntity addUser(@Valid @RequestBody final User user) {
        return userService.addUser(user);
    }
}
