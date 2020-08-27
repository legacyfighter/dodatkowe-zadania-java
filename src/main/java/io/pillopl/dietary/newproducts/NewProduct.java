package io.pillopl.dietary.newproducts;

import org.springframework.security.core.parameters.P;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.UUID;

public class NewProduct {

    private UUID serialNumber;

    private Price price;

    private Description desc;

    private Counter counter;

    void decrementCounter() {
        if (price.isNotZero()) {
            this.counter = counter.decrement();
        }
    }

    void incrementCounter() {
        if (price.isNotZero()) {
            this.counter = counter.increment();
        }
    }

    void changePriceTo(BigDecimal price) {
        if (counter.hasAny()) {
            this.price = Price.of(price);
        }
    }

    void replaceCharFromDesc(char charToReplace, char replaceWith) {
        desc = desc.replace(charToReplace, replaceWith);
    }

    String formatDesc() {
        return desc.formatted();
    }
}

class Price {

    static Price of(BigDecimal value) {
        return new Price(value);
    }

    private final BigDecimal price;

    private Price(BigDecimal price) {
        if (price == null || price.signum() < 0) {
            throw new IllegalStateException("Cannot have negative price: " + price);
        }
        this.price = price;
    }


    boolean isNotZero() {
        return price.signum() == 0;
    }
}

class Description {

    private final String desc;
    private final String longDesc;

    Description(String desc, String longDesc) {
        if (desc == null || desc.isEmpty()) {
            throw new IllegalStateException("Cannot have an empty description");
        }
        if (longDesc == null || longDesc.isEmpty()) {
            throw new IllegalStateException("Cannot have an empty long description");
        }
        this.desc = desc;
        this.longDesc = longDesc;
    }

    String formatted() {
        return desc + " *** " + longDesc;
    }

    public Description replace(char charToReplace, char replaceWith) {
        return new Description(desc.replace(charToReplace, replaceWith), longDesc.replace(charToReplace, replaceWith));
    }
}

class Counter {

    static Counter zero() {
        return new Counter(0);
    }

    private final int counter;

    Counter(int counter) {
        if (counter < 0) {
            throw new IllegalStateException("Cannot have negative counter: " + counter);
        }
        this.counter = counter;
    }

    int getIntValue() {
        return counter;
    }

    Counter increment() {
        return new Counter(counter + 1);
    }

    Counter decrement() {
        return new Counter(counter - 1);
    }

    boolean hasAny() {
        return counter > 0;
    }
}