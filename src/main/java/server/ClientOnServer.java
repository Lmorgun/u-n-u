package server;

import data.JsonMap;
import data.entity.Message;
import data.entity.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientOnServer extends Thread{
    private final Socket socket;
    private final int id;
    private User account;
    private final PrintWriter stream;
    private final MessageService messSrv;
    private final UserService urSrv;

    public int getID() {
        return id;
    }

    public User getAccount() {
        return account;
    }

    public PrintWriter getStream() {
        return stream;
    }

    public void setAccount(User account) {
        this.account = account;
    }

    public ClientOnServer(Socket socket, int id, PrintWriter stream) {
        this.socket = socket;
        this.id = id;
        this.stream = stream;
        account = null;
    }

    @Override
    public void run() {
        try
        {
            BufferedReader waiter = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("ClientOnServer new instance");

            while(!socket.isClosed()){
                String json = waiter.readLine();
                JsonMap obj = JsonMap.fromJson(json);
                if (obj.contains("new message")) {
                    Message mess = obj.get("new message", Message.class);
                }

                if(obj.contains("search")){
                    String str = obj.get("search",String.class);
                }
            }
        }
        catch(IOException ex){
            System.err.println(ex.getMessage());
        }
        finally{
            System.out.println("ClientOnServer disconnected");
            Server.getInstance().removeClient(this);
        }


    }
}
