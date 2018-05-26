package dao.interfaces;

public interface ITokenDAO {

    boolean insertOrupdateToken(String token, int subscriberId);
    boolean deleteExpiredTokens();
    int getUserIdByToken(String token);
}
