package free.abdullah.threepio;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Map;

import free.abdullah.threepio.parcelmaker.AutoParcel;
import free.abdullah.threepio.parcelmaker.ParcelField;

@AutoParcel
public class TestParcel implements Parcelable {


    @ParcelField
    int[] integer;

    @ParcelField
    long[] aLong;

    @ParcelField
    double[] aDouble;

    @ParcelField
    String[] string;

    @ParcelField
    NewParcelableExt parcelable;

    @ParcelField
    NewParcelableExt[] parcelableArray;

    public TestParcel() {

    }

    protected TestParcel(Parcel in) {
        integer = in.createIntArray();
        aLong = in.createLongArray();
        aDouble = in.createDoubleArray();
        string = in.createStringArray();
        parcelable = in.readParcelable(NewParcelableExt.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeIntArray(integer);
        dest.writeLongArray(aLong);
        dest.writeDoubleArray(aDouble);
        dest.writeStringArray(string);
        dest.writeParcelable(parcelable, flags);
        dest.writeTypedArray(parcelableArray, flags);
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
