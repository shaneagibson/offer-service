package uk.co.epsilontechnologies.offerservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.epsilontechnologies.offerservice.model.Offer;
import uk.co.epsilontechnologies.offerservice.repository.OfferRepository;
import uk.co.epsilontechnologies.offerservice.service.generator.IdGenerator;

import java.util.UUID;

@Service
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;
    private final IdGenerator<UUID> offerIdGenerator;

    @Autowired
    public OfferServiceImpl(
            final OfferRepository offerRepository,
            final IdGenerator<UUID> offerIdGenerator) {
        this.offerRepository = offerRepository;
        this.offerIdGenerator = offerIdGenerator;
    }

    @Override
    public Offer create(final Offer offer) {
        if (offer.getId() != null) {
            throw new IllegalArgumentException("Cannot create an offer with a pre-populated 'id' value");
        }
        return offerRepository.create(
                new Offer(
                        offerIdGenerator.generate(),
                        offer.getDescription(),
                        offer.getPrice(),
                        offer.getCurrency(),
                        offer.getExpiryTime()));
    }

}