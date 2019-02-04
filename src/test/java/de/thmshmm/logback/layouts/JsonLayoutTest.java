package de.thmshmm.logback.layouts;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.LoggingEvent;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertTrue;

/**
 * Created by Thomas Hamm on 04.02.19.
 */
public class JsonLayoutTest {

    @Test
    public void test() {
        final JsonLayout layout = new JsonLayout();
        final Logger logger = (Logger) LoggerFactory.getLogger(JsonLayoutTest.class);
        layout.setIncludeHost(false);
        String res = layout.doLayout(
                new LoggingEvent(
                        JsonLayoutTest.class.getCanonicalName(),
                        logger,
                        Level.DEBUG,
                        "Some debug msg",
                        null,
                        new Object[]{}
                )
        );
        assertTrue(res.contains("\"message\":\"Some debug msg\""));
        assertTrue(res.contains("\"level\":\"DEBUG\""));
        assertTrue(res.contains("\"logger\":\"" + JsonLayoutTest.class.getCanonicalName() + "\""));
        assertTrue(res.contains("\"thread\":\"main\""));
        assertTrue(!res.contains("\"host\""));
    }

    @Test
    public void testWithHostname() {
        final JsonLayout layout = new JsonLayout();
        final Logger logger = (Logger) LoggerFactory.getLogger(JsonLayoutTest.class);
        layout.setHost("localhost");
        layout.setIncludeHost(true);
        String res = layout.doLayout(
                new LoggingEvent(
                        JsonLayoutTest.class.getCanonicalName(),
                        logger,
                        Level.DEBUG,
                        "Some debug msg",
                        null,
                        new Object[]{}
                )
        );
        assertTrue(res.contains("\"host\":\"localhost\""));
    }
}
