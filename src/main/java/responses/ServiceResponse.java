package responses;

public class ServiceResponse {

    private int id;
    private String aanbieder;
    private String dienst;

    public ServiceResponse() { }

    public ServiceResponse(int id, String provider, String service) {
        this.id = id;
        this.aanbieder = provider;
        this.dienst = service;
    }

    public int getId() {
        return id;
    }
}
