package legacyfighter.dietary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public CustomerDto getCustomerBy(Long id) {
        Customer byId = customerRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        return new CustomerDto(byId);
    }

    @Transactional
    public List<OrderDto> getIndividualOrdersForCustomer(Long customerId) {
        CustomerDto customerDto = this.getCustomerBy(customerId);
        Customer customer = customerRepository.getOne(customerDto.getId());
        CustomerOrderGroup group = customer.getGroup();
        if (!customer.getType().equals(Customer.Type.Representative) && !customer.getType().equals(Customer.Type.Person)) {
            throw new IllegalStateException("not a person nor representative");
        }
        if (group == null) {
            throw new IllegalStateException("group cannot be null");
        }
        Set<Order> orders = group.getOrders();

        return
                orders
                        .stream()
                        .map(OrderDto::new)
                        .collect(Collectors.toList());


    }

}
