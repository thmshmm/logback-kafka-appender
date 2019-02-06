package de.thmshmm.logback;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.encoder.LayoutWrappingEncoder;
import de.thmshmm.logback.layouts.JsonLayout;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by Thomas Hamm on 04.02.19.
 */
@RunWith(MockitoJUnitRunner.class)
public class KafkaAppenderTest {

    @Mock
    private KafkaAppender<ILoggingEvent> appender;

    @Mock
    private LayoutWrappingEncoder<ILoggingEvent> encoder;

    @Mock
    private JsonLayout layout;

    @Test
    public void test() {
        encoder.setLayout(layout);
        appender.setEncoder(encoder);

        Logger logger = (Logger) LoggerFactory.getLogger(KafkaAppenderTest.class);
        logger.addAppender(appender);

        logger.debug("Some debug {}", "event");

        ArgumentCaptor<ILoggingEvent> debugEvent = ArgumentCaptor.forClass(ILoggingEvent.class);
        verify(appender, times(1)).doAppend(debugEvent.capture());
        assertTrue(debugEvent.getValue().getFormattedMessage().equals("Some debug event"));

        layout.doLayout(debugEvent.getValue());
        ArgumentCaptor<ILoggingEvent> event = ArgumentCaptor.forClass(ILoggingEvent.class);
        verify(layout).doLayout(debugEvent.capture());
        verify(layout, times(1)).doLayout(event.capture());
        System.out.println(event);
    }
}
