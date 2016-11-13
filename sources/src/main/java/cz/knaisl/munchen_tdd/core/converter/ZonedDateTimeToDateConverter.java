package cz.knaisl.munchen_tdd.core.converter;

import org.springframework.core.convert.converter.Converter;

import java.time.ZonedDateTime;
import java.util.Date;

public class ZonedDateTimeToDateConverter implements Converter<ZonedDateTime, Date> {

    public Date convert(ZonedDateTime source) {
        return Date.from(source.toInstant());
    }

}
