package free.abdullah.threepio.codegenerator.inject.activity;

import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.type.TypeMirror;

import free.abdullah.threepio.codegenerator.TGenerator;
import free.abdullah.threepio.codegenerator.TModelFactory;

/**
 * Created by abdullah on 11/11/15.
 */
public class ActivityGenerator extends TGenerator {

    public ActivityGenerator(ProcessingEnvironment environment, TModelFactory modelFactory) {
        super(environment, modelFactory);
    }

    @Override
    public String getSupportedAnnotation() {
        return Const.TACTIVITY;
    }

    @Override
    public Set<String> getSupportedOptions() {
        return super.getSupportedOptions();
    }

    @Override
    public void process(Element element) {
        if(isValidElement(element)) {
            GeneratedActivity ga = new GeneratedActivity(element, modelFactory, messager, teUtils);
            ga.initialize();
        }
    }

    private boolean isValidElement(Element element) {
        if(element.getKind() == ElementKind.CLASS) {
            if(teUtils.isSubtype(element.asType(), Const.ACTIVITY)) {
                return true;
            }
            messager.printError("TActivity can only be applied to sub-class of Activity", element);
        }
        return false;
    }
}
