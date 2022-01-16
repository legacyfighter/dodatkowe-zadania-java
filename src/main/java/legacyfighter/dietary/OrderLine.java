package legacyfighter.dietary;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class OrderLine {

    @Id
    @GeneratedValue
    private Long id;

    private BigDecimal price;

    @ManyToOne
    private Order order;

    @ManyToOne
    private Product product;

    private int quantity;

    public OrderLine() {

    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
