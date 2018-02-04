package uk.co.epsilontechnologies.offerservice.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.epsilontechnologies.offerservice.exception.OfferNotFoundException;
import uk.co.epsilontechnologies.offerservice.model.Offer;
import uk.co.epsilontechnologies.offerservice.service.OfferService;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OfferControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private OfferService mockOfferService;

    @Test
    public void shouldReceiveSuccessFromCreate() {

        // arrange
        final String description = "some-description";
        final String price = "100.00";
        final String currency = "GBP";
        final String expiryTime = "2018-07-28T22:25:51Z";
        final Offer offerToCreate = new Offer(null, description, new BigDecimal(price), Currency.getInstance(currency), Instant.parse(expiryTime));
        final Offer createdOffer = new Offer(UUID.randomUUID(), description, new BigDecimal(price), Currency.getInstance(currency), Instant.parse(expiryTime));
        final String requestJson = String.format("{\"description\":\"%s\",\"price\":%s,\"currency\":\"%s\",\"expiryTime\":\"%s\"}", description, price, currency, expiryTime);
        final String expectedResponseJson = String.format("{\"id\":\"%s\",\"description\":\"%s\",\"price\":%s,\"currency\":\"%s\",\"expiryTime\":\"%s\"}", createdOffer.getId().toString(), description, price, currency, expiryTime);
        when(mockOfferService.create(offerToCreate)).thenReturn(createdOffer);

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
        final Offer offer = new Offer(id, description, new BigDecimal(price), Currency.getInstance(currency), Instant.parse(expiryTime));
        final String offerJson = String.format("{\"id\":\"%s\",\"description\":\"%s\",\"price\":%s,\"currency\":\"%s\",\"expiryTime\":\"%s\"}", id, description, price, currency, expiryTime);
        when(mockOfferService.update(id, offer)).thenReturn(offer);

        // act
        final ResponseEntity<String> response = restTemplate.exchange(String.format("/offers/%s", id), HttpMethod.POST, requestEntity(offerJson), String.class);

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
        final Offer offer = new Offer(id, description, new BigDecimal(price), Currency.getInstance(currency), Instant.parse(expiryTime));
        final String offerJson = String.format("{\"id\":\"%s\",\"description\":\"%s\",\"price\":%s,\"currency\":\"%s\",\"expiryTime\":\"%s\"}", id, description, price, currency, expiryTime);
        when(mockOfferService.getById(id)).thenReturn(offer);

        // act
        final ResponseEntity<String> response = restTemplate.getForEntity(String.format("/offers/%s", id), String.class);

        // assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(offerJson, response.getBody());
    }

    @Test
    public void shouldReceiveNotFoundFromGetByIdForUnknownId() {

        // arrange
        final UUID id = UUID.randomUUID();
        when(mockOfferService.getById(id)).thenThrow(new OfferNotFoundException(id));

        // act
        final ResponseEntity<String> response = restTemplate.getForEntity(String.format("/offers/%s", id), String.class);

        // assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(String.format("{\"error\":\"offer does not exist for id: %s\"}", id), response.getBody());
    }

    @Test
    public void shouldReceiveBadRequestIfDescriptionIsMissing() {

        // arrange
        final String price = "100.00";
        final String currency = "GBP";
        final String expiryTime = "2018-07-28T22:25:51Z";
        final String requestJson = String.format("{\"price\":%s,\"currency\":\"%s\",\"expiryTime\":\"%s\"}", price, currency, expiryTime);

        // act
        final ResponseEntity<String> response = restTemplate.exchange("/offers", HttpMethod.POST, requestEntity(requestJson), String.class);

        // assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("{\"error\":\"description may not be null\"}", response.getBody());
    }

    @Test
    public void shouldReceiveBadRequestIfIllegalArgumentExceptionIsThrown() {

        // arrange
        final UUID id = UUID.randomUUID();
        final String description = "some-description";
        final String price = "100.00";
        final String currency = "GBP";
        final String expiryTime = "2018-07-28T22:25:51Z";
        final Offer offerToCreate = new Offer(id, description, new BigDecimal(price), Currency.getInstance(currency), Instant.parse(expiryTime));
        final String requestJson = String.format("{\"id\":\"%s\",\"description\":\"%s\",\"price\":%s,\"currency\":\"%s\",\"expiryTime\":\"%s\"}", id.toString(), description, price, currency, expiryTime);
        when(mockOfferService.create(offerToCreate)).thenThrow(new IllegalArgumentException("some error message"));

        // act
        final ResponseEntity<String> response = restTemplate.exchange("/offers", HttpMethod.POST, requestEntity(requestJson), String.class);

        // assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("{\"error\":\"some error message\"}", response.getBody());
    }

    @Test
    public void shouldReceiveInternalServerErrorWhenSomethingUnexpectedGoesWrong() {

        // arrange
        final String description = "some-description";
        final String price = "100.00";
        final String currency = "GBP";
        final String expiryTime = "2018-07-28T22:25:51Z";
        final Offer offerToCreate = new Offer(null, description, new BigDecimal(price), Currency.getInstance(currency), Instant.parse(expiryTime));
        final String requestJson = String.format("{\"description\":\"%s\",\"price\":%s,\"currency\":\"%s\",\"expiryTime\":\"%s\"}", description, price, currency, expiryTime);
        when(mockOfferService.create(offerToCreate)).thenThrow(new RuntimeException("Something went wrong!"));

        // act
        final ResponseEntity<String> response = restTemplate.exchange("/offers", HttpMethod.POST, requestEntity(requestJson), String.class);

        // assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("{\"error\":\"Something went wrong!\"}", response.getBody());
    }

    @Test
    public void shouldReceiveSuccessForCancel() {

        // arrange
        final UUID id = UUID.randomUUID();

        // act
        final ResponseEntity<String> response = restTemplate.exchange(String.format("/offers/%s", id), HttpMethod.DELETE, null, String.class);

        // assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(mockOfferService, times(1)).cancel(id);
    }

    @Test
    public void shouldReceiveBadRequestIfIllegalStateExceptionIsThrown() {

        // arrange
        final UUID id = UUID.randomUUID();
        doThrow(new IllegalStateException("some error message")).when(mockOfferService).cancel(id);

        // act
        final ResponseEntity<String> response = restTemplate.exchange(String.format("/offers/%s", id), HttpMethod.DELETE, null, String.class);

        // assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("{\"error\":\"some error message\"}", response.getBody());
    }


    private HttpEntity<String> requestEntity(final String body) {
        final HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
        requestHeaders.add(HttpHeaders.ACCEPT, "application/json");
        return new HttpEntity<>(body, requestHeaders);
    }

}
