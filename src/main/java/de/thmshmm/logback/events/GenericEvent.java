package de.thmshmm.logback.events;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Thomas Hamm on 04.02.19.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class GenericEvent {
    private long timestamp;
    private String host;
    private String level;
    private String logger;
    private String thread;
    private String message;
}
