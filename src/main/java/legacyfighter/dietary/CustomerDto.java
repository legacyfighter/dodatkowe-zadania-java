package legacyfighter.dietary;

import java.util.Objects;

public class CustomerDto {

    private String name;
    private Long id;


    public CustomerDto(Customer customer) {
        this.name = customer.getName();
        this.id = customer.getId();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerDto that = (CustomerDto) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id);
    }
}
