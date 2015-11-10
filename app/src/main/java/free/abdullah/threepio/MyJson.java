package free.abdullah.threepio;

import org.json.JSONException;
import org.json.JSONObject;

import free.abdullah.threepio.autojson.AutoJson;
import free.abdullah.threepio.autojson.JsonField;
import free.abdullah.threepio.autojson.JsonParseListener;
import free.abdullah.threepio.parcelmaker.AutoParcel;
import free.abdullah.threepio.parcelmaker.ParcelField;

/**
 * Created by abdullah on 9/11/15.
 */
@AutoParcel
@AutoJson
public class MyJson implements JsonParseListener {

    @ParcelField
    @JsonField
    int anInt;

    @ParcelField
    @JsonField
    long aLong;

    @ParcelField
    @JsonField(optional = true, defaultValue = "12.323")
    double aDouble;

    @ParcelField
    @JsonField(key = "new_value", optional = true, defaultValue = "Something great")
    String string;

    @ParcelField
    @JsonField
    int[] ints;

    @ParcelField
    @JsonField
    long[] longs;

    @ParcelField
    @JsonField
    boolean[] booleans;

    @ParcelField
    @JsonField(optional = true)
    double[] doubles;

    @ParcelField
    @JsonField
    String[] strings;

    void some() {
        JSONObject j;

    }

    @Override
    public void readJson(JSONObject in) throws JSONException {

    }

    @Override
    public void writeJson(JSONObject out) throws JSONException {

    }
}
