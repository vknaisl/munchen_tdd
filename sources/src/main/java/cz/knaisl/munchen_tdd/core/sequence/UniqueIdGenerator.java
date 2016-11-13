package cz.knaisl.munchen_tdd.core.sequence;

import java.util.UUID;

public class UniqueIdGenerator {

    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
