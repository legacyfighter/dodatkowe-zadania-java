package legacyfighter.dietary.boundaries;

import java.math.BigDecimal;
import java.time.Instant;

class ClientOrder {
    private final BigDecimal amount;
    private final Instant timestamp;

    ClientOrder(BigDecimal amount, Instant timestamp) {
        this.amount = amount;
        this.timestamp = timestamp;
    }

    boolean isMoreThan(BigDecimal amount) {
        return this.amount.compareTo(amount) > 0;
    }
}
