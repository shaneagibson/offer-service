package uk.co.epsilontechnologies.offerservice.service.generator;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UUIDGenerator implements IdGenerator<UUID> {

    @Override
    public UUID generate() {
        return UUID.randomUUID();
    }

}