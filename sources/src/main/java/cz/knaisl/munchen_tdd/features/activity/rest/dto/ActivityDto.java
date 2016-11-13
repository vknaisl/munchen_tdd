package cz.knaisl.munchen_tdd.features.activity.rest.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.List;

@Builder(toBuilder = true)
public class ActivityDto {

    public final Long activityId;

    @NotNull
    public final String title;

    @NotNull
    public final List<String> labels;

    @NotNull
    @JsonFormat(shape=JsonFormat.Shape.STRING)
    public final ZonedDateTime startDate;

    @JsonFormat(shape=JsonFormat.Shape.STRING)
    public final ZonedDateTime endDate;

    public final long duration;

    @JsonCreator
    public ActivityDto(@JsonProperty(value = "activityId") Long activityId,
                       @JsonProperty(value = "title") String title,
                       @JsonProperty(value = "labels") List<String> labels,
                       @JsonProperty(value = "startDate") ZonedDateTime startDate,
                       @JsonProperty(value = "endDate") ZonedDateTime endDate,
                       @JsonProperty(value = "duration") long duration) {
        this.activityId = activityId;
        this.title = title;
        this.labels = labels;
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = duration;
    }
}
