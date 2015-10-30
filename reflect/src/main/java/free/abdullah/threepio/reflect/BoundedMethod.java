package free.abdullah.threepio.reflect;

import android.support.annotation.NonNull;

import com.google.common.base.Preconditions;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by abdullah on 5/6/15.
 */
public class BoundedMethod<Bound extends Object> extends BoundedMember<Bound> {

    private Method method;

    public BoundedMethod(Method method, Bound bound) {
        super(method, bound);
        this.method = method;
    }

    public String getName() {
        return method.getName();
    }

    public Method getMethod() {
        return method;
    }

    public Object invoke() throws InvocationTargetException, IllegalAccessException {
        return method.invoke(bound, (Object[]) null);
    }

    public Object invoke(Object... args) throws InvocationTargetException, IllegalAccessException {
        return method.invoke(bound, args);
    }

    public Object forceInvoke() throws InvocationTargetException {
        return forceInvoke((Object[]) null);
    }

    public Object forceInvoke(Object... args) throws InvocationTargetException {
        Object returnValue = null;
        boolean isAccessible = method.isAccessible();

        try {
            if(!isAccessible) {
                method.setAccessible(true);
            }
            returnValue = method.invoke(bound, args);

        } catch (IllegalAccessException e) {
            assert false : "Should not come here as we are explicitly setting the method accessible";
        } finally {
            if(!isAccessible) {
                method.setAccessible(false);
            }
        }

        return returnValue;
    }

    @Override
    public <A extends Annotation> boolean isAnnotationPresent(@NonNull Class<A> annotationType) {
        Preconditions.checkNotNull(annotationType);

        return method.isAnnotationPresent(annotationType);
    }

    @Override
    public <A extends Annotation> A getAnnotation(@NonNull Class<A> annotationType) {
        Preconditions.checkNotNull(annotationType);

        return method.getAnnotation(annotationType);
    }
}
