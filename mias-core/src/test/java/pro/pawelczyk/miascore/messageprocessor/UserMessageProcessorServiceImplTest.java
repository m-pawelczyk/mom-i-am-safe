package pro.pawelczyk.miascore.messageprocessor;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import pro.pawelczyk.miascore.messageprocessor.impl.UserMessageProcessorServiceImpl;
import pro.pawelczyk.miascore.messageprocessor.model.Position;
import pro.pawelczyk.miascore.messageprocessor.model.User;
import pro.pawelczyk.miascore.messageprocessor.repositories.PositionRepository;
import pro.pawelczyk.miascore.messageprocessor.repositories.TripRepository;
import pro.pawelczyk.miascore.messageprocessor.repositories.UserRepository;
import pro.pawelczyk.miascore.twitterupdatter.TwitterUpdaterService;
import pro.pawelczyk.miascore.messageprocessor.MessageText;
import pro.pawelczyk.miascore.messageprocessor.SenderType;
import pro.pawelczyk.miascore.twitterupdatter.TwitterMessage;
import pro.pawelczyk.miascore.messageprocessor.UserMessage;
import reactor.core.publisher.Mono;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserMessageProcessorServiceImplTest {

    public static final String KWESTIA_SZLAKU = "kwestiaszlaku";
    public static final String SENDER_ID = "500501502";
    UserMessageProcessorServiceImpl userMessageProcessorService;

    @Mock
    TwitterUpdaterService twitterUpdaterService;

    @Mock
    UserRepository userRepository;

    @Mock
    PositionRepository positionRepository;

    @Mock
    TripRepository tripRepository;

    @BeforeAll
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        userMessageProcessorService =
                new UserMessageProcessorServiceImpl(twitterUpdaterService, userRepository, positionRepository, tripRepository);
    }

    @Test
    void shouldProcessMessageAndSendTwitterUpdate() {
        // given
        UserMessage userMessage = aUserMessage();
        User user = aUser();
        Position position = aPosition();
        User userWithPosition = aUser();
        userWithPosition.setLastPosition(position);
        // when
        when(userRepository.findByPhoneNumber(anyString())).thenReturn(Mono.just(user));
        when(positionRepository.save(any())).thenReturn(Mono.just(position));
        when(userRepository.save(any())).thenReturn(Mono.just(userWithPosition));
        userMessageProcessorService.processMessage(userMessage);
        // then
        verify(twitterUpdaterService, times(1)).sendTwitterUpdate(isA(TwitterMessage.class));
    }

    private UserMessage aUserMessage() {
        UUID uuid = UUID.nameUUIDFromBytes(KWESTIA_SZLAKU.getBytes());
        Instant instantFixed = Instant.parse("2018-08-19T16:02:42.00Z");
        ZoneId zoneId = ZoneId.of("Europe/Warsaw");
        Clock clock = Clock.fixed(instantFixed, zoneId);
        MessageText messageText = new MessageText("Hello Mom! I would like to say that I am in Puerto Natales! gps$-51.7293565,-72.510806,-10.7");
        return new UserMessage(uuid, Instant.now(clock), SENDER_ID, SenderType.SMS, messageText);
    }

    private Position aPosition() {
        Position position = new Position();
        position.setId(new ObjectId());
        position.setTimestamp(Instant.now());
        position.setLocation(new GeoJsonPoint(-51.7293565,-72.510806));
        position.setAltitude(-10.7);

        return position;
    }

    private User aUser() {
        User user = new User();
        user.setId(new ObjectId());
        user.setLastMessageTimestamp(Instant.now());
        user.setPhoneNumber(SENDER_ID);
        user.setTwitterAccount(KWESTIA_SZLAKU);

        return user;
    }
}