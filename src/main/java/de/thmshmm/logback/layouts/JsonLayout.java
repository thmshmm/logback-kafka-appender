package de.thmshmm.logback.layouts;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.LayoutBase;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.thmshmm.logback.events.GenericEvent;
import de.thmshmm.logback.mappers.GenericEventMapper;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Thomas Hamm on 04.02.19.
 */
public class JsonLayout extends LayoutBase<ILoggingEvent> {
    private static ObjectMapper jsonMapper;
    private static String host = null;

    @Getter
    @Setter
    private Boolean includeHost = false;

    public JsonLayout() {
        jsonMapper = new ObjectMapper();
        setHost();
    }

    public void setHost(String host) {
         JsonLayout.host = host;
    }

    public void setHost() {
        host = System.getenv("HOSTNAME");
    }

    @Override
    public String doLayout(ILoggingEvent event) {
        String res = null;
        GenericEvent genericEvent = GenericEventMapper.INSTANCE.toGenericEvent(event);

        if (includeHost && host != null && !host.isEmpty()) {
            genericEvent.setHost(host);
        } else if (includeHost && host == null) {
            addWarn("The environment variable 'HOSTNAME' needs to be set");
        }

        try {
            res = jsonMapper.writeValueAsString(genericEvent);
        } catch (JsonProcessingException e) {
            addError("Failed to create json string from log event");
        }

        return res;
    }
}
