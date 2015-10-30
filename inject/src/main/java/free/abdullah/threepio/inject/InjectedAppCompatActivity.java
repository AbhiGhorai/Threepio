package free.abdullah.threepio.inject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by abdulmunaf on 10/6/15.
 */
public class InjectedAppCompatActivity extends AppCompatActivity {
    protected ActivityInjector<InjectedAppCompatActivity> ai = new ActivityInjector<InjectedAppCompatActivity>(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if(ai.injectContentView()) {
            ai.injectSubviews();
            ai.injectClickHandlers();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(ai.injectMenu(menu)) {
            ai.injectMenuHandlers();
            return true;
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(ai.onMenuItemClicked(item.getItemId())) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
