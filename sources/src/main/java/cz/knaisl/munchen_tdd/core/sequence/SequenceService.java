package cz.knaisl.munchen_tdd.core.sequence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class SequenceService {

    @Autowired
    private MongoOperations mongo;

    public long getNextSequence(Class classz) {
        Sequence sequence = mongo.findAndModify(
                query(where("id").is(classz.getName())),
                new Update().inc("seq", 1),
                options().returnNew(true).upsert(true),
                Sequence.class);

        return sequence.getSeq();
    }

}
