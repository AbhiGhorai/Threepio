package free.abdullah.threepio.autojson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by abdullah on 10/11/15.
 */
public interface JsonParseListener {

    void readJson(JSONObject in) throws JSONException;

    void writeJson(JSONObject out) throws JSONException;
}
