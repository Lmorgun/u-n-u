package client;

import data.JsonMap;
import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.IOException;

public class Waiter extends Thread {
    private final BufferedReader reader;
    private final ClientApplication application;

    public Waiter(BufferedReader reader, ClientApplication application) {
        this.reader = reader;
        this.application = application;
    }

    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        try {
            while (true) {
                String json = reader.readLine();
                application.onGetFromServer(JsonMap.fromJson(json));
            }
        } catch (IOException ex) {
            System.err.println("Reader closed");
            Platform.exit();
        }

    }
}

