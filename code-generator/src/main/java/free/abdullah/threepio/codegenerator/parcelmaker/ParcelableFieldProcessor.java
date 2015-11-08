package free.abdullah.threepio.codegenerator.parcelmaker;

import javax.lang.model.element.VariableElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeMirror;

import free.abdullah.threepio.codegenerator.TEUtils;
import free.abdullah.threepio.codegenerator.TMessager;

public class ParcelableFieldProcessor extends BaseFieldVisitor {


    private final PrimitiveFieldProcessor primitiveFieldProcessor;
    private final ArrayFieldProcessor arrayFieldProcessor;
    private final ListFieldProcessor listFieldProcessor;

    public ParcelableFieldProcessor(TEUtils teUtils,
                                    TMessager messager,
                                    GeneratedParcelable parcelable) {
        super(teUtils, messager, parcelable);

        primitiveFieldProcessor = new PrimitiveFieldProcessor(teUtils, messager, parcelable);
        arrayFieldProcessor = new ArrayFieldProcessor(teUtils, messager, parcelable);
        listFieldProcessor = new ListFieldProcessor(teUtils, messager, parcelable);
    }

    @Override
    public Void visitPrimitive(PrimitiveType type, VariableElement element) {
        return type.accept(primitiveFieldProcessor, element);
    }

    @Override
    public Void visitArray(ArrayType t, VariableElement element) {
        return t.getComponentType().accept(arrayFieldProcessor, element);
    }

    @Override
    public Void visitString(DeclaredType type, VariableElement element) {
        return parcelable.addStatements(element, "readString", "writeString");
    }

    @Override
    public Void visitParcelable(DeclaredType type, VariableElement element) {
        return parcelable.addLoadableStatements(element, "readParcelable", "writeParcelable");
    }

    @Override
    public Void visitSerializable(DeclaredType type, VariableElement element) {
        messager.printError("Serializable is not supported", element);
        return defaultAction(type, element);
        //return parcelable.addStatements(element, "readSerializable", "writeSerializable");
    }

    @Override
    public Void visitBundle(DeclaredType type, VariableElement element) {
        return parcelable.addStatements(element, "readBundle", "writeBundle");
    }

    @Override
    public Void visitIBinder(DeclaredType type, VariableElement element) {
        messager.printError("IBinder is not supported", element);
        return defaultAction(type, element);
//        return parcelable.addStatements(element, "readStrongBinder", "writeStrongBinder");
    }

    @Override
    public Void visitList(DeclaredType type, TypeMirror paramType, VariableElement element) {
        if(paramType == null) {
            messager.printError("Raw List is not supported", element);
            return null;
        } else {
            return paramType.accept(listFieldProcessor, element);
        }
    }
}
