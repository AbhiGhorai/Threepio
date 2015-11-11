package free.abdullah.threepio;

import free.abdullah.threepio.autoparcel.AutoParcel;

/**
 * Created by abdullah on 2/11/15.
 */
@AutoParcel
public class NewParcelableBase {

    String string;

    public NewParcelableBase() {
        string = "Something here";
    }
}
