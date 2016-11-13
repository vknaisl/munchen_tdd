package cz.knaisl.munchen_tdd.features.activity.rest.factory;

import cz.knaisl.munchen_tdd.features.activity.domain.Activity;
import cz.knaisl.munchen_tdd.features.activity.rest.dto.ActivityDto;
import cz.knaisl.munchen_tdd.features.activity.service.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityMapper {

    @Autowired
    TimeService timeService;

    public Activity fromDTO(ActivityDto dto) {
        return Activity.builder()
                .activityId(dto.activityId)
                .title(dto.title)
                .labels(dto.labels)
                .startDate(dto.startDate)
                .endDate(dto.endDate)
                .build();
    }

    public ActivityDto toDTO(Activity activity) {
        return ActivityDto.builder()
                .activityId(activity.getActivityId())
                .title(activity.getTitle())
                .labels(activity.getLabels())
                .startDate(activity.getStartDate())
                .endDate(activity.getEndDate())
                .duration(timeService.computeDurationInSeconds(activity.getStartDate(), activity.getEndDate()))
                .build();
    }

    public List<ActivityDto> toDTO(List<Activity> activities) {
        return activities.stream()
                .map(activity -> toDTO(activity))
                .collect(Collectors.toList());
    }
    
}
