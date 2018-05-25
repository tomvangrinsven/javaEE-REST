package domain;

public class Subscriber {
    private int id;
    private String name;
    private String email;

    public Subscriber(int id, String name, String emailAddress) {
        this.id = id;
        this.name = name;
        this.email = emailAddress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailAddress() {
        return email;
    }

    public void setEmailAddress(String emailAddress) {
        this.email = emailAddress;
    }
}
