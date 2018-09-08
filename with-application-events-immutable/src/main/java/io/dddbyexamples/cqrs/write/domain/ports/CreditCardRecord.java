package io.dddbyexamples.cqrs.write.domain.ports;

import io.dddbyexamples.cqrs.DomainEvent;
import io.dddbyexamples.cqrs.write.domain.produces.CardWithdrawn;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
@Table(name = "credit_card")
@NoArgsConstructor
public class CreditCardRecord {

    @Id
    @GeneratedValue
    private UUID id;
    private BigDecimal initialLimit;
    private BigDecimal usedLimit;


    public CreditCardRecord(BigDecimal limit) {
        this.initialLimit = limit;
        this.usedLimit = BigDecimal.ZERO;
    }
    public CreditCardRecord(UUID id, BigDecimal limit) {
        this.id = id;
        this.initialLimit = limit;
        this.usedLimit = BigDecimal.ZERO;
    }

    public void apply(DomainEvent event) {
        if (event instanceof CardWithdrawn) {
            this.usedLimit = usedLimit.add(((CardWithdrawn) event).getAmount());
        } else {
            throw new IllegalStateException("Event: " + event.getClass().getSimpleName() + "not handled");
        }
    }
}
