package group10.client.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

public abstract class SocketBase {
    protected Socket socket;
    protected ObjectInputStream in;
    protected ObjectOutputStream out;
    protected boolean sent = false;

    protected static final Logger LOGGER = LoggerFactory.getLogger(SocketBase.class);

    public Object readSocket() {
        Object read = null;
        if (this.sent) {
            try {
                read = this.in.readObject();
                this.sent = false;
                LOGGER.info("Read data from socket");
            } catch (IOException e) {
                LOGGER.error("Error while reading from socket");
            } catch (ClassNotFoundException e) {
                LOGGER.error("Class not found.");
            }
        }
        return read;
    }

    public boolean writeSocket(Object data) {
        if (!this.sent) {
            try {
                this.out.writeObject(data);
                LOGGER.info("Data sent through socket");
            } catch (IOException e) {
                LOGGER.error("Error while sending data through socket");
            }
            this.sent = true;
            return true;
        }
        return false;
    }

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
