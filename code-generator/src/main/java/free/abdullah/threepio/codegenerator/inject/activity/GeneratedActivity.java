package free.abdullah.threepio.codegenerator.inject.activity;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JVar;

import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;

import free.abdullah.threepio.codegenerator.TConsts;
import free.abdullah.threepio.codegenerator.TEUtils;
import free.abdullah.threepio.codegenerator.TGeneratedClass;
import free.abdullah.threepio.codegenerator.TMessager;
import free.abdullah.threepio.codegenerator.TModelFactory;

/**
 * Created by abdullah on 11/11/15.
 */
public class GeneratedActivity extends TGeneratedClass {

    private final Element baseClass;

    private ActivityMethodCreator methodCreator;

    protected GeneratedActivity(Element baseClass, TModelFactory factory, TMessager messager, TEUtils teUtils) {
        super(factory, messager, teUtils);
        this.baseClass = baseClass;
    }

    public void initialize() {
        createClassExtends(baseClass.asType().toString() + "Ext", baseClass.toString());

        methodCreator = new ActivityMethodCreator(generated, teUtils, factory);
        JMethod onCreate = methodCreator.createOnCreate();
        onCreate.body().invoke("setContentView").arg(JExpr.lit(layoutId()));
    }

    private int layoutId() {
        TypeMirror tactivity = teUtils.getTypeMirror(Const.TACTIVITY);
        AnnotationValue value = teUtils.getAnnotationValue(baseClass, tactivity, "layout");
        if(value != null) {
            messager.printNote(value.toString());
            return (int) value.getValue();
        }

        return 0;
    }
}
