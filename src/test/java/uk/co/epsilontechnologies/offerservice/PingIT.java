package uk.co.epsilontechnologies.offerservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PingIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldRecieveSuccessFromPingEndpoint() {
        final ResponseEntity<String> response = restTemplate.getForEntity("/ping", String.class);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

}
