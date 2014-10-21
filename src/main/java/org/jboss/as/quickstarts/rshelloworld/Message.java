package org.jboss.as.quickstarts.rshelloworld;

public class Message {

    private String author;

    private String text;

    public Message() {
    }

    public Message(String author, String text) {
        super();
        this.author = author;
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public String getText() {
        return text;
    }


}
