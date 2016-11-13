package cz.knaisl.munchen_tdd.features.activities.integration

import cz.knaisl.munchen_tdd.Application
import cz.knaisl.munchen_tdd.configuration.MongoTestConfiguration
import cz.knaisl.munchen_tdd.core.IntegrationSpec
import cz.knaisl.munchen_tdd.core.utils.UrlProvider
import cz.knaisl.munchen_tdd.features.activity.domain.ActivityRepository
import cz.knaisl.munchen_tdd.features.activity.service.ActivityService
import cz.knaisl.munchen_tdd.features.activity.service.TimeService
import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootContextLoader
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import spock.lang.Unroll

import java.time.ZonedDateTime

import static cz.knaisl.munchen_tdd.features.activities.ActivityDummyFactory.createActivity1
import static cz.knaisl.munchen_tdd.features.activities.ActivityDummyFactory.createActivity2
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

@ContextConfiguration(classes = [Application, MongoTestConfiguration, ActivityTestConfiguration], loader = SpringBootContextLoader)
public class ActivityAPISpec extends IntegrationSpec {

    @Autowired
    ActivityService activityService

    @Autowired
    ActivityRepository activityRepository

    @Autowired
    UrlProviderProxy urlProviderProxy

    UrlProvider urlProvider = Mock(UrlProvider)

    @Autowired
    TimeService timeService

    def jsonParser = new JsonSlurper();

    def activity1 = createActivity1()
    def activity2 = createActivity2()

    def setup() {
        urlProviderProxy.delegate = urlProvider
        urlProvider.getHttpAddress() >> "http://localhost"
    }

    def "GET /activities/ returns all activities from database"() {
        given:
        activityRepository.save(activity1)
        activityRepository.save(activity2)

        // ---------------------------------------------------------
        when: "Make request"
        def response = mockMvc.perform(get("/activities/")).andReturn().response
        def content = jsonParser.parseText(response.contentAsString)

        // ---------------------------------------------------------
        then: "Server returns right content"
        response.status == HttpStatus.OK.value()

        content.size() == 2
        content[0].activityId == 1
        content[1].activityId == 2
    }

    def "GET /activities/1 returns activity detail"() {

        given:
        activityRepository.save(activity1)

        // ---------------------------------------------------------
        when: "Make request"
        def response = mockMvc.perform(get("/activities/1")).andReturn().response
        def content = jsonParser.parseText(response.contentAsString)

        // ---------------------------------------------------------
        then: "Server returns right content"

        // -> status
        response.status == HttpStatus.OK.value()

        // -> body
        content.activityId == 1
        content.title == activity1.getTitle()
        content.labels == activity1.getLabels()
        ZonedDateTime.parse(content.startDate).toLocalDateTime() == activity1.getStartDate().toLocalDateTime()
        ZonedDateTime.parse(content.endDate).toLocalDateTime() == activity1.getEndDate().toLocalDateTime()
        content.duration == timeService.computeDurationInSeconds(activity1.getStartDate(), activity1.getEndDate())
    }

    def "GET /activities/999 returns 404 response if activity does not exists"() {

        when: "Make request on non existing activity"
        def response = mockMvc.perform(get("/activities/999")).andReturn().response

        then: "Server returns 404 response"
        response.status == HttpStatus.NOT_FOUND.value()
    }

    def "POST /activities/ creates new activity in database"(String body) {

        when: "Make request"
        def response = mockMvc.perform(
                post("/activities/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
        ).andReturn().response

        then: "Server returns right content"
        response.status == HttpStatus.CREATED.value()
        response.getHeader("Location") == "http://localhost/activities/1"

        where:
        body                                                                                                                                                     | _
        """
            {
                "title": "Meeting",
                "labels": ["work"],
                "startDate": "${ZonedDateTime.now().minusHours(1).toString()}",
                "endDate": null
            }
        """                                                              | _
        """
            {
                "title": "Meeting",
                "labels": ["work"],
                "startDate": "${ZonedDateTime.now().minusHours(1).toString()}",
                "endDate": "${ZonedDateTime.now().toString()}"
            }
        """ | _
    }

    @Unroll("POST /activities returns error when field #missingField is missing")
    def "POST /activities returns error when all required fields are not filled"(String body, String missingField) {

        when: "Make request"
        def response = mockMvc.perform(
                post("/activities/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
        ).andReturn().response
        def content = jsonParser.parseText(response.contentAsString)

        then: "Server returns right content"
        response.status == HttpStatus.BAD_REQUEST.value()
        content.errors[missingField] != null

        where:
        body                                                                                                                                                     | missingField
        """
            {
                "labels": ["work"],
                "startDate": "${ZonedDateTime.now().minusHours(1).toString()}",
                "endDate": "${ZonedDateTime.now().toString()}"
            }
        """ | "title"
        """
            {
                "title": "Meeting",
                "startDate": "${ZonedDateTime.now().minusHours(1).toString()}",
                "endDate": "${ZonedDateTime.now().toString()}"
            }
        """ | "labels"
        """
            {
                "title": "Meeting",
                "labels": ["work"]
            }
        """                                                                                                                                           | "startDate"
    }

    static class ActivityTestConfiguration {

        @Primary
        @Bean
        UrlProvider urlProvider() {
            return new UrlProviderProxy()
        }

    }

    static class UrlProviderProxy implements UrlProvider {

        @Delegate
        UrlProvider delegate

    }

}
