package legacyfighter.dietary.newproducts;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
public class OldProduct {

    @Id
    private UUID serialNumber = UUID.randomUUID();

    @Embedded
    private Price price;

    @Embedded
    private Description desc;

    @Embedded
    private Counter counter;

    public OldProduct() {}

    public OldProduct(BigDecimal price, String desc, String longDesc, Integer counter) {
        this.price = Price.of(price);
        this.desc = new Description(desc, longDesc);
        this.counter = new Counter(counter);
    }

    void decrementCounter() {
        if (price.isNotZero()) {
            this.counter = counter.decrement();
        } else {
            throw new IllegalStateException("price is zero");
        }
    }

    void incrementCounter() {
        if (price.isNotZero()) {
            this.counter = counter.increment();
        } else {
            throw new IllegalStateException("price is zero");
        }
    }

    void changePriceTo(BigDecimal price) {
        if (counter.hasAny()) {
            this.price = Price.of(price);
        }
    }

    @Deprecated
    // not public
    void replaceCharFromDesc(char charToReplace, char replaceWith) {
        desc = desc.replace(charToReplace, replaceWith);
    }

    @Deprecated
    // not public
    String formatDesc() {
        return desc.formatted();
    }

    BigDecimal getPrice() {
        return price.getAsBigDecimal();
    }

    int getCounter() {
        return counter.getIntValue();
    }

    public UUID serialNumber() {
        return serialNumber;
    }
}

@Embeddable
class Price {


    static Price of(BigDecimal value) {
        return new Price(value);
    }


    private  BigDecimal price;

    private Price() { }

    private Price(BigDecimal price) {
        if (price == null || price.signum() < 0) {
            throw new IllegalStateException("Cannot have negative price: " + price);
        }
        this.price = price;
    }


    boolean isNotZero() {
        return price.signum() != 0;
    }

    BigDecimal getAsBigDecimal() {
        return price;
    }
}

@Embeddable
class Description {

    private String desc;
    private String longDesc;

    private Description() { }

    Description(String desc, String longDesc) {
        if (desc == null) {
            throw new IllegalStateException("Cannot have a null description");
        }
        if (longDesc == null) {
            throw new IllegalStateException("Cannot have null long description");
        }
        this.desc = desc;
        this.longDesc = longDesc;
    }

    String formatted() {
        if (desc.isEmpty() || longDesc.isEmpty()) {
            return "";
        }
        return desc + " *** " + longDesc;
    }

    public Description replace(char charToReplace, char replaceWith) {
        return new Description(desc.replace(charToReplace, replaceWith), longDesc.replace(charToReplace, replaceWith));
    }
}

@Embeddable
class Counter {

    static Counter zero() {
        return new Counter(0);
    }

    private Counter() {
    }

    private int counter;

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