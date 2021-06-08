package server.service;

import data.entity.Message;
import data.entity.User;
import microsoft.sql.DateTimeOffset;
import server.connection.QueryHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MessageService {
    QueryHandler qHandler;

    public MessageService(QueryHandler qHandler) {
        this.qHandler = qHandler;
    }

    public void createMessage(Message mess) throws SQLException {
        String query ="Insert into messages(id_from_user,id_to_user,text_message) " +
                "values ("+mess.getSender().getPhone_number()+","+mess.getReceiver().getPhone_number()+",'"+mess.getText()+"')";

        qHandler.executeNoReturn(query);

    }

    public void updateStatus(Message mess) throws SQLException {
        String query ="update messages " +
                "set status_unread = 0 " +
                "where id_from_user = "+mess.getSender().getPhone_number()+" and id_to_user = "+mess.getReceiver().getPhone_number();
        qHandler.executeNoReturn(query);
    }

    public void onDeleteMess(Message mess) throws SQLException {
        String query ="delete  from messages " +
                "where id_message = "+mess.getId();
        qHandler.executeNoReturn(query);
    }

    public List<Message> allMessages(User urUser) throws SQLException {
        String query = "select * " +
                "from acc"+urUser.getPhone_number().toString();
        ResultSet a = qHandler.execute(query);
        return fromResultMess(a);
    }

    private List<Message> fromResultMess(ResultSet set) throws SQLException {
        List<Message> entities = new ArrayList<>();
        while (set.next()) {
            entities.add(new Message(set.getInt("mid_message"),
                    new User(set.getString("uphone_number"),set.getString("uName_"),
                            set.getString("usurname"),set.getString("unick"),
                            set.getString("upassword"),set.getTimestamp("udate_of_birth").toLocalDateTime()),
                    new User(set.getString("u2phone_number"),set.getString("u2Name_"),
                            set.getString("u2surname"),set.getString("u2nick"),
                            set.getString("u2password"),set.getTimestamp("u2date_of_birth").toLocalDateTime()),
                    set.getString("mtext_message"),
                    ( (DateTimeOffset)set.getObject("mdatatime",DateTimeOffset.class)).getTimestamp().toLocalDateTime(),
                    set.getBoolean("mstatus_unread")));
        }
        return entities;
    }
}
