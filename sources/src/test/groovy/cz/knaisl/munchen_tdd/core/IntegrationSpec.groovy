package cz.knaisl.munchen_tdd.core

import cz.knaisl.munchen_tdd.Application
import cz.knaisl.munchen_tdd.Profiles
import cz.knaisl.munchen_tdd.configuration.MongoTestConfiguration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootContextLoader
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup

@ActiveProfiles(Profiles.TEST)
@ContextConfiguration(classes = [Application, MongoTestConfiguration], loader = SpringBootContextLoader)
@SpringBootTest(webEnvironment = MOCK)
abstract public class IntegrationSpec extends Specification {

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @Autowired
    protected MongoTemplate mongoTemplate;

    protected MockMvc mockMvc;

    def setup() throws Exception {
        mongoTemplate.getDb().dropDatabase();
        mockMvc = webAppContextSetup(webApplicationContext)
                .alwaysDo(print()).build();
    }
}
