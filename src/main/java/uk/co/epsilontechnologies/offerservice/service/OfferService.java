package uk.co.epsilontechnologies.offerservice.service;

import uk.co.epsilontechnologies.offerservice.model.Offer;

import java.util.UUID;

public interface OfferService {

    Offer create(Offer offer);

    Offer update(UUID id, Offer offer);

    Offer getById(UUID id);

}
