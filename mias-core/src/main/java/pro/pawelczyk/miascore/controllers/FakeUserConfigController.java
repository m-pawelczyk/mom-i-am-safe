package pro.pawelczyk.miascore.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pro.pawelczyk.miascore.model.Trip;
import pro.pawelczyk.miascore.model.User;
import pro.pawelczyk.miascore.repositories.TripRepository;
import pro.pawelczyk.miascore.repositories.UserRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;

import static java.util.Comparator.comparing;
import static org.springframework.data.domain.Sort.Direction.ASC;

/**
 * m-pawelczyk (GitGub) / m_pawelczyk (Twitter)
 * on 20.07.2020
 * created FakeUserConfigController in pro.pawelczyk.miascore.controllers
 * in project mias-core
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(FakeUserConfigController.BASE_URL)
public class FakeUserConfigController {
    /**
     * This Controller is made for test purposes ;) It will be deleted. Please remember about it.
     * Thats why I haven't created new service layer etc.
     */

    public static final String BASE_URL = "/fake/user";

    private final UserRepository userRepository;
    private final TripRepository tripRepository;

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    Flux<User> userlist(@RequestParam(required = false, defaultValue = "0") int page,
                        @RequestParam(required = false, defaultValue = "10") int size) {

        if(size > 5) {
            size = 5;
        }

        log.debug("userlist with page: " + page + " size: " + size);
        // findAll() does not support paging. I would like to make hack for it.
        return userRepository.findByIdNotNull(PageRequest.of(page, size, Sort.by("twitterAccount")));
    }


    @GetMapping("/list/{id}")
    @ResponseStatus(HttpStatus.OK)
    Mono<User> userlistById(@PathVariable String id) {
        return userRepository.findById(id);
    }

    @PatchMapping("/update/{id}/twitterAccount/{twitterAccount}")
    Mono<User> patchUserTwitterAccount(@PathVariable String id, @PathVariable String twitterAccount) {
        Mono<User> user = userRepository.findById(id).map(e -> {
            e.setTwitterAccount(twitterAccount);
            userRepository.save(e).subscribe();
            return e;
        });
        return user;
    }

    @PatchMapping("/update/{id}/phoneNumber/{phoneNumber}")
    Mono<User> patchUserPhoneNumber(@PathVariable String id, @PathVariable String phoneNumber) {
        Mono<User> user = userRepository.findById(id).map(e -> {
            e.setPhoneNumber(phoneNumber);
            userRepository.save(e).subscribe();
            return e;
        });
        return user;
    }


    @PostMapping("/create/{id}/newTrip")
    Mono<User> addNewTripToUserAndSetItAsCurrent(@PathVariable String id, @RequestParam String tripName) {
        Mono<User> user = userRepository.findById(id).map(e -> {
            Trip trip = new Trip();
            trip.setName(tripName);
            trip.setOwnerId(e.getId());
            tripRepository.save(trip).subscribe(e2 -> {
                log.info("tripRepository.save(trip) " + e2.getId());
                e.setTripId(e2.getId());
                userRepository.save(e).subscribe(e4 -> {
                    log.info("userRepository.save" + e4);
                });
            });
            return e;
        });
        return user;
    }
}
