package io.pillopl.dietary;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "order_table")
public class Order {



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
    //"zgrupowane" na widoku
    private CustomerOrderGroup customerOrderGroup;

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

}


class OrderLine {

}