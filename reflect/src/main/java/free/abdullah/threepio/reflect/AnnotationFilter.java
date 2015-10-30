package free.abdullah.threepio.reflect;

import com.google.common.base.Predicate;

import java.lang.annotation.Annotation;

/**
 * Allows the filed to pass only if it has a specific annotation
 * present at run time.
 *
 * @author Abdullah
 */
public class AnnotationFilter<Bound extends Object> implements Predicate<BoundedMember<Bound>> {

    private Class<? extends Annotation> _annotationType;

    public AnnotationFilter(Class<? extends Annotation> annotationType) {
        _annotationType = annotationType;
    }

    @Override
    public boolean apply(BoundedMember<Bound> member) {
        return member.isAnnotationPresent(_annotationType);
    }
}
