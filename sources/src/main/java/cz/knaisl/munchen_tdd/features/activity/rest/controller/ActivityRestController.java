package cz.knaisl.munchen_tdd.features.activity.rest.controller;

import cz.knaisl.munchen_tdd.core.utils.UrlProvider;
import cz.knaisl.munchen_tdd.features.activity.rest.dto.ActivityDto;
import cz.knaisl.munchen_tdd.features.activity.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/activities")
public class ActivityRestController {

    @Autowired
    UrlProvider urlProvider;

    @Autowired
    ActivityService activityService;

    @RequestMapping(method = RequestMethod.GET, path = "/")
    ResponseEntity listActivities() {
        return ResponseEntity.ok(activityService.getActivities());
    }

    @RequestMapping(method = RequestMethod.POST, path = "/")
    ResponseEntity createActivty(@RequestBody @Valid ActivityDto dto) {
        Long activityId = activityService.createActivity(dto);
        URI url = URI.create(urlProvider.getHttpAddress() + "/activities/" + activityId);
        return ResponseEntity.created(url).build();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{activityId}")
    ResponseEntity getActivty(@PathVariable Long activityId) {
        return ResponseEntity.ok(activityService.getActivity(activityId));
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{activityId}")
    ResponseEntity deleteActivity(@PathVariable Long activityId) {
        activityService.deleteActivity(activityId);
        return ResponseEntity.noContent().build();
    }

}
