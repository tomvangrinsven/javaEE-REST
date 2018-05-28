package nl.tomvangrinsven.vodagone.responses;

import nl.tomvangrinsven.vodagone.domain.Subscription;

public class SpecifiedSubscriptionOfUser {

    private final int id;
    private final String aanbieder;
    private final String dienst;
    private final String prijs;
    private final String startDatum;
    private final String verdubbeling;
    private final boolean deelbaar;
    private final String status;

    public SpecifiedSubscriptionOfUser(Subscription abonnement) {
        this.id = abonnement.getId();
        this.aanbieder = abonnement.getProvider();
        this.dienst = abonnement.getServiceName();
        this.prijs = abonnement.getPriceToString();
        this.startDatum = abonnement.getStartDate();
        this.verdubbeling = abonnement.getDoubled();
        this.deelbaar = abonnement.getShareable();
        this.status = abonnement.getStatus();
    }
}
