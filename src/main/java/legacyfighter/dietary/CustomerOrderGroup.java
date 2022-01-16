package legacyfighter.dietary;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
public class CustomerOrderGroup {

    public CustomerOrderGroup() {

    }
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customerOrderGroup")
    private Set<Order> orders;

    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    private Customer customer;

    @ManyToOne(cascade = CascadeType.ALL)
    private CustomerOrderGroup parent;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parent")
    private Set<CustomerOrderGroup> childs;

    String getCustomerName() {
        return customer.getName();
    }

    public CustomerOrderGroup getParent() {
        return parent;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Set<CustomerOrderGroup> getChilds() {
        return childs;
    }

    public void setChilds(Set<CustomerOrderGroup> childs) {
        this.childs = childs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerOrderGroup that = (CustomerOrderGroup) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "CustomerOrderGroup{" +
                "customer='" + customer.getName() + '\'' +
                ", parent=" + parent +
                '}';
    }
}
