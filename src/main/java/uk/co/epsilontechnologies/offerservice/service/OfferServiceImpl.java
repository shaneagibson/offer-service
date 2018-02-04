package uk.co.epsilontechnologies.offerservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.epsilontechnologies.offerservice.exception.OfferNotFoundException;
import uk.co.epsilontechnologies.offerservice.model.Offer;
import uk.co.epsilontechnologies.offerservice.repository.OfferRepository;
import uk.co.epsilontechnologies.offerservice.service.generator.IdGenerator;

import java.util.Currency;
import java.util.List;
import java.util.Optional;
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
            throw new IllegalArgumentException("cannot create an offer with a pre-populated 'id' value");
        }
        return offerRepository.create(
                new Offer(
                        offerIdGenerator.generate(),
                        offer.getDescription(),
                        offer.getPrice(),
                        offer.getCurrency(),
                        offer.getExpiryTime()));
    }

    @Override
    public Offer update(final UUID id, final Offer offer) {
        if (id == null || !id.equals(offer.getId())) {
            throw new IllegalArgumentException("id in payload must match id from uri");
        }
        return offerRepository.update(offer);
    }

    @Override
    public Offer getById(final UUID id) {
        return offerRepository.findById(id).orElseThrow(() -> new OfferNotFoundException(id));
    }

    @Override
    public void cancel(final UUID id) {
        offerRepository.findById(id).ifPresent(offer -> {
            if (!offer.isExpired()) {
                offerRepository.delete(id);
            } else {
                throw new IllegalStateException("cannot cancel an offer that has expired");
            }
        });
    }

    @Override
    public List<Offer> query(final Optional<Currency> currency) {
        return offerRepository.findByQuery(currency);
    }

}