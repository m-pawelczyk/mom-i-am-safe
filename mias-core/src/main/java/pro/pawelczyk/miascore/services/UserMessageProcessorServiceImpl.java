package pro.pawelczyk.miascore.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.pawelczyk.miascore.model.Position;
import pro.pawelczyk.miascore.model.User;
import pro.pawelczyk.miascore.repositories.PositionRepository;
import pro.pawelczyk.miascore.repositories.TripRepository;
import pro.pawelczyk.miascore.repositories.UserRepository;
import pro.pawelczyk.miascore.valueobjects.*;
import reactor.core.publisher.Mono;

import java.time.Instant;


/**
 * m-pawelczyk (GitGub) / m_pawelczyk (Twitter)
 * on 02.07.2020
 * in project mias-core
 */
@Slf4j
@Service
public class UserMessageProcessorServiceImpl implements UserMessageProcessorService {

    private final TwitterUpdaterService twitterUpdaterService;

    private final UserRepository userRepository;
    private final PositionRepository positionRepository;
    private final TripRepository tripRepository;

    public UserMessageProcessorServiceImpl(
            TwitterUpdaterService twitterUpdaterService,
            UserRepository userRepository,
            PositionRepository positionRepository,
            TripRepository tripRepository) {
        this.twitterUpdaterService = twitterUpdaterService;
        this.userRepository = userRepository;
        this.positionRepository = positionRepository;
        this.tripRepository = tripRepository;
    }

    @Override
    public void processMessage(UserMessage userMessage) {
        if (userMessage.getSenderType() == SenderType.SMS) {
            log.info("check user in database: " + userMessage.getSenderId());
            userRepository.findByPhoneNumber(userMessage.getSenderId())
                    .switchIfEmpty(Mono.error(new IllegalArgumentException("user phone number not exist: "
                            + userMessage.getSenderId())))
                    .subscribe(
                            result -> {
                                log.info("user is found: " + result.toString());
                                ProcessedMessage processedMessage = userMessage.process();
                                updateDatabase(processedMessage, result);

                                twitterUpdaterService.sendTwitterUpdate(
                                        processedMessage.twittMessage(result.getTwitterAccount()));
                            }
                    );
        } else {
            throw new IllegalArgumentException("Other types than SMS messages are not supported, but received: "
                    + userMessage.toString());
        }
    }

    private void updateDatabase(ProcessedMessage processedMessage, User user) {
        if (processedMessage.containCoordinates()) {
            ProcessedMessageWithCord processedMessageWithCord = (ProcessedMessageWithCord) processedMessage;
            Position position = processedMessageWithCord.createPosition();

            updatePositionsAndTrips(position, user.getTripId());
            updateUsersWithPosition(user, processedMessageWithCord.getTimestamp(), position);
        } else {
            updateUsers(user, processedMessage.getTimestamp());
        }


    }

    private void updatePositionsAndTrips(Position position, String tripId) {
        positionRepository.save(position).subscribe(result -> {
            log.info("stored position in db: " + result.toString());
            updateTrips(position, tripId);
        });
    }

    private void updateTrips(Position position, String tripId) {
        if (tripId != null) {
            tripRepository.findById(tripId).subscribe(result -> {
                result.getPositions().add(position.getId());
                tripRepository.save(result).subscribe(update -> {
                    log.info("stored update trip in db: " + update.toString());
                });
            });
        }
    }

    private void updateUsersWithPosition(User user, Instant lastMessageTimestamp, Position position) {
        user.setLastMessageTimestamp(lastMessageTimestamp);
        user.setLastPosition(position);
        userRepository.save(user).subscribe(result -> {
            log.info("stored user in db: " + result.toString());
        });
    }

    private void updateUsers(User user, Instant lastMessageTimestamp) {
        user.setLastMessageTimestamp(lastMessageTimestamp);
        userRepository.save(user).subscribe(result -> {
            log.info("stored user in db: " + result.toString());
        });
    }
}
