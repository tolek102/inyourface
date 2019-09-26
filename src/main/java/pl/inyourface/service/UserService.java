package pl.inyourface.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
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
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    public ResponseEntity getUsers() throws JsonProcessingException {
        final List<User> users = userRepository.findAll();
        log.info("allUsers: " + users);
        return ResponseEntity.ok(objectMapper.writeValueAsString(users));
    }

    public ResponseEntity addUser(final User user) {
        log.info("user: " + user.getUsername() + " " + user.getPassword());
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
