package free.abdullah.threepio.httpcomms.rest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by abdullah on 4/14/15.
 */
public interface JsonRequestObject {

    JSONObject toJson() throws JSONException;
}
