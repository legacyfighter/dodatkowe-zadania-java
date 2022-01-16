package legacyfighter.dietary;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    private BigDecimal price;

    private String product;

    private int counter;

    public Product() {

    }

    void decrementCounter() {
        counter--;
    }

    void incrementCounter() {
        counter++;
    }
}
