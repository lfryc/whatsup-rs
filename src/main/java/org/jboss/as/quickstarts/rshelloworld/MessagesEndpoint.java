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

import javax.inject.Inject;
import javax.json.Json;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * A simple REST service which is able to say hello to someone using HelloService Please take a look at the web.xml where JAX-RS
 * is enabled
 *
 * @author gbrey@redhat.com
 *
 */

@Path("/messages")
public class MessagesEndpoint {

    @Inject
    private MessageStore messageStore;

    @POST
    @Path("/")
    @Produces({ MediaType.APPLICATION_JSON })
    public String putMessage(Message message) {
        long version = System.currentTimeMillis();
        messageStore.addMessage(version, message);

        return Json.createObjectBuilder()
                .add("version", version)
                .build()
                .toString();
    }

    @GET
    @Path("/{version}")
    @Produces({ MediaType.APPLICATION_JSON })
    public Message getHelloWorldJSON( @PathParam("version") long version ) {
        return messageStore.getMessage(version);
    }
}
