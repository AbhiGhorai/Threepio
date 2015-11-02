package free.abdullah.threepio.codegenerator.parcelmaker;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JFieldRef;
import com.sun.codemodel.JInvocation;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JStatement;
import com.sun.codemodel.JType;
import com.sun.codemodel.JVar;

import javax.lang.model.element.Element;
import javax.lang.model.element.VariableElement;

import free.abdullah.threepio.codegenerator.TConsts;
import free.abdullah.threepio.codegenerator.TModelFactory;

public class GeneratedParcelable {

    private final Element baseClass;
    private final TModelFactory factory;
    private final JCodeModel codeModel;

    private JDefinedClass parcelable;
    private JBlock writeBlock;
    private JBlock readBlock;
    private JVar inVar;
    private JVar destVar;

    public GeneratedParcelable(Element baseClass, TModelFactory factory) {
        this.baseClass = baseClass;
        this.factory = factory;
        this.codeModel = factory.getCodeModel();

        createParcelableClass();
        createConstructor();
        createWriteToParcel();
        createDescribeContent();
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

    public Void addStatements(VariableElement fieldElement, String readMethod, String writeMethod) {
        JFieldRef field = JExpr.ref(fieldElement.getSimpleName().toString());

        readBlock.assign(field, JExpr.invoke(inVar, readMethod));
        writeBlock.invoke(destVar, writeMethod).arg(field);
        return null;
    }

    public void addWriteStatement(JStatement statement) {
        writeBlock.add(statement);
    }

    public void addReadStatement(JStatement statement) {
        readBlock.add(statement);
    }

    // <editor-fold desc="Private Methods">
    private void createParcelableClass() {
        String baseFullName = baseClass.asType().toString();
        parcelable = factory.create(baseFullName + "Ext");
        parcelable._extends(factory.ref(baseFullName));
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
    // </editor-fold>
}
