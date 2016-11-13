package cz.knaisl.munchen_tdd.configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class JacksonConfiguration {

    private static final String SPRING_HATEOAS_OBJECT_MAPPER = "_halObjectMapper";

//    @Autowired
//    @Qualifier(SPRING_HATEOAS_OBJECT_MAPPER)
//    private ObjectMapper springHateoasObjectMapper;

    @Primary
//    @Bean(name = "objectMapper")
    @Bean
    ObjectMapper objectMapper() {
        ObjectMapper springHateoasObjectMapper = new ObjectMapper();

        JavaTimeModule javaTimeModule=new JavaTimeModule();
        springHateoasObjectMapper.registerModules(javaTimeModule);

        springHateoasObjectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        springHateoasObjectMapper.disable(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS);

        springHateoasObjectMapper.disable(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS);
        springHateoasObjectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        springHateoasObjectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

        springHateoasObjectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        springHateoasObjectMapper.enable(SerializationFeature.WRITE_DATES_WITH_ZONE_ID);

        return springHateoasObjectMapper;
    }
}
