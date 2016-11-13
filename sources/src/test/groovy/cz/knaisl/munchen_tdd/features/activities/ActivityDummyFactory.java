package cz.knaisl.munchen_tdd.features.activities;

import cz.knaisl.munchen_tdd.features.activity.domain.Activity;

import java.time.ZonedDateTime;
import java.util.Arrays;

public class ActivityDummyFactory {

    public static Activity createActivity1() {
        return Activity.builder()
                .activityId(1L)
                .title("Working on homeworks")
                .labels(Arrays.asList("school", "homeworks"))
                .startDate(ZonedDateTime.now().minusDays(1).minusHours(5).minusMinutes(30))
                .endDate(ZonedDateTime.now().minusDays(1).minusHours(3))
                .build();
    }

    public static Activity createActivity2() {
        return Activity.builder()
                .activityId(2L)
                .title("Math lecture")
                .labels(Arrays.asList("school", "lectures"))
                .startDate(ZonedDateTime.now().minusDays(1).minusHours(1))
                .endDate(ZonedDateTime.now().minusDays(1))
                .build();
    }

}
