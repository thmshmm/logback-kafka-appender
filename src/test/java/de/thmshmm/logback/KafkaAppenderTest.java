package de.thmshmm.logback;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Created by Thomas Hamm on 04.02.19.
 */
public class KafkaAppenderTest {

    @Test
    public void test() {
        @SuppressWarnings("unchecked")
        KafkaAppender<ILoggingEvent> appender = (KafkaAppender<ILoggingEvent>) mock(KafkaAppender.class);

        Logger logger = (Logger) LoggerFactory.getLogger(KafkaAppenderTest.class);
        logger.addAppender(appender);

        logger.debug("Some debug {}", "event");

        ArgumentCaptor<ILoggingEvent> debugEvent = ArgumentCaptor.forClass(ILoggingEvent.class);
        verify(appender, times(1)).doAppend(debugEvent.capture());
        assertTrue(debugEvent.getValue().getFormattedMessage().equals("Some debug event"));
    }
}
