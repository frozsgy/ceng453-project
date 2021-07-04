package group10.client.controller;

import group10.client.model.OpponentInfo;
import group10.client.service.HTTPService;
import javafx.scene.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MultiplayerController implements Runnable {

    /**
     * Constant logger instance to print messages to console
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MultiplayerController.class);

    private String ip;
    private int port;
    private Text txt;
    private OpponentInfo found;

    public MultiplayerController(String ip, int port, Text txt) {
        this.ip = ip;
        this.port = port;
        this.txt = txt;
        this.found = HTTPService.getInstance().getOpponent(new OpponentInfo(ip, port));
    }

    @Override
    public void run() {
        this.connect();

    }

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
