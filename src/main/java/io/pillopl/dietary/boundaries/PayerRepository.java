package io.pillopl.dietary.boundaries;

interface PayerRepository {

    Payer findById(PayerId payerId);
}
