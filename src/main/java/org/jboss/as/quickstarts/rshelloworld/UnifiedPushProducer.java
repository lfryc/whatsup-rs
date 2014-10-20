package org.jboss.as.quickstarts.rshelloworld;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;

import org.jboss.aerogear.unifiedpush.JavaSender;
import org.jboss.aerogear.unifiedpush.SenderClient;
import org.jboss.aerogear.unifiedpush.message.UnifiedMessage;

public class UnifiedPushProducer {

    @Produces @ApplicationScoped
    public JavaSender createSender() {
        return SenderClient
                .withRootServerURL("http://192.168.15.104:8080/ag-push/")
                .build();
    }

    @Produces @Dependent
    public UnifiedMessage.Builder createMessage() {
        return new UnifiedMessage.Builder()
            .pushApplicationId("a429c46a-bb60-4d88-a2ed-e91245c8c566")
            .masterSecret("49292890-92fd-4bb0-9a20-440f7451c1af");
    }
}
