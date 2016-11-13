package cz.knaisl.munchen_tdd.configuration

import com.github.fakemongo.Fongo
import com.mongodb.Mongo
import org.springframework.boot.test.context.TestConfiguration

@TestConfiguration
class MongoTestConfiguration extends MongoConfiguration {

    @Override
    public Mongo mongo() throws Exception {
        return new Fongo(getDatabaseName()).getMongo();
    }
}
