package free.abdullah.threepio;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Map;

import free.abdullah.threepio.parcelmaker.AutoParcel;
import free.abdullah.threepio.parcelmaker.ParcelField;

@AutoParcel
public class TestParcel implements Parcelable {


    @ParcelField
    int integer;

    @ParcelField
    long aLong;

    @ParcelField
    double aDouble;

    @ParcelField
    String string;

    @ParcelField
    NewParcelable parcelable;

    public TestParcel() {

    }

    protected TestParcel(Parcel in) {
        integer = in.readInt();
        aLong = in.readLong();
        aDouble = in.readDouble();
        string = in.readString();
        parcelable = in.readParcelable(NewParcelable.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(integer);
        dest.writeLong(aLong);
        dest.writeDouble(aDouble);
        dest.writeString(string);
        dest.writeParcelable(parcelable, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TestParcel> CREATOR = new Creator<TestParcel>() {
        @Override
        public TestParcel createFromParcel(Parcel in) {
            return new TestParcel(in);
        }

        @Override
        public TestParcel[] newArray(int size) {
            return new TestParcel[size];
        }
    };
}
