package pl.inyourface.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.inyourface.model.User;
import pl.inyourface.repository.UserRepository;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/users")
    public ResponseEntity getUsers() throws JsonProcessingException {
        final List<User> users = userRepository.findAll();
        return ResponseEntity.ok(objectMapper.writeValueAsString(users));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/users")
    public ResponseEntity addUser(@RequestBody final User user) {
        final Optional<User> userFromDb = userRepository.findByUsername(user.getUsername());

        if (userFromDb.isPresent()) {
            log.error("User " + user.getUsername() + " already exist in database");
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
        final User savedUser = userRepository.save(user);
        log.info("User " + user.getUsername() + " successfully created");
        return ResponseEntity.ok(savedUser);
    }
}
