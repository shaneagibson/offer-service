package uk.co.epsilontechnologies.offerservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.epsilontechnologies.offerservice.model.Offer;
import uk.co.epsilontechnologies.offerservice.repository.OfferRepository;
import uk.co.epsilontechnologies.offerservice.service.generator.UUIDGenerator;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class OfferServiceComponentTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private UUIDGenerator mockUuidGenerator;

    @Autowired
    private OfferRepository offerRepository;

    @Test
    public void shouldReceiveSuccessFromCreate() {

        // arrange
        final UUID id = UUID.randomUUID();
        final String description = "some-description";
        final String price = "100.00";
        final String currency = "GBP";
        final String expiryTime = "2018-07-28T22:25:51Z";
        final String requestJson = String.format("{\"description\":\"%s\",\"price\":%s,\"currency\":\"%s\",\"expiryTime\":\"%s\"}", description, price, currency, expiryTime);
        final String expectedResponseJson = String.format("{\"id\":\"%s\",\"description\":\"%s\",\"price\":%s,\"currency\":\"%s\",\"expiryTime\":\"%s\",\"expired\":false}", id.toString(), description, price, currency, expiryTime);
        when(mockUuidGenerator.generate()).thenReturn(id);

        // act
        final ResponseEntity<String> response = restTemplate.exchange("/offers", HttpMethod.POST, requestEntity(requestJson), String.class);

        // assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedResponseJson, response.getBody());
    }

    @Test
    public void shouldReceiveSuccessFromUpdate() {

        // arrange
        final UUID id = UUID.randomUUID();
        final String description = "some-description";
        final String price = "100.00";
        final String currency = "GBP";
        final String expiryTime = "2018-07-28T22:25:51Z";
        final String offerJson = String.format("{\"id\":\"%s\",\"description\":\"%s\",\"price\":%s,\"currency\":\"%s\",\"expiryTime\":\"%s\",\"expired\":false}", id, description, price, currency, expiryTime);
        offerRepository.create(new Offer(id, description, new BigDecimal(price), Currency.getInstance(currency), Instant.parse(expiryTime)));

        // act
        final ResponseEntity<String> response = restTemplate.exchange(String.format("/offers/%s", id.toString()), HttpMethod.POST, requestEntity(offerJson), String.class);

        // assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(offerJson, response.getBody());
    }

    @Test
    public void shouldReceiveSuccessFromGetById() {

        // arrange
        final UUID id = UUID.randomUUID();
        final String description = "some-description";
        final String price = "100.00";
        final String currency = "GBP";
        final String expiryTime = "2018-07-28T22:25:51Z";
        final String expectedJson = String.format("{\"id\":\"%s\",\"description\":\"%s\",\"price\":%s,\"currency\":\"%s\",\"expiryTime\":\"%s\",\"expired\":false}", id, description, price, currency, expiryTime);
        offerRepository.create(new Offer(id, description, new BigDecimal(price), Currency.getInstance(currency), Instant.parse(expiryTime)));

        // act
        final ResponseEntity<String> response = restTemplate.getForEntity(String.format("/offers/%s", id.toString()), String.class);

        // assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedJson, response.getBody());
    }

    @Test
    public void shouldReceiveSuccessFromCancel() {

        // arrange
        final UUID id = UUID.randomUUID();
        final String description = "some-description";
        final String price = "100.00";
        final String currency = "GBP";
        final String expiryTime = "2018-07-28T22:25:51Z";
        offerRepository.create(new Offer(id, description, new BigDecimal(price), Currency.getInstance(currency), Instant.parse(expiryTime)));

        // act
        final ResponseEntity<String> response = restTemplate.exchange(String.format("/offers/%s", id.toString()), HttpMethod.DELETE, null, String.class);

        // assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    public void shouldReceiveSuccessFromQuery() {

        // arrange
        final UUID id1 = UUID.randomUUID();
        final String description1 = "some-description";
        final String price1 = "100.00";
        final String currency1 = "GBP";
        final String expiryTime1 = "2018-07-28T22:25:51Z";
        final UUID id2 = UUID.randomUUID();
        final String description2 = "some-other-description";
        final String price2 = "110.00";
        final String currency2 = "AUD";
        final String expiryTime2 = "2018-07-29T22:25:51Z";
        final String expectedResponseJson = String.format(
                "[{\"id\":\"%s\",\"description\":\"%s\",\"price\":%s,\"currency\":\"%s\",\"expiryTime\":\"%s\",\"expired\":false}," +
                 "{\"id\":\"%s\",\"description\":\"%s\",\"price\":%s,\"currency\":\"%s\",\"expiryTime\":\"%s\",\"expired\":false}]",
                id1, description1, price1, currency1, expiryTime1,
                id2, description2, price2, currency2, expiryTime2);
        offerRepository.create(new Offer(id1, description1, new BigDecimal(price1), Currency.getInstance(currency1), Instant.parse(expiryTime1)));
        offerRepository.create(new Offer(id2, description2, new BigDecimal(price2), Currency.getInstance(currency2), Instant.parse(expiryTime2)));

        // act
        final ResponseEntity<String> response = restTemplate.getForEntity("/offers", String.class);

        // assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponseJson, response.getBody());
    }

    private HttpEntity<String> requestEntity(final String body) {
        final HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
        requestHeaders.add(HttpHeaders.ACCEPT, "application/json");
        return new HttpEntity<>(body, requestHeaders);
    }

}
