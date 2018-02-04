package uk.co.epsilontechnologies.offerservice.service;

import uk.co.epsilontechnologies.offerservice.model.Offer;

import java.util.Currency;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OfferService {

    Offer create(Offer offer);

    Offer update(UUID id, Offer offer);

    Offer getById(UUID id);

    void cancel(UUID id);

    List<Offer> query(Optional<Currency> currency);

}
