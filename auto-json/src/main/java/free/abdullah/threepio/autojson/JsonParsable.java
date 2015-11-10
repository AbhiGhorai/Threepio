package free.abdullah.threepio.autojson;

import org.json.JSONException;
import org.json.JSONObject;

public interface JsonParsable {

    JSONObject toJson() throws JSONException;

    interface JsonCreator<T extends JsonParsable> {

        T create(JSONObject json) throws JSONException;

        T[] createArray(int size);
    }
}
