package free.abdullah.threepio;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcel;

import org.json.JSONObject;

import java.util.ArrayList;

import free.abdullah.threepio.autoparcel.AutoParcel;
import free.abdullah.threepio.autoparcel.ParcelField;
import free.abdullah.threepio.autoparcel.ParcelListener;

@AutoParcel
public class TestParcelBase implements ParcelListener {

    @ParcelField
    int[] integer;

    @ParcelField
    long[] aLong;

    @ParcelField
    double[] aDouble;

    @ParcelField
    String[] string;

//    @ParcelField
//    NewParcelable parcelable;
//
//    @ParcelField
//    NewParcelable[] parcelableArray;

    @ParcelField
    Bitmap bitmap;

    @ParcelField
    Bundle bundle;

    @ParcelField
    Bundle[] bundles;

    @ParcelField
    boolean aBoolean;

//    @ParcelField
//    List<NewParcelable> parcelables;

    @ParcelField
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
