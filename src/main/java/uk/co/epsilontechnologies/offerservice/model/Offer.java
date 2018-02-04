package uk.co.epsilontechnologies.offerservice.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;
import java.util.UUID;

public class Offer {

    private final UUID id;

    @NotNull
    @Size(min = 3, max = 50)
    private final String description;

    @NotNull
    private final BigDecimal price;

    @NotNull
    private final Currency currency;

    @NotNull
    private final Instant expiryTime;

    @JsonCreator
    public Offer(
            @JsonProperty("id") final UUID id,
            @JsonProperty("description") final String description,
            @JsonProperty("price") final BigDecimal price,
            @JsonProperty("currency") final Currency currency,
            @JsonProperty("expiryTime") final Instant expiryTime) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.currency = currency;
        this.expiryTime = expiryTime;
    }

    public boolean isExpired() {
        return this.expiryTime.isBefore(Instant.now());
    }

    public UUID getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Instant getExpiryTime() {
        return expiryTime;
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}