package free.abdullah.threepio;

import android.graphics.Bitmap;
import android.os.Bundle;

import free.abdullah.threepio.parcelmaker.AutoParcel;
import free.abdullah.threepio.parcelmaker.ParcelField;

@AutoParcel
public class TestParcel {

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

    @ParcelField
    Bitmap bitmap;

    @ParcelField
    Bundle bundle;

    @ParcelField
    Bundle[] bundles;

    @ParcelField
    boolean aBoolean;

    public TestParcel() {
    }

}
