package free.abdullah.threepio.inject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by abdulmunaf on 7/7/15.
 */
public class InjectedFragment extends Fragment {
    protected FragmentInjector<InjectedFragment> fi = new FragmentInjector<InjectedFragment>(this);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = fi.injectView(inflater, container);
        if(fragmentView != null) {
            return fragmentView;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
