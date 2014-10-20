package org.jboss.as.quickstarts.rshelloworld;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Message {

    private String author;

    private String text;
}
