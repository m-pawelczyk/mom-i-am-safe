package pro.pawelczyk.miascore;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Duration;

import static org.assertj.core.api.BDDAssertions.then;

@Testcontainers
public class DemoMiasCoreApplicationTest {
    private static final URL compose = DemoMiasCoreApplicationTest.class.getResource("/docker-compose.yml");

    @Container
    DockerComposeContainer container = new DockerComposeContainer(new File(compose.toURI()))
            .withExposedService("mongodb_1", 27017,  Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(30)))
            .withExposedService("rabbitmq_1", 5672,  Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(30)))
            .withExposedService("app_1", 8082,  Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(30)));

    DemoMiasCoreApplicationTest() throws URISyntaxException {
    }

    @Test
    public void shouldVerifyThatTheApplicationIsRunningInHealthyState() {
        System.out.println("The apps are running");

        ResponseEntity<String> entity = new RestTemplate().getForEntity("http://localhost:8082/actuator/health", String.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
