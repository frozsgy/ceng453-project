package group10.client.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public abstract class SocketBase {
    protected Socket socket;
    protected DataInputStream in;
    protected DataOutputStream out;
    protected boolean sent = false;

    protected static final Logger LOGGER = LoggerFactory.getLogger(SocketBase.class);

    public String readSocket() {
        String line = null;
        if (this.sent) {
            try {
                line = this.in.readUTF();
                this.sent = false;
                LOGGER.info("Read data from socket");
            } catch (IOException e) {
                LOGGER.error("Error while reading from socket");
            }
        }
        return line;
    }

    public boolean writeSocket(String data) {
        if (!this.sent) {
            try {
                this.out.writeUTF(data);
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
