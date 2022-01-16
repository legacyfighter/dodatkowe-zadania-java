package legacyfighter.dietary;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

@SpringBootTest
@Sql(scripts = {"/scripts/testdb.sql"})
class OrdersTest {

	@Autowired
	private OrderService orderService;

	@Autowired
	private CustomerService customerService;

	@MockBean
	private AuthenticationContextFacade authenticationContextFacade;

	@Test
	void companyTest() {
		//caterinx
		List<OrderDto> ordersForCompanyCaterinx = orderService.getOrdersForCompany(2L);
		assertEquals(4, ordersForCompanyCaterinx.size());

		//zdrowoi jedz
		List<OrderDto> ordersForCompanyZdrowoJedz = orderService.getOrdersForCompany(1L);
		assertEquals(4, ordersForCompanyZdrowoJedz.size());
	}

	@Test
	void adminTest() {
		//piotr admin
		List<OrderDto> adminOrders = orderService.getOrdersForAdmin(3L);
		assertEquals(8, adminOrders.size());


	}

	@Test
	void divisionTest() {
		//logistyka zdrowo jedz
		List<OrderDto> logistyka = orderService.getOrdersForCompany(5L);
		assertEquals(2, logistyka.size());

		//k$l zdrowo jedz
		List<OrderDto> kl = orderService.getOrdersForCompany(4L);
		assertEquals(2, kl.size());

		//zamowienia caterinx
		List<OrderDto> zamowienia = orderService.getOrdersForCompany(6L);
		assertEquals(3, zamowienia.size());


	}

	@Test
	void personOrRepresentativeTest() {
		//kasia k$l
		List<OrderDto> katarzyna = customerService.getIndividualOrdersForCustomer(7L);
		assertEquals(2, katarzyna.size());

		//kasia k$l
		List<OrderDto> ludwik = customerService.getIndividualOrdersForCustomer(8L);
		assertEquals(0, ludwik.size());

		//pawel Logistyk k$l
		List<OrderDto> pawelLogistyk = customerService.getIndividualOrdersForCustomer(9L);
		assertEquals(0, pawelLogistyk.size());

		//edward sprzedawca caterinx
		List<OrderDto> edwardSprzedawca = customerService.getIndividualOrdersForCustomer(10L);
		assertEquals(1, edwardSprzedawca.size());

	}

	@Test
	void testLogged() {
		Authentication authentication = mock(Authentication.class);
		Mockito.when(authenticationContextFacade.getAuthentication()).thenReturn(authentication);

		Mockito.when(authentication.getName()).thenReturn("KATARZYNA");
		List<OrderDto> katarzyna = orderService.getLoggedCustomerOrders(false);
		assertEquals(2, katarzyna.size());
		assertThrows(IllegalStateException.class, () -> orderService.getLoggedCustomerOrders(true));

		Mockito.when(authentication.getName()).thenReturn("ZDROWO JEDZ");
		List<OrderDto> zdrowoJedz = orderService.getLoggedCustomerOrders(true);
		assertEquals(4, zdrowoJedz.size());
	}

}
