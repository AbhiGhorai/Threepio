package free.abdullah.threepio.inject;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Set;

import free.abdullah.threepio.reflect.AnnotationFilter;
import free.abdullah.threepio.reflect.BoundedField;
import free.abdullah.threepio.reflect.BoundedMethod;
import free.abdullah.threepio.reflect.Reflector;


/**
 * Created by abdulmunaf on 7/7/15.
 */
public class FragmentInjector<T extends Fragment> {
    private Reflector<T> reflector;
    private T fragment;

    //private HashMap<Integer, BoundedMethod<T>> menuMethods;
    private HashMap<Integer, BoundedMethod<T>> clickMethods;

    public FragmentInjector(T fragment) {
        this.fragment = fragment;
        this.reflector = new Reflector<T>(this.fragment);

        //this.menuMethods = new HashMap<Integer, BoundedMethod<T>>();
        this.clickMethods = new HashMap<Integer, BoundedMethod<T>>();
    }

    @SuppressWarnings("unchecked")
    public View injectView(LayoutInflater inflater, ViewGroup container) {
        Class<T> ac = (Class<T>) fragment.getClass();
        if(ac.isAnnotationPresent(BindView.class)) {
            BindView a = ac.getAnnotation(BindView.class);
            View rootView = inflater.inflate(a.viewId(), container, false);
            injectSubviews(rootView);
            injectClickHandlers(rootView);
            return rootView;
        }
        return null;
    }

    public void injectSubviews(View rootView) {
        Set<BoundedField<T>> fields = reflector.getDeclaredFields(new AnnotationFilter<T>(BindView.class));

        for(BoundedField<T> field : fields) {
            BindView a = field.getAnnotation(BindView.class);

            View view = rootView.findViewById(a.viewId());
            if(view != null) {
                field.forceSet(view);
            }
        }
    }

    public void injectClickHandlers(View rootView) {
        Set<BoundedMethod<T>> methods = reflector.getMethods(new AnnotationFilter<T>(BindView.class));

        for(BoundedMethod<T> method : methods) {
            BindView v = method.getAnnotation(BindView.class);

            View view = rootView.findViewById(v.viewId());
            if(view != null) {
                view.setOnClickListener(clickListener);
                clickMethods.put(v.viewId(), method);
            }
        }
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Integer viewId = v.getId();
            if(clickMethods.containsKey(viewId)) {
                try {
                    clickMethods.get(viewId).invoke();
                } catch (InvocationTargetException e) { e.printStackTrace(); }
                catch (IllegalAccessException e) { e.printStackTrace(); }
            }
        }
    };

//    public void injectMenuHandlers() {
//        Set<BoundedMethod<T>> methods = reflector.getMethods(new AnnotationFilter<T>(BindMenu.class));
//
//        for(BoundedMethod<T> method : methods) {
//            BindMenu a = method.getAnnotation(BindMenu.class);
//
////            if(innerMethod.getReturnType() != void.class) {
////                throw new InjectException("Menu handling method must have void return type");
////            }
////
////            if(innerMethod.getParameterTypes().length > 0) {
////                throw new InjectException("Menu handling method must not parameters");
////            }
//            menuMethods.put(a.menuId(), method);
//        }
//    }
//
//    public boolean onMenuItemClicked(int menuItemId) {
//        if(menuMethods.containsKey(menuItemId)) {
//            try {
//                menuMethods.get(menuItemId).invoke();
//                return true;
//            } catch (IllegalAccessException e) { e.printStackTrace(); }
//            catch (InvocationTargetException e) { e.printStackTrace(); }
//        }
//        return false;
//    }
//
//    public boolean injectMenu(Menu menu) {
//        Class<T> ac = (Class<T>) fragment.getClass();
//        if(ac.isAnnotationPresent(BindMenu.class)) {
//            BindMenu a = ac.getAnnotation(BindMenu.class);
//            fragment.getMenuInflater().inflate(a.menuId(), menu);
//            return true;
//        }
//        return false;
//    }
}
