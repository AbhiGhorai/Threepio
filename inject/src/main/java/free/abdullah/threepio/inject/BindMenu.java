package free.abdullah.threepio.inject;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by abdullah on 5/6/15.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface BindMenu {

    int menuId();
}
