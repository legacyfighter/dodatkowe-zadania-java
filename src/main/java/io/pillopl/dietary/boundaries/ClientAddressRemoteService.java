package io.pillopl.dietary.boundaries;

interface ClientAddressRemoteService {

    ClientAddress getByPayerId(PayerId payerId);
}
