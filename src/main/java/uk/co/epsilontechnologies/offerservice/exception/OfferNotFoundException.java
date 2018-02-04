package uk.co.epsilontechnologies.offerservice.exception;

import java.util.UUID;

public class OfferNotFoundException extends RuntimeException {

    public OfferNotFoundException(final UUID id) {
        super(String.format("offer does not exist for id: %s", id.toString()));
    }

}
