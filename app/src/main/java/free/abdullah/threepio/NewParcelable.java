package free.abdullah.threepio;

import android.os.Parcel;
import android.os.Parcelable;

import free.abdullah.threepio.parcelmaker.AutoParcel;

/**
 * Created by abdullah on 2/11/15.
 */
@AutoParcel
public class NewParcelable implements Parcelable {

    public NewParcelable() {

    }

    protected NewParcelable(Parcel in) {
    }

    public static final Creator<NewParcelable> CREATOR = new Creator<NewParcelable>() {
        @Override
        public NewParcelable createFromParcel(Parcel in) {
            return new NewParcelable(in);
        }

        @Override
        public NewParcelable[] newArray(int size) {
            return new NewParcelable[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
