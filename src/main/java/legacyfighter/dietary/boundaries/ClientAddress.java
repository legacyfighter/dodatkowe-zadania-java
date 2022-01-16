package legacyfighter.dietary.boundaries;

public class ClientAddress {

    private final String address1;
    private final String address2;
    private final String address3;
    private final String address4;

    ClientAddress(String address1, String address2, String address3, String address4) {
        this.address1 = address1;
        this.address2 = address2;
        this.address3 = address3;
        this.address4 = address4;
    }

    boolean isWithinEurope() {
        return address4.contains("Europe");
    }
}
