package dao.interfaces;

public interface ITokenDAO {
    //maybe boolean?
    void insertOrupdateToken(String token, int subscriberId);
    void deleteExpiredTokens();
}
