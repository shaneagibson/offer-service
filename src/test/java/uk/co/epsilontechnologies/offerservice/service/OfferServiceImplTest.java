package uk.co.epsilontechnologies.offerservice.service;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import uk.co.epsilontechnologies.offerservice.model.Offer;
import uk.co.epsilontechnologies.offerservice.repository.OfferRepository;
import uk.co.epsilontechnologies.offerservice.service.generator.IdGenerator;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;


public class OfferServiceImplTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    private OfferServiceImpl underTest;

    @Mock
    private OfferRepository mockOfferRepository;

    @Mock
    private IdGenerator<UUID> mockOfferIdGenerator;

    @Before
    public void setUp() {
        this.underTest = new OfferServiceImpl(mockOfferRepository, mockOfferIdGenerator);
    }

    @Test
    public void shouldCreateOfferIfIdIsNotPopulated() {

        // arrange
        final UUID id = UUID.randomUUID();
        when(mockOfferIdGenerator.generate()).thenReturn(id);
        final Offer offerToCreate = new Offer(null, "some description", new BigDecimal("100.00"), Currency.getInstance("GBP"), Instant.now());
        final Offer createdOffer = new Offer(id, "some description", new BigDecimal("100.00"), Currency.getInstance("GBP"), Instant.now());
        when(mockOfferRepository.create(createdOffer)).thenReturn(createdOffer);

        // act
        final Offer response = underTest.create(offerToCreate);

        // assert
        assertNotNull(response.getId());
        assertEquals(createdOffer.getId(), response.getId());
        assertEquals(createdOffer.getDescription(), response.getDescription());
        assertEquals(createdOffer.getPrice(), response.getPrice());
        assertEquals(createdOffer.getCurrency(), response.getCurrency());
        assertEquals(createdOffer.getExpiryTime(), response.getExpiryTime());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectOfferCreationIfIdIsPopulated() {

        // arrange
        final Offer offer = new Offer(UUID.randomUUID(), "some description", new BigDecimal("100.00"), Currency.getInstance("GBP"), Instant.now());

        // act
        underTest.create(offer);
    }

}