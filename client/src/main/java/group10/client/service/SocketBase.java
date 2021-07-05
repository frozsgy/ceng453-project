package group10.client.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Abstract base class to handle socket communications.
 *
 * @author Alperen Caykus, Mustafa Ozan Alpay
 */
public abstract class SocketBase {
    /**
     * Main socket object
     */
    protected Socket socket;
    /**
     * Input stream to handle incoming data
     */
    protected ObjectInputStream in;
    /**
     * Output stream to handle outgoing data
     */
    protected ObjectOutputStream out;

    /**
     * Constant logger instance to print messages to console
     */
    protected static final Logger LOGGER = LoggerFactory.getLogger(SocketBase.class);

    /**
     * Reads incoming object from the socket
     *
     * @return Object that is sent
     */
    public Object readSocket() {
        Object read = null;
        try {
            read = this.in.readObject();
            LOGGER.info("Read data from socket");
        } catch (IOException e) {
            LOGGER.error("Error while reading from socket");
        } catch (ClassNotFoundException e) {
            LOGGER.error("Class not found.");
        }
        return read;
    }

    /**
     * Sends object through the socket
     *
     * @param data Object to be sent
     * @return boolean true for successful, false for failed
     */
    public boolean writeSocket(Object data) {
        try {
            this.out.writeObject(data);
            LOGGER.info("Data sent through socket");
        } catch (IOException e) {
            LOGGER.error("Error while sending data through socket");
            return false;
        }
        return true;
    }

    /**
     * Closes socket and handles cleanup.
     */
    public void closeSocket() {
        try {
            this.socket.close();
            this.in.close();
            LOGGER.info("Socket server closed");
        } catch (IOException e) {
            LOGGER.error("Error while closing socket server");
        }
    }


}
