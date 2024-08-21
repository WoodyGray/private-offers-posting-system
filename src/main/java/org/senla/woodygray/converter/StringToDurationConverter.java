package org.senla.woodygray.converter;

import org.springframework.core.convert.converter.Converter;
import java.time.Duration;

public class StringToDurationConverter implements Converter<String, Duration> {
    @Override
    public Duration convert(String source) {
        return Duration.parse("PT" + source);
    }
}
