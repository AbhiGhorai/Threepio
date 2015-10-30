package free.abdullah.threepio.httpcomms.rest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by abdullah on 4/14/15.
 */
public interface JsonResponseObject {

    void fromJson(JSONObject object) throws JSONException;
}
