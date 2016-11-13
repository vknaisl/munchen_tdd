package cz.knaisl.munchen_tdd.features.activity.service;

import java.time.ZonedDateTime;

public interface TimeService {

    long computeDurationInSeconds(ZonedDateTime startDateTime, ZonedDateTime endDateTime);

}
