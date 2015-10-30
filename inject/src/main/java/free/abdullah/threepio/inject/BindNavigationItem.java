package free.abdullah.threepio.inject;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by abdulmunaf on 6/7/15.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface BindNavigationItem {

    int menuId();
}
