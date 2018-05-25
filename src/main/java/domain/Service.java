package domain;

public class Service {

    private int id;
    private String dienst;
    private String aanbieder;
    private double prijsPerMaand;
    private int deelbaar;
    private boolean verdubbelbaar;

    public Service(int id, String service, String provider, double costsPerMonth, int noOfSharableUsers, boolean canBeDoubled) {
        this.id = id;
        this.dienst = service;
        this.aanbieder = provider;
        this.prijsPerMaand = costsPerMonth;
        this.deelbaar = noOfSharableUsers;
        this.verdubbelbaar = canBeDoubled;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getService() {
        return dienst;
    }

    public void setService(String service) {
        this.dienst = service;
    }

    public String getProvider() {
        return aanbieder;
    }

    public double getCostsPerMonth() {
        return prijsPerMaand;
    }

    public int getNoOfSharableUsers() {
        return deelbaar;
    }


    public boolean getCanBeDoubled() {
        return verdubbelbaar;
    }
}
