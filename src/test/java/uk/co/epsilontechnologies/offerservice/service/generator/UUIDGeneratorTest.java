package uk.co.epsilontechnologies.offerservice.service.generator;

import org.junit.Test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class UUIDGeneratorTest {

    private UUIDGenerator underTest = new UUIDGenerator();

    @Test
    public void shouldGenerateId() {
        assertNotNull(underTest.generate());
    }

    @Test
    public void shouldGenerateDifferentIdsWithSubsequentInvocations() {
        assertNotEquals(underTest.generate(), underTest.generate());
    }

}