package free.abdullah.threepio.reflect;

import android.support.annotation.NonNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Member;

/**
 * Created by abdullah on 5/6/15.
 */
public abstract class BoundedMember<Bound extends Object> {

    private Member member;
    protected Bound bound;

    public BoundedMember(Member member, Bound bound) {
        this.member = member;
        this.bound = bound;
    }

    public abstract <A extends Annotation> boolean isAnnotationPresent(@NonNull Class<A> annotationType);

    public abstract <A extends Annotation> A getAnnotation(@NonNull Class<A> annotationType);
}
