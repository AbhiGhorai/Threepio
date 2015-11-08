package free.abdullah.threepio;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import java.util.Objects;

import free.abdullah.threepio.parcelmaker.AutoParcel;
import free.abdullah.threepio.parcelmaker.ParcelField;

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
