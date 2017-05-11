package com.learncity.backend_flexible.messaging.framework.message.listeners;

/**
 * Created by DJ on 5/4/2017.
 */
public interface GenericFcmMessageListener<Message> {

    void onReceiveMessage(Message m);
}
