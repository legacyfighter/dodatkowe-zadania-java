package legacyfighter.dietary.boundaries;

import java.math.BigDecimal;
import java.util.List;

interface OrderRemoteService {

    List<ClientOrder> getByPayerId(PayerId payerId);

    void informAboutNewOrderWithPayment(BigDecimal amount);
}
