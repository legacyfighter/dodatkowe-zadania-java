package legacyfighter.dietary;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Customer {


    enum Type {
        Person, Representative, Division, Company, Admin
    }

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;

    @Enumerated(value = EnumType.STRING)
    private Type type;


    @OneToOne(cascade = CascadeType.ALL, mappedBy = "customer")
    private CustomerOrderGroup customerOrderGroup;

    public Customer() {
    }


    public CustomerOrderGroup getGroup() {
        return customerOrderGroup;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public CustomerOrderGroup getCustomerOrderGroup() {
        return customerOrderGroup;
    }

    public void setCustomerOrderGroup(CustomerOrderGroup customerOrderGroup) {
        this.customerOrderGroup = customerOrderGroup;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", type=" + type +
                '}';
    }

}
