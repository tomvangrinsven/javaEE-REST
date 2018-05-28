package nl.tomvangrinsven.vodagone.authorization;

import nl.tomvangrinsven.vodagone.dao.ITokenDAO;

import javax.inject.Inject;

public class Authorization {

    @Inject
    private ITokenDAO dao;

    private int userId;

    public boolean isAuthorized(String token){
        this.userId = dao.getUserIdByToken(token);
        if (this.userId >= 0){
            return true;
        }
        return false;
    }

    public int getUserId() {
        return userId;
    }
}
