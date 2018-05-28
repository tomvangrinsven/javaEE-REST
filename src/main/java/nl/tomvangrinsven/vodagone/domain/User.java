package nl.tomvangrinsven.vodagone.domain;

import nl.tomvangrinsven.vodagone.dao.ITokenDAO;
import nl.tomvangrinsven.vodagone.dao.realisation.DatabaseProperties;
import nl.tomvangrinsven.vodagone.dao.realisation.TokenDAO;

public class User {
    private String token;
    private Subscriber abonnee;
    private ITokenDAO dao;

    public User(String token, Subscriber subscriber) {
        this.abonnee = subscriber;
        if (this.token == null){
            dao = new TokenDAO(new DatabaseProperties());
            this.createToken();
            return;
        }

    }

    private void createToken() {
        this.token = Token.generateToken();
        dao.insertOrupdateToken(this.token, this.abonnee.getId());
    }

    public String getToken() {
        return token;
    }

    public String getSubscriberName() {
        return abonnee.getName();
    }
}
