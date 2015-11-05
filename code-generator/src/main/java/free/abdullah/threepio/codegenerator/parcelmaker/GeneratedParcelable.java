package free.abdullah.threepio.codegenerator.parcelmaker;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JFieldRef;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JInvocation;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JOp;
import com.sun.codemodel.JStatement;
import com.sun.codemodel.JType;
import com.sun.codemodel.JVar;

import javax.lang.model.element.Element;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.TypeMirror;

import free.abdullah.threepio.codegenerator.TConsts;
import free.abdullah.threepio.codegenerator.TMessager;
import free.abdullah.threepio.codegenerator.TModelFactory;

public class GeneratedParcelable {

    private final Element baseClass;
    private final TModelFactory factory;
    private final JCodeModel codeModel;
    private final TMessager messager;

    private JDefinedClass parcelable;
    private JBlock writeBlock;
    private JBlock readBlock;
    private JVar inVar;
    private JVar destVar;

    public GeneratedParcelable(Element baseClass, TModelFactory factory, TMessager messager) {
        this.baseClass = baseClass;
        this.factory = factory;
        this.codeModel = factory.getCodeModel();
        this.messager = messager;

        createParcelableClass();
        createConstructor();
        createWriteToParcel();
        createDescribeContent();
        createCreatorMember();
    }

    public JVar getInVar() {
        return inVar;
    }

    public JVar getDestVar() {
        return destVar;
    }

    public JBlock getWriteBlock() {
        return writeBlock;
    }

    public JBlock getReadBlock() {
        return readBlock;
    }

    // <editor-fold desc="Read Write Methods">
    public Void addStatements(VariableElement fieldElement, String readMethod, String writeMethod) {
        JFieldRef field = JExpr.ref(fieldElement.getSimpleName().toString());

        readBlock.assign(field, JExpr.invoke(inVar, readMethod));
        writeBlock.invoke(destVar, writeMethod).arg(field);
        return null;
    }

    public Void addBooleanStatements(VariableElement element) {
        JFieldRef field = JExpr.ref(element.getSimpleName().toString());
        readBlock.assign(field, JExpr.invoke(inVar, "readInt").eq(JExpr.lit(1)));
        writeBlock.invoke(destVar, "writeInt").arg(JOp.cond(field, JExpr.lit(1), JExpr.lit(0))) ;
        return null;
    }

    public Void addLoadableStatements(VariableElement element, String readMethod, String writeMethod) {
        JFieldRef field = JExpr.ref(element.getSimpleName().toString());
        JFieldRef flags = JExpr.ref("flags");

        writeBlock.invoke(destVar, writeMethod)
                  .arg(field)
                  .arg(flags);
        readBlock.assign(field,
                JExpr.invoke(inVar, readMethod)
                     .arg(getClassLoaderExpression(element)));
        return null;
    }
    // </editor-fold>

    // <editor-fold desc="Private Methods">
    private void createParcelableClass() {
        String baseFullName = baseClass.asType().toString();
        parcelable = factory.create(baseFullName + "Ext");
        parcelable._extends(factory.ref(baseFullName));
        parcelable._implements(factory.ref(Const.PARCELABLE));
    }

    private void createConstructor() {
        JClass Parcel = factory.ref(Const.PARCEL);

        JMethod constructor = parcelable.constructor(JMod.PROTECTED);
        inVar = constructor.param(Parcel, "in");
        readBlock = constructor.body();
    }

    private void createDescribeContent() {
        JType integer = codeModel._ref(int.class);

        JMethod describeContent = parcelable.method(JMod.PUBLIC, integer, "describeContents");
        describeContent.annotate(factory.ref(TConsts.OVERRIDE));
        describeContent.body()._return(JExpr.lit(0));
    }

    private void createWriteToParcel() {
        JType Void = codeModel._ref(void.class);
        JType Integer = codeModel._ref(int.class);
        JClass Parcel = factory.ref(Const.PARCEL);

        JMethod wtp = parcelable.method(JMod.PUBLIC, Void, "writeToParcel");
        wtp.annotate(factory.ref(TConsts.OVERRIDE));
        destVar = wtp.param(Parcel, "dest");
        wtp.param(Integer, "flags");

        writeBlock = wtp.body();
    }

    private void createCreatorMember() {
        JClass Parcel = factory.ref(Const.PARCEL);
        JClass Override = factory.ref(TConsts.OVERRIDE);

        JClass creatorInterface = factory.ref(Const.CREATOR);
        creatorInterface = creatorInterface.narrow(parcelable);
        JDefinedClass anonymousClass = codeModel.anonymousClass(creatorInterface);

        JMethod createInstanceMethod = anonymousClass.method(JMod.PUBLIC, parcelable, "createFromParcel");
        JVar json = createInstanceMethod.param(Parcel, "in");
        createInstanceMethod.annotate(Override);
        createInstanceMethod.body()._return(JExpr._new(parcelable).arg(json));

        JMethod createArrayMethod = anonymousClass.method(JMod.PUBLIC, parcelable.array(), "newArray");
        JVar size = createArrayMethod.param(codeModel.INT, "size");
        createArrayMethod.annotate(Override);
        createArrayMethod.body()._return(JExpr.newArray(parcelable, size));

        JFieldVar creatorField =
                parcelable.field(JMod.PUBLIC | JMod.STATIC, anonymousClass, "CREATOR", JExpr._new(anonymousClass));

    }

    private JExpression getClassLoaderExpression(VariableElement element) {
        TypeMirror type = element.asType();
        JClass dc = null;
        if(type instanceof ArrayType) {
            ArrayType arrayType = (ArrayType) type;
            dc = factory.ref(arrayType.getComponentType().toString());
            return dc.staticRef("CREATOR");
        } else {
            return JExpr.dotclass(factory.ref(type.toString())).invoke("getClassLoader");
        }
    }

    // </editor-fold>
}
