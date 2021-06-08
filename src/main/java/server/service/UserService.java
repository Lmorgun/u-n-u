package server.service;

import data.entity.User;
import server.connection.QueryHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    private final QueryHandler qHandler;

    public UserService(QueryHandler qHandler) {
        this.qHandler = qHandler;
    }

    public List<User> getAllUsersByStr(String str) throws SQLException {
        String query = "SELECT * FROM USERs " +
                "where nick like'%" + str + "%' or phone_number like'%" + str + "%'";
        ResultSet a = qHandler.execute(query);
        return fromResultSet(a);
    }

    public User authorization(String phNumb, String password) throws SQLException {
        String query = "select * from users " +
                "where phone_number = " + phNumb + " and password = " + password ;
        ResultSet a = qHandler.execute(query);
        List<User> f = fromResultSet(a);
        if (f.isEmpty()) {

            return null;
        } else {
            String str = "create view acc" + phNumb.toString() +
                    " as" +
                    " select m.id_message mid_message, m.text_message mtext_message, m.datatime mdatatime," +
                    "m.status_unread mstatus_unread,u.phone_number uphone_number, u.Name_ uName_, u.surname usurname," +
                    " u.nick unick,u.password upassword, u.date_of_birth udate_of_birth,u2.phone_number u2phone_number," +
                    " u2.Name_ u2Name_, u2.surname u2surname, u2.nick u2nick, u2.password u2password," +
                    "u2.date_of_birth u2date_of_birth " +
                    "from messages m join users u on m.id_from_user = u.phone_number " +
                    "join users u2 on u2.phone_number = m.id_to_user " +
                    "where id_from_user = " + phNumb + "  or id_to_user =" + phNumb;
            System.out.println(str);
            try {
                qHandler.executeNoReturn(str);
            } catch (Exception e) {
            }
            return f.get(0);
        }
    }

    public void register(User newUser) throws SQLException {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

        String query = "insert into users " +
                "values (" + newUser.getPhone_number() + ",'" + newUser.getName() + "','" + newUser.getSurname() +
                "','" + newUser.getNick() + "','" + newUser.getPassword() + "',convert(date,'" +
                newUser.getDateOfBirth().format(formatter) + "'))";
        System.out.println(query);
        qHandler.executeNoReturn(query);

    }


    private List<User> fromResultSet(ResultSet set) throws SQLException {
        List<User> entities = new ArrayList<>();
        while (set.next()) {
            entities.add(new User(set.getString("phone_number"),
                    set.getString("Name_"),
                    set.getString("surname"),
                    set.getString("nick"),
                    set.getString("password"),
                    set.getTimestamp("date_of_birth").toLocalDateTime()));
        }
        return entities;
    }

    public User getByPhNumber(String phone_number) throws SQLException {
        String query = "select * from users " +
                "where phone_number = " + phone_number;
        ResultSet a = qHandler.execute(query);
        List<User> f = fromResultSet(a);
        if (f.isEmpty()) {

            return null;
        }
        else {
            return f.get(0);
        }
    }
}
