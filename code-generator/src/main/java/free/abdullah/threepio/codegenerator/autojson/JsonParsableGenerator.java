package free.abdullah.threepio.codegenerator.autojson;

import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

import free.abdullah.threepio.codegenerator.TGenerator;
import free.abdullah.threepio.codegenerator.TModelFactory;

/**
 * Created by abdullah on 9/11/15.
 */
public class JsonParsableGenerator extends TGenerator {

    private static final String ADD_SUFFIX = "addJsonParsableSuffix";
    private static final String REMOVE_SUFFIX = "removeJsonParsableSuffix";

    public JsonParsableGenerator(ProcessingEnvironment environment, TModelFactory modelFactory) {
        super(environment, modelFactory);
    }

    @Override
    public String getSupportedAnnotation() {
        return Const.AUTO_JSON;
    }

    @Override
    public Set<String> getSupportedOptions() {
        return super.getSupportedOptions();
    }

    @Override
    public void process(Element element) {
        if(isValidElement(element)) {
            GeneratedJsonParsable gp = new GeneratedJsonParsable(element, modelFactory, messager, teUtils);
            JsonFieldProcessor processor = new JsonFieldProcessor(teUtils, messager, gp);
            if(gp.initialize(getRemoveSuffix(), getAddSuffix())) {
                for (Element field : element.getEnclosedElements()) {
                    if (isValidField(field)) {
                        field.asType().accept(processor, (VariableElement) field);
                    }
                }
                gp.finalizeGenerate();
            }
        }
    }

    private boolean isValidElement(Element element) {
        ElementKind elementKind = element.getKind();
        if(!elementKind.equals(ElementKind.CLASS)) {
            messager.printError("AutoJson can only be applied to class type.", element);
            return false;
        }
        return true;
    }

    private boolean isValidField(Element fieldElement) {
        TypeElement jsonField = teUtils.getTypeElement(Const.JSON_FIELD);
        if(!teUtils.hasAnnotation(fieldElement, jsonField)) {
            return false;
        }

        Set<Modifier> modifiers = fieldElement.getModifiers();
        if(modifiers.contains(Modifier.FINAL) ||
                modifiers.contains(Modifier.PRIVATE) ||
                modifiers.contains(Modifier.STATIC)) {
            messager.printError("JsonField can not be applied to private, final and static fields", fieldElement);
        }
        return true;
    }

    private String getAddSuffix() {
        return environment.getOptions().get(ADD_SUFFIX);
    }

    private String getRemoveSuffix() {
        return environment.getOptions().get(REMOVE_SUFFIX);
    }
}
