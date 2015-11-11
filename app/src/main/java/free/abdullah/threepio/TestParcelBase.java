package free.abdullah.threepio;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcel;

import org.json.JSONObject;

import java.util.ArrayList;

import free.abdullah.threepio.autoparcel.AutoParcel;
import free.abdullah.threepio.autoparcel.ParcelListener;

@AutoParcel
public class TestParcelBase implements ParcelListener {

    int[] integer;

    long[] aLong;

    double[] aDouble;

    String[] string;

//    @ParcelField
//    NewParcelable parcelable;
//
//    @ParcelField
//    NewParcelable[] parcelableArray;

    Bitmap bitmap;

    Bundle bundle;

    Bundle[] bundles;

    boolean aBoolean;

//    @ParcelField
//    List<NewParcelable> parcelables;

    ArrayList<String> strings;

    public TestParcelBase() {
    }

    public TestParcelBase(String string) {

    }

    public TestParcelBase(JSONObject object, String str, int abs) {

    }

    protected TestParcelBase(Parcel in) {

    }

    @Override
    public void readFromParcel(Parcel in) {

    }

    @Override
    public void writeToParcel(Parcel out) {

    }
}
