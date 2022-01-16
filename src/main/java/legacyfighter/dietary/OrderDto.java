package legacyfighter.dietary;

import java.time.Instant;
import java.util.Objects;

public class OrderDto {

    private Long orderId;
    private Instant confirmationTimestamp;
    private Order.OrderType orderType;
    private Order.OrderState orderState;
    private CustomerDto customerDto;

    public OrderDto(Order order) {
        this.confirmationTimestamp = order.getConfirmationTimestamp();
        this.orderState = order.getOrderState();
        this.orderType = order.getOrderType();
        this.customerDto = new CustomerDto(order.getCustomerOrderGroup().getCustomer());
        this.orderId = order.getId();
    }


    public Instant getConfirmationTimestamp() {
        return confirmationTimestamp;
    }

    public void setConfirmationTimestamp(Instant confirmationTimestamp) {
        this.confirmationTimestamp = confirmationTimestamp;
    }

    public Order.OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(Order.OrderType orderType) {
        this.orderType = orderType;
    }

    public Order.OrderState getOrderState() {
        return orderState;
    }

    public void setOrderState(Order.OrderState orderState) {
        this.orderState = orderState;
    }

    public CustomerDto getCustomerDto() {
        return customerDto;
    }

    public void setCustomerDto(CustomerDto customerDto) {
        this.customerDto = customerDto;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDto orderDto = (OrderDto) o;
        return Objects.equals(orderId, orderDto.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId);
    }
}
