package free.abdullah.threepio.inject;

import android.app.Activity;
import android.support.design.widget.NavigationView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Set;

import free.abdullah.threepio.reflect.AnnotationFilter;
import free.abdullah.threepio.reflect.BoundedField;
import free.abdullah.threepio.reflect.BoundedMethod;
import free.abdullah.threepio.reflect.Reflector;


/**
 * Created by abdullah on 5/6/15.
 */
public class ActivityInjector<T extends Activity> {

    private Reflector<T> reflector;
    private T activity;

    private HashMap<Integer, BoundedMethod<T>> menuMethods;
    private HashMap<Integer, BoundedMethod<T>> clickMethods;
    private HashMap<Integer, BoundedMethod<T>> navMenuMethods;

    public ActivityInjector(T activity) {
        this.activity = activity;
        this.reflector = new Reflector<T>(activity);

        this.menuMethods = new HashMap<Integer, BoundedMethod<T>>();
        this.clickMethods = new HashMap<Integer, BoundedMethod<T>>();
    }

    @SuppressWarnings("unchecked")
    public boolean injectContentView() {
        Class<T> ac = (Class<T>) activity.getClass();
        if(ac.isAnnotationPresent(BindView.class)) {
            BindView a = ac.getAnnotation(BindView.class);
            activity.setContentView(a.viewId());
            return true;
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public boolean injectMenu(Menu menu) {
        Class<T> ac = (Class<T>) activity.getClass();
        if(ac.isAnnotationPresent(BindMenu.class)) {
            BindMenu a = ac.getAnnotation(BindMenu.class);
            activity.getMenuInflater().inflate(a.menuId(), menu);
            return true;
        }
        return false;
    }

    public void injectSubviews() {
        Set<BoundedField<T>> fields = reflector.getDeclaredFields(new AnnotationFilter<T>(BindView.class));

        for(BoundedField<T> field : fields) {
            BindView a = field.getAnnotation(BindView.class);

            View view = activity.findViewById(a.viewId());
            if(view != null) {
                field.forceSet(view);
            }
        }
    }

    public void injectClickHandlers() {
        Set<BoundedMethod<T>> methods = reflector.getMethods(new AnnotationFilter<T>(BindView.class));

        for(BoundedMethod<T> method : methods) {
            BindView v = method.getAnnotation(BindView.class);

            View view = activity.findViewById(v.viewId());
            if(view != null) {
                view.setOnClickListener(clickListener);
                clickMethods.put(v.viewId(), method);
            }
        }
    }

    public void injectMenuHandlers() {
        Set<BoundedMethod<T>> methods = reflector.getMethods(new AnnotationFilter<T>(BindMenu.class));

        for(BoundedMethod<T> method : methods) {
            BindMenu a = method.getAnnotation(BindMenu.class);

//            if(innerMethod.getReturnType() != void.class) {
//                throw new InjectException("Menu handling method must have void return type");
//            }
//
//            if(innerMethod.getParameterTypes().length > 0) {
//                throw new InjectException("Menu handling method must not parameters");
//            }
            menuMethods.put(a.menuId(), method);
        }
    }

    public void injectNavigationMenuHandlers(NavigationView navigationView) {
        navMenuMethods = new HashMap<Integer, BoundedMethod<T>>();

        Set<BoundedMethod<T>> methods =
                reflector.getMethods(new AnnotationFilter<T>(BindNavigationItem.class));
        for(BoundedMethod<T> method : methods) {
            BindNavigationItem a = method.getAnnotation(BindNavigationItem.class);
            navMenuMethods.put(a.menuId(), method);
        }
        navigationView.setNavigationItemSelectedListener(navigationItemSelectedListener);
    }

    public boolean onMenuItemClicked(int menuItemId) {
        if(menuMethods.containsKey(menuItemId)) {
            try {
                menuMethods.get(menuItemId).invoke();
                return true;
            } catch (IllegalAccessException e) { e.printStackTrace(); }
              catch (InvocationTargetException e) { e.printStackTrace(); }
        }
        return false;
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

    private NavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem menuItem) {
                    int menuItemId = menuItem.getItemId();
                    if(navMenuMethods.containsKey(menuItemId)) {
                        try {
                            navMenuMethods.get(menuItemId).invoke(menuItem);
                            return true;
                        } catch (IllegalAccessException e) { e.printStackTrace(); }
                        catch (InvocationTargetException e) { e.printStackTrace(); }
                    }
                    return false;
                }
            };
}
