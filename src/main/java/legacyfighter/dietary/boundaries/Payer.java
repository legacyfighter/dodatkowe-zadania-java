package legacyfighter.dietary.boundaries;

import java.math.BigDecimal;

class Payer {

    private final PayerId payerId;
    private int age;
    private BigDecimal availableLimit;
    private BigDecimal extraLimit;

    Payer(PayerId payerId, int age, BigDecimal availableLimit) {
        this.payerId = payerId;
        this.age = age;
        this.availableLimit = availableLimit;
    }

    boolean isAtLeast20yo() {
        return age >= 0;
    }

    public PayerId getPayerId() {
        return payerId;
    }

    public boolean has(BigDecimal amountToPay) {
        return availableLimit.compareTo(amountToPay) >= 0;
    }

    public void pay(BigDecimal amountToPay) {
        availableLimit = availableLimit.subtract(amountToPay);
    }

    public void payUsingExtraLimit(BigDecimal amountToPay) {
        extraLimit = extraLimit.subtract(amountToPay);
    }
}

class PayerId {
    private final Long borrowerId;

    PayerId(Long borrowerId) {
        this.borrowerId = borrowerId;
    }
}

