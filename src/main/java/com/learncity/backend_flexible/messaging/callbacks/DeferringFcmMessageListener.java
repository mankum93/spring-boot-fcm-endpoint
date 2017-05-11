package com.learncity.backend_flexible.messaging.callbacks;

import com.learncity.backend_flexible.messaging.framework.message.listeners.AbstractFcmMessageListener;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.logging.Logger;

/**
 * Created by DJ on 5/4/2017.
 */
public class DeferringFcmMessageListener<Message> extends AbstractFcmMessageListener<Message> {

    private static final Logger logger = Logger.getLogger(DeferringFcmMessageListener.class.getSimpleName());

    private DeferredResult<Message> deferredResult;

    @Override
    public void onReceiveMessage(Message response) {

        logger.info("The Message received of type - " + response.getClass().getSimpleName() + " is: \n" + response.toString());

        // Set the deferred result.
        if(deferredResult != null){
            deferredResult.setResult(response);
        }
    }

    public void setDeferredResultHolder(DeferredResult<Message> result) {
        this.deferredResult = result;
    }
}
