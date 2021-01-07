package pro.pawelczyk.miascore.messageprocessor.impl;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import pro.pawelczyk.miascore.messageprocessor.UserMessageProcessorService;
import pro.pawelczyk.miascore.messageprocessor.model.Position;
import pro.pawelczyk.miascore.messageprocessor.model.Trip;
import pro.pawelczyk.miascore.messageprocessor.model.User;
import pro.pawelczyk.miascore.messageprocessor.repositories.PositionRepository;
import pro.pawelczyk.miascore.messageprocessor.repositories.TripRepository;
import pro.pawelczyk.miascore.messageprocessor.repositories.UserRepository;
import pro.pawelczyk.miascore.twitterupdatter.TwitterUpdaterService;
import pro.pawelczyk.miascore.messageprocessor.ProcessedMessage;
import pro.pawelczyk.miascore.messageprocessor.ProcessedMessageWithCord;
import pro.pawelczyk.miascore.messageprocessor.SenderType;
import pro.pawelczyk.miascore.messageprocessor.UserMessage;
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

            updatePositionsAndUserAndTrips(position, user, processedMessageWithCord.getTimestamp());
        } else {
            updateUsers(user, processedMessage.getTimestamp());
        }


    }

    private void updatePositionsAndUserAndTrips(Position position, User user, Instant timestamp) {
        positionRepository.save(position).subscribe(result -> {
            log.info("stored position in db: " + result.toString());
            updateUsersWithPosition(user, timestamp, position);
            updateTrips(position, user.getTripId());
        });
    }

    private void updateUsersWithPosition(User user, Instant lastMessageTimestamp, Position position) {
        user.setLastMessageTimestamp(lastMessageTimestamp);
        user.setLastPosition(position);
        userRepository.save(user).subscribe(result -> {
            log.info("update user in db: " + result.toString());
        });
    }

    private void updateUsers(User user, Instant lastMessageTimestamp) {
        user.setLastMessageTimestamp(lastMessageTimestamp);
        userRepository.save(user).subscribe(result -> {
            log.info("update user in db: " + result.toString());
        });
    }

    private void updateTrips(Position position, ObjectId tripId) {
        if (tripId != null) {
            tripRepository.findById(tripId.toString()).subscribe(result -> {
                Trip trip = updateTripWithPosition(result, position);
                tripRepository.save(trip).subscribe(update -> {
                    log.info("update trip in db: " + update.toString());
                });
            });
        }
    }

    private Trip updateTripWithPosition(Trip trip, Position position) {
        trip.addPosition(position);

        trip.setStartDate(updateTripStartTime(trip, position));
        trip.setStopDate(updateTripStopTime(trip, position));

        return trip;
    }

    private Instant updateTripStopTime(Trip trip, Position position) {
        if (trip.getStartDate() == null) {
            return position.getTimestamp();
        }

        if (position.getTimestamp().isBefore(trip.getStartDate())) {
            return position.getTimestamp();
        } else {
            return trip.getStartDate();
        }
    }

    private Instant updateTripStartTime(Trip trip, Position position) {
        if (trip.getStopDate() == null) {
            return position.getTimestamp();
        }

        if (position.getTimestamp().isAfter(trip.getStopDate())) {
            return position.getTimestamp();
        } else {
            return trip.getStopDate();
        }
    }

}
