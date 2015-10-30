package free.abdullah.threepio.reflect;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * @param <Reflected>
 * @author Abdullah
 */
public class Reflector<Reflected extends Object> {

    private Reflected _reflected;
    private final Class<? extends Object> _class;

    /**
     * Constructs Reflector object that can be used with reflected instance.
     *
     * @param reflected Object instance of reflected type to be used as reference when
     *                  invoking methods and accessing fields using reflection.
     */
    public Reflector(Reflected reflected) {
        this(reflected.getClass());
        _reflected = reflected;
    }

    /**
     * Constructs Reflector object for clazz.
     *
     * @param clazz
     */
    public Reflector(Class<? extends Object> clazz) {
        _class = clazz;
    }

    /**
     * Creates an instance of the class using default constructor.
     *
     * @param reflectedClass Class of which instance needs to be created.
     * @return Newly create instance of specified class.
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws java.lang.reflect.InvocationTargetException
     */
    public static <Reflected extends Object> Reflected createInstance(Class<Reflected> reflectedClass) throws IllegalAccessException, InstantiationException {
        return reflectedClass.newInstance();
    }


    /**
     * Sets the value of a public field with name specified.
     *
     * @param fieldName Name of the field to be set.
     * @param value     Value to be set.
     * @throws NoSuchFieldException
     * @throws IllegalArgumentException
     */
    public void setFieldValue(String fieldName, Object value) throws NoSuchFieldException, IllegalArgumentException {
        Preconditions.checkState(_reflected != null, "Can not set value to null object");

        try {
            _class.getField(fieldName).set(_reflected, value);
        }
        catch (IllegalAccessException e) {
            assert false : "We should not have come here.";
            //The reason is Class.getField() returns only public accessible fields.
            //So setting or getting it should not cause and IllegalAccessException.
        }
    }

    /**
     * Sets the value of field with given name even if it is not accessible from the calling context.
     *
     * @param fieldName Name of the field whose value is to be set.
     * @param value     Value to be set
     * @throws NoSuchFieldException
     * @throws IllegalArgumentException
     */
    public void forceSetFieldValue(String fieldName, Object value) throws NoSuchFieldException, IllegalArgumentException {
        Preconditions.checkState(_reflected != null, "Can not set value to null object");
        getDeclaredField(fieldName).forceSet(value);
    }

    /**
     * Returns the value of a public field with name specified.
     *
     * @param fieldName Name of the field.
     * @return Value of the field with name specified.
     * @throws NoSuchFieldException
     * @throws IllegalArgumentException
     */
    public Object getFieldValue(String fieldName) throws NoSuchFieldException, IllegalArgumentException {
        Preconditions.checkState(_reflected != null, "Can not get value from null object");
        try {
            return _class.getField(fieldName).get(_reflected);
        }
        catch (IllegalAccessException e) {
            assert false : "We should not have come here.";
            return null;
            //The reason is Class.getField() returns only public accessible fields.
            //So setting or getting it should not cause and IlliegalAccessException.
        }
    }

    /**
     * Returns the value of field with specified given even if the field is not accessible from calling context.
     *
     * @param fieldName Name of the field whose value is required.
     * @return Value of the field with name specified.
     * @throws NoSuchFieldException
     */
    public Object forceGetFieldValue(String fieldName) throws NoSuchFieldException {
        Preconditions.checkState(_reflected != null, "Can not get value from null object");
        return getDeclaredField(fieldName).forceGet();
    }

    /**
     * Returns <code>BoundedField</code> instance which represents the public field with name specified and is
     * bound to reflected object.
     *
     * @param fieldName Name of field to be returned.
     * @return <code>BoundedField</code> instance which represents the public field with name specified.
     * @throws NoSuchFieldException
     */
    public BoundedField<Reflected> getField(String fieldName) throws NoSuchFieldException {
        return new BoundedField<Reflected>(_class.getField(fieldName), _reflected);
    }

    /**
     * Returns an immutable set containing <code>BoundedField</code> objects for all the public fields.
     *
     * @return Immutable set containing <code>BoundedField</code> objects for all the public fields.
     */
    public Set<BoundedField<Reflected>> getFields() {
        return makeFields(_class.getFields(), null);
    }

    /**
     * Returns a immutable set containing <code>BoundedField</code> objects for all the public fields
     * that passes the criteria of supplied filter.
     *
     * @param predicate To filter out field with specified criteria.
     * @return Immutable set containing <code>BoundedField</code> objects.
     */
    public Set<BoundedField<Reflected>> getFeilds(Predicate<BoundedMember<Reflected>> predicate) {
        return makeFields(_class.getFields(), predicate);
    }

    /**
     * Returns <code>BoundedField</code> instance which represents the declared field with name
     * specified and is bound to reflected object.
     *
     * @param fieldName Name of the field.
     * @return <code>BoundedField</code> instance which represents the declared field with name
     * @throws NoSuchFieldException If there is no field with such a name.
     */
    public BoundedField<Reflected> getDeclaredField(String fieldName) throws NoSuchFieldException {
        return new BoundedField<Reflected>(_class.getDeclaredField(fieldName), _reflected);
    }

    /**
     * Returns an immutable set containing <code>BoundedField</code> objects for all the declared fields.
     *
     * @return Immutable set containing <code>BoundedField</code> objects for all the declared fields.
     */
    public Set<BoundedField<Reflected>> getDeclaredFields() {
        return makeFields(_class.getDeclaredFields(), null);
    }

    /**
     * Immutable set containing <code>BoundedField</code> objects for all the declared fields that passes
     * the criteria specified by the filter passed.
     *
     * @param predicate To filter out field with specified criteria.
     * @return Immutable set containing <code>BoundedField</code> objects.
     */
    public Set<BoundedField<Reflected>> getDeclaredFields(Predicate<BoundedMember<Reflected>> predicate) {
        return makeFields(_class.getDeclaredFields(), predicate);
    }


    public Set<BoundedMethod<Reflected>> getMethods() {
        return makeMethods(_class.getMethods(), null);
    }

    public Set<BoundedMethod<Reflected>> getMethods(Predicate<BoundedMember<Reflected>> predicate) {
        return makeMethods(_class.getMethods(), predicate);
    }

    public Set<BoundedMethod<Reflected>> getDeclaredMethods() {
        return makeMethods(_class.getDeclaredMethods(), null);
    }

    public Set<BoundedMethod<Reflected>> getDeclaredMethods(Predicate<BoundedMember<Reflected>> predicate) {
        return makeMethods(_class.getDeclaredMethods(), predicate);
    }

    private Set<BoundedField<Reflected>> makeFields(Field[] fields, Predicate<BoundedMember<Reflected>> predicate) {
        ImmutableSet.Builder<BoundedField<Reflected>> builder = new ImmutableSet.Builder<BoundedField<Reflected>>();
        for (Field field : fields) {
            builder.add(new BoundedField<Reflected>(field, _reflected));
        }

        if (predicate != null) {
            return Sets.filter(builder.build(), predicate);
        }
        return builder.build();
    }

    private Set<BoundedMethod<Reflected>> makeMethods(Method[] methods, Predicate<BoundedMember<Reflected>> predicate) {
        ImmutableSet.Builder<BoundedMethod<Reflected>> builder = new ImmutableSet.Builder<BoundedMethod<Reflected>>();
        for (Method method : methods) {
            builder.add(new BoundedMethod<Reflected>(method, _reflected));
        }

        if (predicate != null) {
            return Sets.filter(builder.build(), predicate);
        }
        return builder.build();
    }
}
