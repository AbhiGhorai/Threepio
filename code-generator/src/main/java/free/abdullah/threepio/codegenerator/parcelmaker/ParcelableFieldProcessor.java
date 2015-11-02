package free.abdullah.threepio.codegenerator.parcelmaker;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JFieldRef;
import com.sun.codemodel.JInvocation;

import javax.lang.model.element.VariableElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ErrorType;
import javax.lang.model.type.PrimitiveType;

import free.abdullah.threepio.codegenerator.TEUtils;
import free.abdullah.threepio.codegenerator.TMessager;

public class ParcelableFieldProcessor extends BaseFieldVisitor {

    private final TMessager messager;
    private final GeneratedParcelable parcelable;

    private final PrimitiveFieldProcessor primitiveFieldProcessor;

    public ParcelableFieldProcessor(TEUtils teUtils,
                                    TMessager messager,
                                    GeneratedParcelable parcelable) {
        super(teUtils);
        this.messager = messager;
        this.parcelable = parcelable;

        messager.printNote("We are reaching construtor");
        primitiveFieldProcessor = new PrimitiveFieldProcessor(teUtils, messager, parcelable);
    }

    @Override
    public Void visitPrimitive(PrimitiveType type, VariableElement element) {
        return type.accept(primitiveFieldProcessor, element);
    }

    @Override
    public Void visitArray(ArrayType t, VariableElement element) {
        return super.visitArray(t, element);
    }

    @Override
    public Void visitString(DeclaredType type, VariableElement element) {
        return parcelable.addStatements(element, "readString", "writeString");
    }

    @Override
    public Void visitParcelable(DeclaredType type, VariableElement element) {
        JFieldRef flags = JExpr.ref("flags");
        JFieldRef field = JExpr.ref(element.getSimpleName().toString());

        JBlock writeBlock = parcelable.getWriteBlock();
        writeBlock
                .invoke(parcelable.getDestVar(), "writeParcelable")
                .arg(field)
                .arg(flags);

        JBlock readBlock = parcelable.getReadBlock();
        JInvocation read = JExpr.invoke(parcelable.getInVar(), "readParcelable");

        readBlock
                .assign(field, );

        return parcelable.addStatements(element, "readParcelable", "writeParcelable");
    }

    @Override
    public Void visitError(ErrorType t, VariableElement element) {

        if(teUtils.isSubtype(t, StringMirror)) {
            return visitParcelable(t, element);
        } else {
            messager.printNote("Error occured " + element.getSimpleName());
            messager.printNote("Error occured " + t.toString());
        }
        return super.visitError(t, element);
    }
}
