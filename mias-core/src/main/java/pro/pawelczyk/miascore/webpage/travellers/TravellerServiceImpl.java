package pro.pawelczyk.miascore.webpage.travellers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pro.pawelczyk.miascore.messageprocessor.model.User;
import pro.pawelczyk.miascore.messageprocessor.repositories.UserRepository;
import reactor.core.publisher.Flux;

@AllArgsConstructor
@Service
public class TravellerServiceImpl implements TravellerService {

    private final UserRepository userRepository;

    @Override
    public Flux<User> getAllTravellers() {
        return userRepository.findAll();
    }
}

