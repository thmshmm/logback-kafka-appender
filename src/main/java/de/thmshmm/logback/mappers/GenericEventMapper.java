package de.thmshmm.logback.mappers;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import de.thmshmm.logback.events.GenericEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

/**
 * Created by Thomas Hamm on 04.02.19.
 */
@Mapper
public interface GenericEventMapper {
    GenericEventMapper INSTANCE = Mappers.getMapper(GenericEventMapper.class);

    @Mappings({
            @Mapping(source = "timeStamp", target = "timestamp"),
            @Mapping(source = "level", target = "level", qualifiedByName = "levelToString"),
            @Mapping(source = "loggerName", target = "logger"),
            @Mapping(source = "threadName", target = "thread"),
            @Mapping(source = "formattedMessage", target = "message")
    })
    GenericEvent toGenericEvent(ILoggingEvent event);

    @Named("levelToString")
    default String levelToString(Level level) {
        return level.levelStr;
    }
}
