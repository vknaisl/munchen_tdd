package cz.knaisl.munchen_tdd.features.activity.service;

import cz.knaisl.munchen_tdd.features.activity.rest.dto.ActivityDto;

import java.util.List;

public interface ActivityService {

    List<ActivityDto> getActivities();

    Long createActivity(ActivityDto dto);

    ActivityDto getActivity(Long activityId);

    void deleteActivity(Long activityId);

}
