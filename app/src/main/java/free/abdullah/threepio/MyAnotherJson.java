package free.abdullah.threepio;

import java.util.List;

import free.abdullah.threepio.autojson.AutoJson;
import free.abdullah.threepio.autojson.JsonField;
import free.abdullah.threepio.autoparcel.AutoParcel;
import free.abdullah.threepio.autoparcel.ParcelIgnore;

/**
 * Created by abdullah on 10/11/15.
 */
@AutoParcel
@AutoJson
public class MyAnotherJson {

    @JsonField
    MyJsonExt jsonExt;

    @JsonField(key = "My_Array", optional = true)
    MyJsonExt[] jsonExts;

    @ParcelIgnore
    @JsonField(key = "My_List", optional = false)
    List<MyJsonExt> jsonExts1;
}
