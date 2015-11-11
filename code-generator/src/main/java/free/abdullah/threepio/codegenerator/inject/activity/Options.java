package free.abdullah.threepio.codegenerator.inject.activity;

import java.util.Map;

import free.abdullah.threepio.codegenerator.autojson.CaseOptions;

/**
 * Created by abdullah on 11/11/15.
 */
public class Options {
    public static final String ADD_SUFFIX = "addInjectSuffix";
    public static final String REMOVE_SUFFIX = "removeInjectSuffix";

    private final String addSuffix;
    private final String removeSuffix;

    public Options(Map<String, String> options) {
        addSuffix = options.get(ADD_SUFFIX);
        removeSuffix = options.get(REMOVE_SUFFIX);
    }

    public String getAddSuffix() {
        return addSuffix;
    }

    public String getRemoveSuffix() {
        return removeSuffix;
    }
}
