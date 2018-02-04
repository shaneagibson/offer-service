package uk.co.epsilontechnologies.offerservice.repository;

import uk.co.epsilontechnologies.offerservice.model.Offer;

import java.util.Currency;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OfferRepository {

    Offer create(Offer offer);

    Offer update(Offer offer);

    Optional<Offer> findById(UUID id);

    void delete(UUID id);

    List<Offer> findByQuery(Optional<Currency> currency);

}