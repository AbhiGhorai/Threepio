package free.abdullah.threepio;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by abdullah on 31/10/15.
 */
public class TestParcel implements Parcelable {

    int code;
    Integer coder;

    protected TestParcel(Parcel in) {
        code = in.readInt();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(code);
    }
}
