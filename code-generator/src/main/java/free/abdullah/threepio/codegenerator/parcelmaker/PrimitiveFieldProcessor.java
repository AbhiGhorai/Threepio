package free.abdullah.threepio.codegenerator.parcelmaker;

import com.sun.codemodel.JExpr;
import com.sun.codemodel.JFieldRef;
import com.sun.codemodel.JInvocation;
import com.sun.codemodel.JVar;

import javax.lang.model.element.VariableElement;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.util.TypeKindVisitor7;

import free.abdullah.threepio.codegenerator.TEUtils;
import free.abdullah.threepio.codegenerator.TMessager;

/**
 * Created by abdullah on 2/11/15.
 */
public class PrimitiveFieldProcessor extends TypeKindVisitor7<Void, VariableElement> {

    private final TEUtils teUtils;
    private final TMessager messager;
    private final GeneratedParcelable parcelable;

    private JVar inVar;
    private JVar destVar;

    public PrimitiveFieldProcessor(TEUtils teUtils,
                                   TMessager messager,
                                   GeneratedParcelable parcelable) {
        this.teUtils = teUtils;
        this.messager = messager;
        this.parcelable = parcelable;

        inVar = parcelable.getInVar();
        destVar = parcelable.getDestVar();
    }

    @Override
    public Void visitPrimitiveAsBoolean(PrimitiveType t, VariableElement element) {
        return super.visitPrimitiveAsBoolean(t, element);
    }

    @Override
    public Void visitPrimitiveAsByte(PrimitiveType t, VariableElement element) {
        return parcelable.addStatements(element, "readByte", "writeByte");
    }

    @Override
    public Void visitPrimitiveAsShort(PrimitiveType t, VariableElement element) {
        //TODO: Not supported
        return null;
    }

    @Override
    public Void visitPrimitiveAsInt(PrimitiveType t, VariableElement element) {
        return parcelable.addStatements(element, "readInt", "writeInt");
    }

    @Override
    public Void visitPrimitiveAsLong(PrimitiveType t, VariableElement element) {
        return parcelable.addStatements(element, "readLong", "writeLong");
    }

    @Override
    public Void visitPrimitiveAsChar(PrimitiveType t, VariableElement element) {
        //TODO : Not supported
        return null;

    }

    @Override
    public Void visitPrimitiveAsFloat(PrimitiveType t, VariableElement element) {
        return parcelable.addStatements(element, "readFloat", "writeFloat");
    }

    @Override
    public Void visitPrimitiveAsDouble(PrimitiveType t, VariableElement element) {
        return parcelable.addStatements(element, "readDouble", "writeDouble");
    }
}
