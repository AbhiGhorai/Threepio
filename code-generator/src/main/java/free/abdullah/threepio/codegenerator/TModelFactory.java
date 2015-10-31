package free.abdullah.threepio.codegenerator;

import com.sun.codemodel.JClass;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by abdulmunaf on 8/8/15.
 */
public class TModelFactory {

    private final JCodeModel codeModel;
    private final Map<String, JClass> classes;

    public TModelFactory(JCodeModel codeModel) {
        this.codeModel = codeModel;
        this.classes = new HashMap<String, JClass>();
    }

    public JCodeModel getCodeModel() {
        return codeModel;
    }

    public JClass ref(String fullyQualifiedName) {
        if(classes.containsKey(fullyQualifiedName)) {
            return classes.get(fullyQualifiedName);
        }
        JClass newClass = codeModel.ref(fullyQualifiedName);
        classes.put(fullyQualifiedName, newClass);
        return newClass;
    }

    public JDefinedClass create(String fullyQualifiedName)  {
        try {
            return codeModel._class(fullyQualifiedName);
        } catch (JClassAlreadyExistsException e) {
            return codeModel._getClass(fullyQualifiedName);
        }
    }
}
