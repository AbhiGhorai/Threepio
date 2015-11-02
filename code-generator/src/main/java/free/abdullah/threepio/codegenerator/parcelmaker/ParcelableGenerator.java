package free.abdullah.threepio.codegenerator.parcelmaker;

import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

import free.abdullah.threepio.codegenerator.TGenerator;
import free.abdullah.threepio.codegenerator.TModelFactory;

public class ParcelableGenerator extends TGenerator {

    public ParcelableGenerator(ProcessingEnvironment environment, TModelFactory modelFactory) {
        super(environment, modelFactory, Const.AUTO_PARCEL);
    }

    @Override
    public void process(Element element) {

        if(isValidElement(element)) {
            GeneratedParcelable gp = new GeneratedParcelable(element, modelFactory);
            ParcelableFieldProcessor fieldProcessor = new ParcelableFieldProcessor(teUtils, messager, gp);

            for(Element field : element.getEnclosedElements()) {
                if(isValidField(field)) {
                    field.asType().accept(fieldProcessor, (VariableElement) field);
                }
            }
        }
    }

    // <editor-fold desc="Private Methods>
    private boolean isValidElement(Element element) {
        ElementKind elementKind = element.getKind();
        if(!elementKind.equals(ElementKind.CLASS)) {
            messager.printError("AutoParcel can only be applied to class type.", element);
            return false;
        }
        return true;
    }

    private boolean isValidField(Element fieldElement) {
        TypeElement parcelField = teUtils.getTypeElement(Const.PARCEL_FIELD);
        if(!teUtils.hasAnnotation(fieldElement, parcelField)) {
            return false;
        }

        Set<Modifier> modifiers = fieldElement.getModifiers();
        if(modifiers.contains(Modifier.FINAL) ||
                modifiers.contains(Modifier.PRIVATE) ||
                modifiers.contains(Modifier.STATIC)) {
            messager.printError("JsonField can not be applied to private, final and static fields", fieldElement);
            return false;
        }
        return true;
    }
    // </editor-fold>
}
