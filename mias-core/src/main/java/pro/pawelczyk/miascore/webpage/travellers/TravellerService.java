package pro.pawelczyk.miascore.webpage.travellers;

import pro.pawelczyk.miascore.messageprocessor.model.User;
import reactor.core.publisher.Flux;

public interface TravellerService {

    Flux<User> getAllTravellers();
}
