package data.entity;

public class User {
    private String phone_number;
    private String name;
    private String surname;
    private String nick;
    private String password;

    public User(String phone_number, String name, String surname, String nick, String password) {
        this.phone_number = phone_number;
        this.name = name;
        this.surname = surname;
        this.nick = nick;
        this.password = password;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
