package pro.pawelczyk.miascore.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import pro.pawelczyk.miascore.model.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * m-pawelczyk (GitGub) / m_pawelczyk (Twitter)
 * on 27.06.2020
 * created UserRepositoryTest in pro.pawelczyk.miascore.repositories
 * in project mias-core
 */
@DataMongoTest
public class UserRepositoryTest {

    public static final String PHONE_NUMBER_600 = "600600600";
    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void setUp() throws Exception {
        userRepository.deleteAll().block();
    }

    @Test
    void testFindByPhoneNumber() {
        // given
        User user1 = new User();
        user1.setPhoneNumber("500500500");
        userRepository.save(user1).block();

        User user2 = new User();
        user2.setPhoneNumber(PHONE_NUMBER_600);
        userRepository.save(user2).block();
        // when
        User user600 = userRepository.findByPhoneNumber(PHONE_NUMBER_600).block();
        // then
        assertEquals(PHONE_NUMBER_600, user600.getPhoneNumber());
    }
}
