package free.abdullah.threepio.codegenerator.inject.activity;

import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JVar;

import javax.lang.model.element.Element;

import free.abdullah.threepio.codegenerator.TEUtils;
import free.abdullah.threepio.codegenerator.TModelFactory;

/**
 * Created by abdullah on 11/11/15.
 */
class ActivityMethodCreator {

    private final TEUtils teUtils;
    private final JDefinedClass activity;
    private final TModelFactory factory;

    public ActivityMethodCreator(JDefinedClass activity, TEUtils teUtils, TModelFactory factory) {
        this.teUtils = teUtils;
        this.activity = activity;
        this.factory = factory;
    }

    public JMethod createOnCreate() {
        JMethod onCreate = activity.method(JMod.PROTECTED, void.class, "onCreate");
        onCreate.annotate(Override.class);

        JVar sis = onCreate.param(factory.ref(Const.BUNDLE), "savedInstanceState");
        onCreate.body().invoke(JExpr._super(), "onCreate").arg(sis);

        return onCreate;
    }
}
