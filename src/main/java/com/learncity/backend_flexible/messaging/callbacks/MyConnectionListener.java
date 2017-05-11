package com.learncity.backend_flexible.messaging.callbacks;

import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.XMPPConnection;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by DJ on 5/3/2017.
 */
public class MyConnectionListener implements ConnectionListener {

    private static final Logger logger = Logger.getLogger(MyConnectionListener.class.getSimpleName());

    @Override
    public void connected(XMPPConnection connection) {
        logger.log(Level.INFO, "Connection successful ...");
        // TODO: handle the connecting successful
    }

    @Override
    public void authenticated(XMPPConnection connection, boolean resumed) {
        logger.log(Level.INFO, "Authenticated successful ...");
        // TODO: handle the authenticating successful
    }

    @Override
    public void reconnectionSuccessful() {
        logger.log(Level.INFO, "Reconnection successful ...");
        // TODO: handle the reconnecting successful
    }

    @Override
    public void reconnectionFailed(Exception e) {
        logger.log(Level.INFO, "Reconnection failed: ", e.getMessage());
        // TODO: handle the reconnection failed
    }

    @Override
    public void reconnectingIn(int seconds) {
        logger.log(Level.INFO, "Reconnecting in %d secs", seconds);
        // TODO: handle the reconnecting in
    }

    @Override
    public void connectionClosedOnError(Exception e) {
        logger.log(Level.INFO, "Connection closed on error");
        // TODO: handle the connection closed on error
    }

    @Override
    public void connectionClosed() {
        logger.log(Level.INFO, "Connection closed");
        // TODO: handle the connection closed
    }
}
