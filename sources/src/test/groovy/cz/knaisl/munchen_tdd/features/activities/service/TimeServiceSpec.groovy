package cz.knaisl.munchen_tdd.features.activities.service

import cz.knaisl.munchen_tdd.features.activity.service.TimeServiceImpl
import spock.lang.Specification
import spock.lang.Unroll

import java.time.ZoneId
import java.time.ZonedDateTime

import static java.time.ZonedDateTime.of

class TimeServiceSpec extends Specification {

    def timeservice = new TimeServiceImpl()

    static def zone = ZoneId.systemDefault()

    @Unroll("We get right duration (#expectedDuration)")
    def "We get right duration"(ZonedDateTime startDatetime, ZonedDateTime endDatetime, long expectedDuration) {

        given: "Start datetime and end datetime"

        when: "We compute duration"
        long duration = timeservice.computeDurationInSeconds(startDatetime, endDatetime)

        then:
        duration == expectedDuration

        where:
        startDatetime                          | endDatetime                            | expectedDuration
        of(2016, 11, 15, 13, 00, 00, 00, zone) | of(2016, 11, 15, 14, 00, 00, 00, zone) | 60 * 60
        of(2016, 11, 15, 13, 00, 00, 00, zone) | of(2016, 11, 16, 13, 00, 00, 00, zone) | 24 * 60 * 60
        of(2016, 11, 15, 23, 55, 00, 00, zone) | of(2016, 11, 16, 00, 05, 00, 00, zone) | 10 * 60
    }

}
