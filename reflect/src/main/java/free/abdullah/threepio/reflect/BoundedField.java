package free.abdullah.threepio.reflect;

import android.support.annotation.NonNull;

import com.google.common.base.Preconditions;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Represents a field of a class <code>Bound<code> that is bounded to its
 * instance.
 *
 * @param <Bound> Class to which the field belong.
 * @author Abdullah
 */
public class BoundedField<Bound extends Object> extends BoundedMember<Bound> {

    private Field field;

    BoundedField(Field field, Bound bound) {
        super(field, bound);
        Preconditions.checkNotNull(field);

        this.field = field;
        this.bound = bound;
    }

    public BoundedField(Field field) {
        this(field, null);
    }

    /**
     * Returns the name of the field.
     *
     * @return Name of the field.
     */
    public String getName() {
        return field.getName();
    }

    /**
     * Returns the underlying <code>java.lang.reflect.Field</code>.
     *
     * @return Underlying <code>java.lang.reflect.Field</code>.
     */
    public Field getField() {
        return field;
    }

    /**
     * Returns the type of the underlying field.
     *
     * @return Type of underlying field.
     */
    public Class<?> getFieldType() {
        return field.getType();
    }

    /**
     * Sets a new bounded object for this field. All the subsequent operations will be done
     * on the passed object.
     *
     * @param bound New bounded object.
     */
    public void setBound(Bound bound) {
        this.bound = bound;
    }

    /**
     * Sets the value of this field in to bounded object.
     *
     * @param value Value to be set.
     * @throws IllegalAccessException   If the caller does not have permission to access this field.
     * @throws IllegalArgumentException If the value passed is not compatible with the field type.
     */
    public void set(Object value) throws IllegalAccessException, IllegalArgumentException {
        Preconditions.checkState(bound != null, "Can not set value to null object");
        field.set(bound, value);
    }

    /**
     * Returns the value of this field set in the bounded object.
     *
     * @return Value of this field set in the bounded object.
     * @throws IllegalAccessException If the caller does not have permission to access this field.
     */
    public Object get() throws IllegalAccessException {
        Preconditions.checkState(bound != null, "Can not get value from null object");
        return field.get(bound);
    }

    /**
     * Sets the value of this field in the bounded object even by force i.e.
     * even if the field is not accessible by the caller.
     * <p/>
     * This method is intrusive, you better know what you are doing while
     * using the method, it may break some functionality of your code.
     *
     * @param value Value to be set.
     * @throws IllegalArgumentException If the value passed is not compatible with the field type.
     */
    public void forceSet(Object value) throws IllegalArgumentException {
        Preconditions.checkState(bound != null, "Can not set value to null object");
        try {
            if (field.isAccessible()) {
                field.set(bound, value);
            }
            else {
                field.setAccessible(true);
                field.set(bound, value);
                field.setAccessible(false);
            }
        }
        catch (IllegalAccessException e) {
            assert false : "Should not come here as we are explicitly setting the field accessible";
        }
    }

    /**
     * Returns the value of this field set in the bounded object even if the caller
     * does not have permission to access the field.
     *
     * @return Value of this field set in the bounded object.
     */
    public Object forceGet() {
        Preconditions.checkState(bound != null, "Can not get value from null object");
        try {
            if (field.isAccessible()) {
                return field.get(bound);
            }
            else {
                field.setAccessible(true);
                Object value = field.get(bound);
                field.setAccessible(false);
                return value;
            }
        }
        catch (IllegalAccessException e) {
            assert false : "Should not come here as we are explicitly setting the field accessible";
            return null;
        }
    }

    @Override
    public <A extends Annotation> boolean isAnnotationPresent(@NonNull Class<A> annotationType) {
        Preconditions.checkNotNull(annotationType);

        return field.isAnnotationPresent(annotationType);
    }

    public <A extends Annotation> A getAnnotation(@NonNull Class<A> annotationType) {
        Preconditions.checkNotNull(annotationType);

        return field.getAnnotation(annotationType);
    }
}
