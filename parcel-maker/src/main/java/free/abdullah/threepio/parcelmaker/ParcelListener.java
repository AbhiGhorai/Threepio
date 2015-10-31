package free.abdullah.threepio.parcelmaker;

import android.os.Parcel;

public interface ParcelListener {

    void readFromParcel(Parcel in);

    void writeToParcel(Parcel out);
}
