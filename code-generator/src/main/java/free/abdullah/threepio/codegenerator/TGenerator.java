package free.abdullah.threepio.codegenerator;

import com.sun.codemodel.JCodeModel;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;

/**
 * Created by abdullah on 31/10/15.
 */
public abstract class TGenerator {

    private final ProcessingEnvironment environment;
    private final TModelFactory modelFactory;

    private final TEUtils teUtils;
    private final TMessager messager;
    private final JCodeModel codeModel;

    public TGenerator(ProcessingEnvironment environment, TModelFactory modelFactory) {
        this.environment = environment;
        this.modelFactory = modelFactory;
        this.teUtils = new TEUtils(environment);
        this.messager = new TMessager(environment);
        this.codeModel = modelFactory.getCodeModel();
    }

    public abstract void process(Element element);
}
