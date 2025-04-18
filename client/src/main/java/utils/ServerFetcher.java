package utils;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;

public class ServerFetcher {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 4631;

    public static Object fetch(String type, String action, HashMap<String, String> payload) {
        try (
                Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream())
        ) {
            HashMap<String, Object> request = new HashMap<>();
            request.put("type", type);
            request.put("action", action);
            request.put("payload", payload);

            System.out.println("Request: " + request);

            out.writeObject(request);
            out.flush();

            return in.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return "⚠️ Error communicating with the server: " + e.getMessage();
        }
    }
}
