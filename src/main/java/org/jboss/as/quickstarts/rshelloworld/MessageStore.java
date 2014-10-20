package org.jboss.as.quickstarts.rshelloworld;

import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Singleton;

@Singleton
public class MessageStore {

    private ConcurrentHashMap<Long, Message> map = new ConcurrentHashMap<>();

    public Message getMessage(long identifier) {
        return map.get(identifier);
    }

    public void addMessage(long identifier, Message message) {
        map.put(identifier, message);
    }
}
