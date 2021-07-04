package group10.client.service;


import java.io.*;
import java.net.Socket;

public class SocketClient extends SocketBase {

    public SocketClient(String ip, int port) {
        try {
            this.socket = new Socket(ip, port);
            LOGGER.info("Connected to socket server");
        } catch (IOException e) {
            LOGGER.error("Error while connecting to socket server");
        }

        try {
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            LOGGER.info("Ready for transmitting data");
        } catch (IOException e) {
            LOGGER.error("Error while setting up the socket for transmitting data");
        }

    }
}
