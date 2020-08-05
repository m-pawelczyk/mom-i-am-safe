package pro.pawelczyk.miascore.controllers;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.pawelczyk.miascore.model.User;
import pro.pawelczyk.miascore.repositories.UserRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * m-pawelczyk (GitGub) / m_pawelczyk (Twitter)
 * on 20.07.2020
 * created FakeUserConfigController in pro.pawelczyk.miascore.controllers
 * in project mias-core
 */
@Slf4j
@RestController
@RequestMapping(FakeUserConfigController.BASE_URL)
public class FakeUserConfigController {

    public static final String BASE_URL = "/fake/user";

    private final UserRepository userRepository;

    public FakeUserConfigController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/list")
    Flux<User> userslist(){
        return userRepository.findAll();
    }

    @GetMapping("/list2")
    Flux<String> users222list(){
        return userRepository.findAll().map(e -> e.getId().toString() + " ");
    }

    @GetMapping("/list/{id}")
    Mono<User> userById(@PathVariable String id) {
        return userRepository.findById(id);
    }
}
