package free.abdullah.threepio.codegenerator.parcelmaker;

import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ErrorType;
import javax.lang.model.type.TypeMirror;

import free.abdullah.threepio.codegenerator.TBaseFieldVisitor;
import free.abdullah.threepio.codegenerator.TEUtils;

/**
 * Created by abdullah on 2/11/15.
 */
public class BaseFieldVisitor extends TBaseFieldVisitor<Void> {

    protected TypeMirror parcelableMirror;
    protected TypeMirror serializableMirror;
    protected TypeMirror binderMirror;

    public BaseFieldVisitor(TEUtils teUtils) {
        super(teUtils);

        parcelableMirror = teUtils.getTypeMirror(Const.PARCELABLE);
        serializableMirror = teUtils.getTypeMirror(Const.SERIALIZABLE);
        binderMirror = teUtils.getTypeMirror(Const.IBINDER);
    }

    @Override
    public Void visitDeclaredExt(DeclaredType type, VariableElement element) {
        if(type.equals(parcelableMirror)) {
            return visitParcelable(type, element);
        }
        else if(type.equals(serializableMirror)) {
            return visitSerializable(type, element);
        }
        else if(type.equals(binderMirror)) {
            return visitIBinder(type, element);
        }
        return super.visitDeclaredExt(type, element);
    }

    public Void visitParcelable(DeclaredType type, VariableElement element) {
        return defaultAction(type, element);
    }

    public Void visitSerializable(DeclaredType type, VariableElement element) {
        return defaultAction(type, element);
    }

    public Void visitIBinder(DeclaredType type, VariableElement element) {
        return defaultAction(type, element);
    }
}
