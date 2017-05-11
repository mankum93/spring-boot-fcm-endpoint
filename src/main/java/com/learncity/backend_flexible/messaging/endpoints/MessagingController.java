package com.learncity.backend_flexible.messaging.endpoints;


import com.learncity.backend_flexible.ApplicationProperties;
import com.learncity.backend_flexible.messaging.callbacks.DeferringFcmMessageListener;
import com.learncity.backend_flexible.messaging.framework.client.listeners.FcmStanzaListener;
import com.learncity.backend_flexible.messaging.framework.message.model.incoming.DeliveryReceiptOrControlMessage;
import com.learncity.backend_flexible.messaging.framework.message.model.incoming.DownstreamMessageResponse;
import com.learncity.backend_flexible.messaging.framework.message.model.incoming.UpstreamMessage;
import com.learncity.backend_flexible.messaging.model.Message;
import com.learncity.backend_flexible.messaging.framework.client.CcsClient;
import org.jivesoftware.smack.XMPPException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by DJ on 4/2/2017.
 */


@RestController
@EnableAutoConfiguration
public class MessagingController {

    private static final Logger logger = Logger.getLogger(MessagingController.class.getSimpleName());

    private CcsClient client;

    // App Properties from properties files
    private ApplicationProperties properties;

    private FcmStanzaListener fcmStanzaListener;
    private DeferringFcmMessageListener<DownstreamMessageResponse> downstreamMessageResponseListener;
    private DeferringFcmMessageListener<UpstreamMessage> upstreamMessageListener;
    private DeferringFcmMessageListener<DeliveryReceiptOrControlMessage> deliveryReceiptOrControlMessageListener;

    @Autowired
    public MessagingController(ApplicationProperties properties) throws IOException{
        this.properties = properties;

        if(client == null){
            client = CcsClient.prepareClient(properties.getSenderId(), properties.getServerKey(), true);
            if(fcmStanzaListener == null){
                fcmStanzaListener = new FcmStanzaListener();
            }
            if(downstreamMessageResponseListener == null){
                downstreamMessageResponseListener = new DeferringFcmMessageListener<DownstreamMessageResponse>();
            }
            if(upstreamMessageListener == null){
                upstreamMessageListener = new DeferringFcmMessageListener<UpstreamMessage>();
            }
            if(deliveryReceiptOrControlMessageListener == null){
                deliveryReceiptOrControlMessageListener = new DeferringFcmMessageListener<DeliveryReceiptOrControlMessage>();
            }

            fcmStanzaListener.setDownstreamMessageResponseListener(downstreamMessageResponseListener);
            fcmStanzaListener.setUpStreamMessageListener(upstreamMessageListener);
            fcmStanzaListener.setDeliveryReceiptOrControlMessageListener(deliveryReceiptOrControlMessageListener);

            client.setFcmStanzaListener(fcmStanzaListener);
            try {
                client.connect();
            }
            catch (XMPPException e) {
                e.printStackTrace();
            }
            /*catch (IOException ioe){
                ioe.printStackTrace();
            }*/
        }
    }


    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello from Learn City App!";
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(MessagingController.class, args);
    }

    @RequestMapping(
            path = "/send",
            method = RequestMethod.POST,
            consumes = "application/json"
    )
    public @ResponseBody DeferredResult<DownstreamMessageResponse> sendMessage(@RequestBody Message message){
        // Retrieve the Firebase Token of the Receiver
        String firebaseTokenReceiver = message.getTo();

        if(firebaseTokenReceiver == null){
            // Account doesn't exist
            logger.warning("Account with the User ID: " + message.getTo() + "doesn't exist");
            // TODO: Return a proper message indicating this situation
            return null;
        }

        // Lets not block this thread for this request anymore
        DeferredResult<DownstreamMessageResponse> deferredResult = new DeferredResult<DownstreamMessageResponse>();

        // Maintain this deferred result with the callback for later setting.
        downstreamMessageResponseListener.setDeferredResultHolder(deferredResult);

        client.sendAsync(Message.obtainFCMMessage(message));

        return deferredResult;
    }

    /*
    @RequestMapping(
            path = "/sendTest",
            method = RequestMethod.POST,
            consumes = "application/json"
    )
    public void sendTestMessage(@RequestBody Message message){
        Map<String, String> payload = new HashMap<String, String>();
        payload.put("message", "HELLO KITTY");
        client.send(Message.obtainFCMMessage(message));
    }
    */
}
