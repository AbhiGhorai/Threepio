package free.abdullah.threepio.database;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface DatabaseColumn {

    String name();

    String datatype();
}
