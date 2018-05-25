package domain;

public class Subscription {

    private static final String DOUBLED = "verdubbeld";
    private static final String NOT_AVAILABLE = "niet-beschikbaar";
    private static final String CANCELLED = "opgezegd";
    private static final String STANDARD = "standaard";
    private static final String PRICE_WITHOUT_DECIMAL = "€%.0f,- per maand";
    private static final String PRICE_WITH_DECIMAL = "€%.2f,- per maand";
    private static final double DOUBLING_MULTIPLIER = 1.5;

    private int id;
    private String startDatum;
    private String verdubbeling;
    private String status;
    private boolean eigenaar;
    private Service dienst;

    public Subscription(int id, String startDate, boolean isDoubled, String status, boolean owner, Service service) {
        this.id = id;
        this.startDatum = startDate;

        if (service.getCanBeDoubled()){
            if (isDoubled){
                this.verdubbeling = "verdubbeld";
            } else{
                this.verdubbeling = "standaard";
            }
        } else {
            this.verdubbeling = "niet-beschikbaar";
        }
        this.status = status;
        this.eigenaar = owner;
        this.dienst = service;
    }

    public int getId() {
        return id;
    }

    public String getStartDate() {
        return startDatum;
    }

    public String getDoubled() {
        return verdubbeling;
    }

    public String getStatus() {
        return status;
    }

    public boolean isOwner() {
        return eigenaar;
    }

    public Service getService() {
        return dienst;
    }

    public double getPrice(){
        double price = 0.00;
        if (!this.status.equals(CANCELLED)){
            switch(verdubbeling){
                case DOUBLED:
                    price += dienst.getCostsPerMonth() * DOUBLING_MULTIPLIER;
                    break;
                default:
                    price += dienst.getCostsPerMonth();
                    break;

            }
        }
        return price;
    }

    public String getPriceToString(){
        double price = this.getPrice();
        if (price % 1 == 0){
            return String.format(PRICE_WITHOUT_DECIMAL, price);
        } else{
            return String.format(PRICE_WITH_DECIMAL, price);
        }
    }

    public String getProvider(){
        return dienst.getProvider();
    }

    public String getServiceName(){
        return dienst.getService();
    }

    public boolean getShareable(){
        return this.eigenaar && dienst.getNoOfSharableUsers() > 0;
    }
}
