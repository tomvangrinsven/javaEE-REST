package domain;

import dao.DatabaseProperties;
import dao.TokenDAO;
import dao.interfaces.ITokenDAO;

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
