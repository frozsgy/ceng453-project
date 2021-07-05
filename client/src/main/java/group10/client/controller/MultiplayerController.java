package group10.client.controller;

import group10.client.model.OpponentInfo;
import group10.client.service.HTTPService;
import javafx.scene.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handles socket requests through Runnable interface.
 *
 * @author Alperen Caykus, Mustafa Ozan Alpay
 * @see Runnable
 */
public class MultiplayerController implements Runnable {

    /**
     * Constant logger instance to print messages to console
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MultiplayerController.class);

    /**
     * IP address of socket
     */
    private String ip;
    /**
     * Port of socket
     */
    private int port;
    /**
     * Text area for GUI
     */
    private Text txt;
    /**
     * Opponent information
     */
    private OpponentInfo found;

    /**
     * Constructor
     *
     * @param ip   ip address of socket
     * @param port port of socket
     * @param txt  reference of Text to GUI
     */
    public MultiplayerController(String ip, int port, Text txt) {
        this.ip = ip;
        this.port = port;
        this.txt = txt;
        this.found = HTTPService.getInstance().getOpponent(new OpponentInfo(ip, port));
    }

    /**
     * Thread runner method
     */
    @Override
    public void run() {
        this.connect();

    }

    /**
     * Method that connects to the socket
     */
    private void connect() {
        if (this.found != null) {
            // found. connect to socket.
            GameController._instance.connectSocket(this.found, txt);
        } else {
            // not found. Open a socket and wait for connections.
            GameController._instance.createSocket(port, txt);
        }
    }

}
