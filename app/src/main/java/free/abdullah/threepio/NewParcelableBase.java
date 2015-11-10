package free.abdullah.threepio;

import free.abdullah.threepio.autoparcel.AutoParcel;
import free.abdullah.threepio.autoparcel.ParcelField;

/**
 * Created by abdullah on 2/11/15.
 */
@AutoParcel
public class NewParcelableBase {

    @ParcelField
    String string;

    public NewParcelableBase() {
        string = "Something here";
    }
}
