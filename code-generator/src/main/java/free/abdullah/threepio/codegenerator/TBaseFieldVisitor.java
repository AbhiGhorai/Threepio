package free.abdullah.threepio.codegenerator;

import java.util.List;

import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.SimpleTypeVisitor7;

public class TBaseFieldVisitor<T> extends SimpleTypeVisitor7<T, VariableElement> {

    protected final TEUtils teUtils;

    protected final TypeMirror StringMirror;
    protected final TypeMirror ListMirror;
    protected final TypeMirror MapMirror;

    public TBaseFieldVisitor(TEUtils teUtils) {
        this.teUtils = teUtils;

        StringMirror = teUtils.getTypeMirror(TConsts.STRING);
        ListMirror = teUtils.getTypeMirror(TConsts.LIST);
        MapMirror = teUtils.getTypeMirror(TConsts.MAP);
    }

    @Override
    public T visitDeclared(DeclaredType type, VariableElement element) {
        if(type.equals(StringMirror)) {
            return visitString(type, element);
        }
        else if(teUtils.isSubtype(type, ListMirror)) {
            return visitListInternal(type, element);
        }
        else if(teUtils.isSubtype(type, MapMirror)) {
            return visitMapInternal(type, element);
        }
        else if(teUtils.isBoxedPrimitiveType(type)) {
            return visitBoxedType(type, teUtils.getPrimitiveType(type), element);
        }
        return visitDeclaredExt(type, element);
    }

    public T visitString(DeclaredType type, VariableElement element) {
        return defaultAction(type, element);
    }

    public T visitList(DeclaredType type, TypeMirror paramType, VariableElement element) {
        return defaultAction(type, element);
    }

    public T visitMap(DeclaredType type, TypeMirror keyType, TypeMirror valueType, VariableElement element) {
        return defaultAction(type, element);
    }

    public T visitBoxedType(DeclaredType type, PrimitiveType primitiveType, VariableElement element) {
        return defaultAction(type, element);
    }

    public T visitDeclaredExt(DeclaredType type, VariableElement element) {
        return super.visitDeclared(type, element);
    }

    // <editor-fold desc="Private Methods" >
    public T visitListInternal(DeclaredType type, VariableElement element) {
        TypeMirror paramType = null;
        List<? extends TypeMirror> types =  type.getTypeArguments();
        if(types != null && types.size() > 0) {
            paramType = types.get(0);
        }
        return visitList(type, paramType, element);
    }

    public T visitMapInternal(DeclaredType type, VariableElement element) {
        TypeMirror keyType = null;
        TypeMirror valueType = null;
        List<? extends TypeMirror> types =  type.getTypeArguments();
        if(types != null && types.size() > 0) {
            keyType = types.get(0);
            valueType = types.get(1);
        }
        return visitMap(type, keyType, valueType, element);
    }
    // </editor-fold>
}
