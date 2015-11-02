package free.abdullah.threepio.codegenerator;

import com.sun.codemodel.JCodeModel;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;

/**
 * Created by abdullah on 31/10/15.
 */
public abstract class TGenerator {

    protected final ProcessingEnvironment environment;
    protected final TModelFactory modelFactory;

    protected final TEUtils teUtils;
    protected final TMessager messager;
    protected final JCodeModel codeModel;

    private final String supportedAnnotation;

    public TGenerator(ProcessingEnvironment environment,
                      TModelFactory modelFactory,
                      String supportedAnnotation) {
        this.environment = environment;
        this.modelFactory = modelFactory;
        this.teUtils = new TEUtils(environment);
        this.messager = new TMessager(environment);
        this.codeModel = modelFactory.getCodeModel();
        this.supportedAnnotation = supportedAnnotation;
    }

    public String getSupportedAnnotation() {
        return supportedAnnotation;
    }

    public abstract void process(Element element);
}
