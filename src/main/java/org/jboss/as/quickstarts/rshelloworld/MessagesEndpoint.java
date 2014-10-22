/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.as.quickstarts.rshelloworld;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.json.Json;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;

import org.jboss.aerogear.unifiedpush.JavaSender;
import org.jboss.aerogear.unifiedpush.message.MessageResponseCallback;
import org.jboss.aerogear.unifiedpush.message.UnifiedMessage;

@Path("/messages")
public class MessagesEndpoint {

    private Logger log = Logger.getLogger("MessagesEndpoint");

    @Inject
    private MessageStore messageStore;

    @Inject
    private JavaSender sender;

    @Inject
    private UnifiedMessage.Builder messageBuilder;

    @POST
    @Path("/")
    @Produces({ MediaType.APPLICATION_JSON })
    public void putMessage( Message message, @Suspended final AsyncResponse response ) {

        log.info("sending push message...");

        final long version = System.currentTimeMillis() / 1000;
        messageStore.addMessage(version, message);

        UnifiedMessage pushMessage = messageBuilder
            .alert(String.format("Message from %s", message.getAuthor()))
            .simplePush("version=" + version)
            .attribute("version", Long.toString(version))
            .attribute("page", "cordova")
            .build();

        sender.send(pushMessage, new MessageResponseCallback() {

            @Override
            public void onError(Throwable throwable) {
                log.log(Level.WARNING, "push message error", throwable);
                throwable.printStackTrace();
                response.resume(throwable);
            }

            @Override
            public void onComplete(int statusCode) {
                log.info("push messages successfully sent with statusCode=" + statusCode);
                response.resume(Json.createObjectBuilder()
                        .add("version", version)
                        .build()
                        .toString());
            }
        });
    }

    @GET
    @Path("/{version}")
    @Produces({ MediaType.APPLICATION_JSON })
    public Message getHelloWorldJSON( @PathParam("version") long version ) {
        return messageStore.getMessage(version);
    }
}
