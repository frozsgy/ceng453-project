package group10.client.service;

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
            try {
                this.socket = this.server.accept();
                this.out = new ObjectOutputStream(this.socket.getOutputStream());
                this.in = new ObjectInputStream(this.socket.getInputStream());
                LOGGER.info("Client connected");
            } catch (Exception e) {
                LOGGER.error("Error during client connection");
            }
        } catch (IOException e) {
            LOGGER.error("Error creating socket");
        }
    }

    /**
     * Closes socket and handles cleanup.
     */
    @Override
    public void closeSocket() {
        super.closeSocket();
        try {
            this.server.close();
        } catch (IOException e) {
            LOGGER.error("Error while closing socket server");
        }
    }

}
