import domain.Subscriber;
import domain.User;

public class main {
    public static void main(String[] args) {
        Subscriber sub = new Subscriber(1, "tom", "asdf@asdf.nl");
        User user = new User(null, sub);
    }
}
