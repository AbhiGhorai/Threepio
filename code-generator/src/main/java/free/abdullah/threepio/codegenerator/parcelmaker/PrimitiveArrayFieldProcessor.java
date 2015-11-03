package free.abdullah.threepio.codegenerator.parcelmaker;

import javax.lang.model.element.VariableElement;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.util.TypeKindVisitor7;

import free.abdullah.threepio.codegenerator.TEUtils;
import free.abdullah.threepio.codegenerator.TMessager;

/**
 * Created by abdullah on 3/11/15.
 */
public class PrimitiveArrayFieldProcessor extends TypeKindVisitor7<Void, VariableElement> {

    private final TEUtils teUtils;
    private final TMessager messager;
    private final GeneratedParcelable parcelable;

    public PrimitiveArrayFieldProcessor(TEUtils teUtils,
                                        TMessager messager,
                                        GeneratedParcelable parcelable) {
        this.teUtils = teUtils;
        this.messager = messager;
        this.parcelable = parcelable;
    }

    @Override
    public Void visitPrimitiveAsBoolean(PrimitiveType t, VariableElement element) {
        return parcelable.addStatements(element, "createBooleanArray", "writeBooleanArray");
    }

    @Override
    public Void visitPrimitiveAsByte(PrimitiveType t, VariableElement element) {
        return parcelable.addStatements(element, "createByteArray", "writeByteArray");
    }

    @Override
    public Void visitPrimitiveAsInt(PrimitiveType t, VariableElement element) {
        return parcelable.addStatements(element, "createIntArray", "writeIntArray");
    }

    @Override
    public Void visitPrimitiveAsLong(PrimitiveType t, VariableElement element) {
        return parcelable.addStatements(element, "createLongArray", "writeLongArray");
    }

    @Override
    public Void visitPrimitiveAsChar(PrimitiveType t, VariableElement element) {
        return parcelable.addStatements(element, "createCharArray", "writeCharArray");
    }

    @Override
    public Void visitPrimitiveAsFloat(PrimitiveType t, VariableElement element) {
        return parcelable.addStatements(element, "createFloatArray", "writeFloatArray");
    }

    @Override
    public Void visitPrimitiveAsDouble(PrimitiveType t, VariableElement element) {
        return parcelable.addStatements(element, "createDoubleArray", "writeDoubleArray");
    }
}
