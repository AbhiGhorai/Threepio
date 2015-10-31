package free.abdullah.threepio.codegenerator;

import com.sun.codemodel.JCodeModel;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

public class TProcessor extends AbstractProcessor {

    ProcessingEnvironment environment;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.environment = processingEnv;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_7;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        HashSet<String> annotations = new HashSet<>();
        return annotations;
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        try {

        }catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private void writeGenerateClass(JCodeModel codeModel) {
        try {
            codeModel.build(new TCodeWriter(environment.getFiler()));
        } catch (IOException e) {

        }
    }
}
