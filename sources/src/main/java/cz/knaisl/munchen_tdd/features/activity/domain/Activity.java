package cz.knaisl.munchen_tdd.features.activity.domain;

import lombok.*;
import lombok.experimental.NonFinal;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.ZonedDateTime;
import java.util.List;

@Document
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Activity {

    @Id
    ObjectId _id;

    Long activityId;

    String title;

    List<String> labels;

    ZonedDateTime startDate;

    ZonedDateTime endDate;

}
