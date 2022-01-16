package legacyfighter.dietary;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "order_table")
public class Order {

    public List<OrderLine> getItems() {
        return items;
    }

    public void setItems(List<OrderLine> items) {
        this.items = items;
    }

    enum OrderState {
        Initial, Paid, Delivered, Returned
    }

    enum OrderType {
        Phone, Wire, Wire_One_Item, Special_Discount, Regular_Batch
    }

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderState orderState;

    @Enumerated(EnumType.STRING)
    private OrderType orderType;

    @ManyToOne(cascade = CascadeType.ALL)
    private CustomerOrderGroup customerOrderGroup;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderLine> items;

    @ManyToMany
    private List<TaxRule> taxRules;

    private Instant confirmationTimestamp;

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public Instant getConfirmationTimestamp() {
        return confirmationTimestamp;
    }

    public OrderState getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public CustomerOrderGroup getCustomerOrderGroup() {
        return customerOrderGroup;
    }

    public void setCustomerOrderGroup(CustomerOrderGroup customerOrderGroup) {
        this.customerOrderGroup = customerOrderGroup;
    }

    public List<TaxRule> getTaxRules() {
        return taxRules;
    }

    public void setTaxRules(List<TaxRule> taxRules) {
        this.taxRules = taxRules;
    }

}


