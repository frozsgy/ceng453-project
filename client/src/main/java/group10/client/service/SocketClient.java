package group10.client.service;


import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Socket class for handling client behaviour.
 *
 * @author Alperen Caykus, Mustafa Ozan Alpay
 */
public class SocketClient extends SocketBase {

    /**
     * Constructor to connect to the server
     *
     * @param ip   ip address of the server
     * @param port port of the server
     */
    public SocketClient(String ip, int port) {
        try {
            this.socket = new Socket(ip, port);
            LOGGER.info("Connected to socket server");
            try {
                this.out = new ObjectOutputStream(socket.getOutputStream());
                this.in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
                LOGGER.info("Ready for transmitting data");
            } catch (IOException e) {
                LOGGER.error("Error while setting up the socket for transmitting data");
            }
        } catch (IOException e) {
            LOGGER.error("Error while connecting to socket server");
        }



    }
}
