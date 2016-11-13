package cz.knaisl.munchen_tdd.features.activity.domain;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ActivityRepository extends MongoRepository<Activity, Long> {

    Optional<Activity> findByActivityId(Long activityId);

}
