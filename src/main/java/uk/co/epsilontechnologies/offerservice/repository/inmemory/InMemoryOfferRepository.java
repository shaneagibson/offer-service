package uk.co.epsilontechnologies.offerservice.repository.inmemory;

import org.springframework.stereotype.Repository;
import uk.co.epsilontechnologies.offerservice.model.Offer;
import uk.co.epsilontechnologies.offerservice.repository.OfferRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
public class InMemoryOfferRepository implements OfferRepository {

    private static final Map<UUID,Offer> offers = new HashMap<>();

    @Override
    public Offer create(final Offer offer) {
        offers.put(offer.getId(), offer);
        return offer;
    }

}