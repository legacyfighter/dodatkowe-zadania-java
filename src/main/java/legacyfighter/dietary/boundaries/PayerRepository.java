package legacyfighter.dietary.boundaries;

interface PayerRepository {

    Payer findById(PayerId payerId);
}
