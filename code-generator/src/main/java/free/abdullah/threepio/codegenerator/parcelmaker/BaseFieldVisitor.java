package free.abdullah.threepio.codegenerator.parcelmaker;

import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ErrorType;
import javax.lang.model.type.TypeMirror;

import free.abdullah.threepio.codegenerator.TBaseFieldVisitor;
import free.abdullah.threepio.codegenerator.TEUtils;
import free.abdullah.threepio.codegenerator.TMessager;

/**
 * Created by abdullah on 2/11/15.
 */
public class BaseFieldVisitor extends TBaseFieldVisitor<Void> {

    protected TypeMirror parcelableMirror;
    protected TypeMirror serializableMirror;
    protected TypeMirror binderMirror;
    protected TypeMirror bundleMirror;

    protected final TMessager messager;
    protected final GeneratedParcelable parcelable;

    public BaseFieldVisitor(TEUtils teUtils,
                            TMessager messager,
                            GeneratedParcelable parcelable) {
        super(teUtils);
        this.messager = messager;
        this.parcelable = parcelable;

        parcelableMirror = teUtils.getTypeMirror(Const.PARCELABLE);
        serializableMirror = teUtils.getTypeMirror(Const.SERIALIZABLE);
        binderMirror = teUtils.getTypeMirror(Const.IBINDER);
        bundleMirror = teUtils.getTypeMirror(Const.BUNDLE);
    }

    @Override
    public Void visitDeclaredExt(DeclaredType type, VariableElement element) {
        messager.printNote(type.toString());
        if(teUtils.isSubtype(type, parcelableMirror)) {
            return visitParcelable(type, element);
        }
        else if(teUtils.isSubtype(type, serializableMirror)) {
            return visitSerializable(type, element);
        }
        else if(teUtils.isSubtype(type, binderMirror)) {
            return visitIBinder(type, element);
        }
        else if(type.equals(bundleMirror)) {
            return visitBundle(type, element);
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

    public Void visitBundle(DeclaredType type, VariableElement element) {
        return defaultAction(type, element);
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
