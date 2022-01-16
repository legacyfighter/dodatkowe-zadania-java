package legacyfighter.dietary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    CustomerOrderGroupRepository customerOrderGroupRepository;

    @Autowired
    private AuthenticationContextFacade authenticationContextFacade;

    @Transactional
    public List<OrderDto> getOrdersForCompany(Long customerId) {
        CustomerDto customerDto = customerService.getCustomerBy(customerId);
        Customer customer = customerRepository.getOne(customerDto.getId());
        if (!customer.getType().equals(Customer.Type.Company) && !customer.getType().equals(Customer.Type.Division)) {
            throw new IllegalStateException("not a company nor division");
        }
        return getOrdersIncludingSubordinates(customerId);
    }

    @Transactional
    public List<OrderDto> getOrdersForAdmin(Long customerId) {
        CustomerDto customerDto = customerService.getCustomerBy(customerId);
        Customer customer = customerRepository.getOne(customerDto.getId());
        if (!customer.getType().equals(Customer.Type.Admin)) {
            throw new IllegalStateException("not an admin");
        }
        return getOrdersIncludingSubordinates(customerId);
    }

    @Transactional
    public List<OrderDto> getOrdersIncludingSubordinates(Long customerId) {
        CustomerDto customerDto = customerService.getCustomerBy(customerId);
        Customer customer = customerRepository.getOne(customerDto.getId());
        CustomerOrderGroup group = customer.getGroup();
        if (group == null) {
            throw new IllegalStateException("group cannot be null");
        }
        Set<OrderDto> orders = group.getOrders().stream().map(OrderDto::new).collect(Collectors.toSet());
        Set<CustomerOrderGroup> childs = group.getChilds();
        if (childs != null) {

            childs.forEach(child -> orders.addAll(getOrdersIncludingSubordinates(child.getCustomer().getId())));


        }
        return new ArrayList<>(orders);

    }

    private List<OrderDto> fetchChildOrders(CustomerOrderGroup group, Set<OrderDto> orders) {
        if (group == null) {
            throw new IllegalStateException("group cannot be null");
        }
        orders.addAll(group.getOrders().stream().map(OrderDto::new).collect(Collectors.toSet()));
        Set<CustomerOrderGroup> childs = group.getChilds();
        if (childs != null) {

            childs.forEach(child -> orders.addAll(fetchChildOrders(child.getCustomer().getCustomerOrderGroup(), orders)));


        }
        return new ArrayList<>(orders);

    }

    @Transactional
    public OrderDto getOrderById(Long orderId) {
        String authentication = authenticationContextFacade.getAuthentication().getName();
        Customer c = customerRepository.findByName(authentication);
        List<OrderDto> getOrdersIncludingSubordinates = getOrdersIncludingSubordinates(c.getId());

        OrderDto requested = new OrderDto(orderRepository.getOne(orderId));
        if (getOrdersIncludingSubordinates.contains(requested)) {
            return requested;
        }

        return null;
    }

    @Transactional
    public List<OrderDto> getLoggedCustomerOrders(boolean includingSubordinates) {
        String authentication = authenticationContextFacade.getAuthentication().getName();
        Customer c = customerRepository.findByName(authentication);
        if (includingSubordinates) {
            if (!c.getType().equals(Customer.Type.Company) && !c.getType().equals(Customer.Type.Division)) {
                throw new IllegalStateException("not a company nor division");
            }
            return getOrdersIncludingSubordinates(c.getId());
        } else {
            return customerService.getIndividualOrdersForCustomer(c.getId());
        }
    }

    @Transactional
    public BigDecimal calculateTaxForOrder(Long orderId) {
        Order order = orderRepository.getOne(orderId);
        return new TaxRulesAggregation(order.getTaxRules()).calculate(BigDecimal.ZERO);
    }
}