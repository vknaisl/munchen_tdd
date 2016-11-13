package cz.knaisl.munchen_tdd.configuration;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import cz.knaisl.munchen_tdd.Profiles;
import cz.knaisl.munchen_tdd.core.converter.DateToZonedDateTimeConverter;
import cz.knaisl.munchen_tdd.core.converter.ZonedDateTimeToDateConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.ArrayList;
import java.util.List;

@Profile({Profiles.DEV, Profiles.PROD})
@Configuration
@EnableMongoAuditing
@EnableMongoRepositories(basePackages = { MongoConfiguration.BASE_PACKAGE })
public class MongoConfiguration extends AbstractMongoConfiguration {

    public static final String BASE_PACKAGE = "cz.knaisl.munchen_tdd.features.activity.domain";

    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    @Value("${spring.data.mongodb.database}")
    private String databaseName;

    @Override
    protected String getDatabaseName() {
        return databaseName;
    }

    @Override
    public Mongo mongo() throws Exception {
        return new MongoClient(new MongoClientURI(mongoUri));
    }

    @Override
    public CustomConversions customConversions() {
        List<Converter<?, ?>> converterList = new ArrayList<>();
        converterList.add(new DateToZonedDateTimeConverter());
        converterList.add(new ZonedDateTimeToDateConverter());
        return new CustomConversions(converterList);
    }

    @Override
    protected String getMappingBasePackage() {
        return BASE_PACKAGE;
    }
}