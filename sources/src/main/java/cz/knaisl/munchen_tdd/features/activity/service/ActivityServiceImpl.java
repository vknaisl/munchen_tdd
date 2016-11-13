package cz.knaisl.munchen_tdd.features.activity.service;

import cz.knaisl.munchen_tdd.core.exceptions.NotFoundException;
import cz.knaisl.munchen_tdd.core.sequence.SequenceService;
import cz.knaisl.munchen_tdd.features.activity.domain.Activity;
import cz.knaisl.munchen_tdd.features.activity.domain.ActivityRepository;
import cz.knaisl.munchen_tdd.features.activity.rest.dto.ActivityDto;
import cz.knaisl.munchen_tdd.features.activity.rest.factory.ActivityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    SequenceService sequenceService;

    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    ActivityMapper activityMapper;

    public List<ActivityDto> getActivities() {
        return activityMapper.toDTO(activityRepository.findAll());
    }

    public Long createActivity(ActivityDto dto) {
        Activity activity = activityMapper.fromDTO(dto);
        Long activityId = sequenceService.getNextSequence(Activity.class);
        Activity activityWithId = activity.toBuilder().activityId(activityId).build();
        activityRepository.save(activityWithId);
        return activityId;
    }

    public ActivityDto getActivity(Long activityId) {
        Activity activity = _getActivity(activityId);
        return activityMapper.toDTO(activity);
    }

    public void deleteActivity(Long activityId) {
        Activity activity = _getActivity(activityId);
        activityRepository.delete(activity);
    }

    private Activity _getActivity(Long activityId) {
        Optional<Activity> oActivity = activityRepository.findByActivityId(activityId);
        return oActivity.orElseThrow(
                () -> new NotFoundException(format("Activity record with activityId: %d not found.", activityId))
        );
    }

}
