package legacyfighter.dietary.boundaries;


import java.math.BigDecimal;

public class PaymentService {

    private static BigDecimal MIN_AMOUNT_OF_ONE_ORDER_TO_BE_VIP = new BigDecimal(100);
    private static int MIN_AMOUNT_OF_ORDERS_TO_BE_VIP = 10;

    private final PayerRepository payerRepository;
    private final ClientAddressRemoteService ordersRemoteService;
    private final OrderRemoteService orderRemoteService;
    private final ClaimsRemoteService claimsRemoteService;

    PaymentService(PayerRepository payerRepository, ClientAddressRemoteService payerAddressRemoteService, OrderRemoteService orderRemoteService, ClaimsRemoteService claimsRemoteService) {
        this.payerRepository = payerRepository;
        this.ordersRemoteService = payerAddressRemoteService;
        this.orderRemoteService = orderRemoteService;
        this.claimsRemoteService = claimsRemoteService;
    }

    boolean pay(PayerId payerId, BigDecimal amountToPay) {
        Payer payer = payerRepository.findById(payerId);
        if (canAfford(amountToPay, payer)) {
            pay(amountToPay, payer);
            return true;
        } else if (payerIsVip(payer)) {
            payUsingExtraLimit(amountToPay, payer);
            return true;
        } else {
            return false;
        }
    }

    private void pay(BigDecimal amountToPay, Payer payer) {
        payer.pay(amountToPay);
        orderRemoteService.informAboutNewOrderWithPayment(amountToPay);
    }

    private void payUsingExtraLimit(BigDecimal amountToPay, Payer payer) {
        payer.payUsingExtraLimit(amountToPay);
        orderRemoteService.informAboutNewOrderWithPayment(amountToPay);
    }

    private boolean canAfford(BigDecimal amountToPay, Payer payer) {
        return payer.has(amountToPay);
    }

    private boolean payerIsVip(Payer payer) {
        return hasEnoughOrders(payer) && addressIsInEurope(payer) && isOldEnough(payer) && noClaimsBy(payer);
    }

    private boolean noClaimsBy(Payer payer) {
        return claimsRemoteService.clientHasNoClaims(payer.getPayerId());
    }

    private boolean isOldEnough(Payer payer) {
        return payer.isAtLeast20yo();
    }

    private boolean addressIsInEurope(Payer payer) {
        return ordersRemoteService.getByPayerId(payer.getPayerId()).isWithinEurope();
    }

    private boolean hasEnoughOrders(Payer payer) {
        return orderRemoteService.getByPayerId(payer.getPayerId())
                .stream()
                .filter(w -> w.isMoreThan(MIN_AMOUNT_OF_ONE_ORDER_TO_BE_VIP))
                .count() > MIN_AMOUNT_OF_ORDERS_TO_BE_VIP;
    }

}


