package de.thmshmm.logback;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import lombok.Getter;
import lombok.Setter;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;


/**
 * Created by Thomas Hamm on 02.02.19.
 */
public class KafkaAppender extends AppenderBase<ILoggingEvent> {
    private static Producer<Long, String> producer;

    @Getter
    @Setter
    private String topic;

    @Getter
    @Setter
    private String bootstrapServers;

    protected void append(ILoggingEvent iLoggingEvent) {
        producer.send(new ProducerRecord<>(topic, iLoggingEvent.getFormattedMessage()));
    }

    @Override
    public void start() {
        KafkaAppender.producer = createProducer(bootstrapServers);
        super.start();
    }

    @Override
    public void stop() {
        KafkaAppender.producer.close();
        super.stop();
    }

    private static Producer<Long, String> createProducer(String bootstrapServers) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "logback-appender");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.ACKS_CONFIG, "0");
        return new KafkaProducer<>(props);
    }
}
