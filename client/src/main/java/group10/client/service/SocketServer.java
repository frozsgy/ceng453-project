package group10.client.service;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;

/**
 * Socket class for handling server behaviour.
 *
 * @author Alperen Caykus, Mustafa Ozan Alpay
 */
public class SocketServer extends SocketBase {
    /**
     * Socket server
     */
    private ServerSocket server;

    /**
     * Server constructor
     *
     * @param port port of the socket
     */
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
