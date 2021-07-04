package group10.client.service;

import java.io.*;
import java.net.ServerSocket;

public class SocketServer extends SocketBase {
    private ServerSocket server;

    public SocketServer(int port) {
        try {
            this.server = new ServerSocket(port);
            LOGGER.info("Socket server started");
        } catch (IOException e) {
            LOGGER.error("Error creating socket");
        }
        try {
            this.socket = this.server.accept();
            this.out = new ObjectOutputStream(this.socket.getOutputStream());
            this.in = new ObjectInputStream(new BufferedInputStream(this.socket.getInputStream()));
            LOGGER.info("Client connected");
        } catch (IOException e) {
            LOGGER.error("Error during client connection");
        }
        this.sent = true;
    }


}
