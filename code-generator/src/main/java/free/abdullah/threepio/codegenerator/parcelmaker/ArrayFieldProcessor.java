package free.abdullah.threepio.codegenerator.parcelmaker;

import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.PrimitiveType;

import free.abdullah.threepio.codegenerator.TEUtils;
import free.abdullah.threepio.codegenerator.TMessager;

/**
 * Created by abdullah on 3/11/15.
 */
public class ArrayFieldProcessor extends BaseFieldVisitor {

    private final PrimitiveArrayFieldProcessor primitiveProcessor;

    public ArrayFieldProcessor(TEUtils teUtils,
                               TMessager messager,
                               GeneratedParcelable parcelable) {
        super(teUtils, messager, parcelable);

        primitiveProcessor = new PrimitiveArrayFieldProcessor(teUtils, messager, parcelable);
    }

    @Override
    public Void visitDeclaredExt(DeclaredType type, VariableElement element) {
        return super.visitDeclaredExt(type, element);
    }

    @Override
    public Void visitParcelable(DeclaredType type, VariableElement element) {
        return parcelable.addLoadableStatements(element, "createTypedArray", "writeTypedArray");
    }

    @Override
    public Void visitSerializable(DeclaredType type, VariableElement element) {
        messager.printError("Serializable array is not supported", element);
        return defaultAction(type, element);
    }

    @Override
    public Void visitIBinder(DeclaredType type, VariableElement element) {
        return parcelable.addStatements(element, "createBinderArray", "writeBinderArray");
    }

    @Override
    public Void visitString(DeclaredType type, VariableElement element) {
        return parcelable.addStatements(element, "createStringArray", "writeStringArray");
    }

    @Override
    public Void visitPrimitive(PrimitiveType t, VariableElement element) {
        return t.accept(primitiveProcessor, element);
    }
}
