package de.thmshmm.logback;

import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.core.encoder.Encoder;
import lombok.Getter;
import lombok.Setter;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.ByteArraySerializer;

import java.util.Properties;


/**
 * Created by Thomas Hamm on 02.02.19.
 */
public class KafkaAppender<E> extends AppenderBase<E> {
    private static Producer<byte[], byte[]> producer;

    @Getter
    @Setter
    private String topic;

    @Getter
    @Setter
    private String bootstrapServers;

    @Getter
    @Setter
    private Encoder<E> encoder;

    protected void append(E event) {
        producer.send(new ProducerRecord<>(topic, encoder.encode(event)));
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

    private static Producer<byte[], byte[]> createProducer(String bootstrapServers) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "logback-appender");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class);
        props.put(ProducerConfig.ACKS_CONFIG, "0");
        return new KafkaProducer<>(props);
    }
}
