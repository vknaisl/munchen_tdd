package cz.knaisl.munchen_tdd.features.activity.service;

import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class TimeServiceImpl implements TimeService {

    public long computeDurationInSeconds(ZonedDateTime startDateTime, ZonedDateTime endDateTime) {
        return ChronoUnit.SECONDS.between(startDateTime, endDateTime);
    }

}
