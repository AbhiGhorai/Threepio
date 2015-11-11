package free.abdullah.threepio.codegenerator.autojson;

import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.TypeKindVisitor7;

import free.abdullah.threepio.codegenerator.TConsts;
import free.abdullah.threepio.codegenerator.TEUtils;
import free.abdullah.threepio.codegenerator.TMessager;

/**
 * Created by abdullah on 10/11/15.
 */
public class AnnotationUtil {

    private static final String KEY = "key";
    private static final String OPTIONAL = "optional";
    private static final String DEFAULT_VALUE = "defaultValue";

    private final TEUtils teUtils;
    private final TypeMirror fieldAnnotation;

    public AnnotationUtil(TEUtils teUtils) {
        this.teUtils = teUtils;
        this.fieldAnnotation = teUtils.getTypeMirror(Const.JSON_FIELD);
    }

    public JsonFieldMetadata getFieldMetadata(VariableElement field, TMessager messager) {
        JsonFieldMetadata metadata = new JsonFieldMetadata();

        AnnotationMirror am = teUtils.getAnnotationMirror(field, fieldAnnotation);
        if(am != null) {
            AnnotationValue av = teUtils.getAnnotationValue(field, fieldAnnotation, KEY);
            if(av != null) {
                metadata.key = (String) av.getValue();
            } else {
                metadata.key = field.getSimpleName().toString();
            }

            av = teUtils.getAnnotationValue(field, fieldAnnotation, OPTIONAL);
            if(av != null) {
                metadata.optional = (boolean) av.getValue();
            } else {
                metadata.optional = false;
            }

            av = teUtils.getAnnotationValue(field, fieldAnnotation, DEFAULT_VALUE);
            if(av != null) {
                String value = (String) av.getValue();
                metadata.defaultValue = getDefaultExpression(field, value);
            }
        }
        return metadata;
    }

    private JExpression getDefaultExpression(VariableElement field, String value) {
        if(teUtils.isSameType(field.asType(), teUtils.getTypeMirror(TConsts.STRING))) {
            return JExpr.lit(value);
        }

        return field.asType().accept(new TypeKindVisitor7<JExpression, String>() {

            @Override
            public JExpression visitPrimitiveAsBoolean(PrimitiveType t, String s) {
                return JExpr.lit(Boolean.valueOf(s));
            }

            @Override
            public JExpression visitPrimitiveAsInt(PrimitiveType t, String s) {
                return JExpr.lit(Integer.valueOf(s));
            }

            @Override
            public JExpression visitPrimitiveAsLong(PrimitiveType t, String s) {
                return JExpr.lit(Long.valueOf(s));
            }

            @Override
            public JExpression visitPrimitiveAsDouble(PrimitiveType t, String s) {
                return JExpr.lit(Double.valueOf(s));
            }
        }, value);
    }
}
