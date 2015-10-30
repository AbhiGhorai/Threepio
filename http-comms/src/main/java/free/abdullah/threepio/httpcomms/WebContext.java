package free.abdullah.threepio.httpcomms;

import android.content.Context;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

/**
 * Created by abdullah on 4/11/15.
 */
public class WebContext {

    protected Context context;

    protected ArrayListMultimap<String, String> commonHeaders;
    protected CookieManager cookieManager;

    public WebContext(Context context) {
        this.context = context;
        commonHeaders = ArrayListMultimap.create();
    }

    public WebContext(Context context, boolean manageCookies) {
        this(context);
        setManageCookies(true);
    }

    public void setManageCookies(boolean manageCookies) {
        if(manageCookies && cookieManager == null) {
            cookieManager = new CookieManager();
        } else {
            cookieManager = null;
        }
    }

    public void setCookiePolicy(CookiePolicy policy) {
        if(cookieManager != null) {
            cookieManager.setCookiePolicy(policy);
        }
    }

    public void applyBefore(HttpURLConnection connection) throws URISyntaxException, IOException {

        //Add common headers to the request.
        for (String name : commonHeaders.keySet()) {
            List<String> values = commonHeaders.get(name);
            for (String value : values) {
                connection.setRequestProperty(name, value);
            }
        }

        if(cookieManager != null) {
            URI uri = connection.getURL().toURI();
            Map<String, List<String>> cookieHeaders = cookieManager.get(uri, connection.getRequestProperties());

            for(String name : cookieHeaders.keySet()) {
                List<String> values = cookieHeaders.get(name);
                for(String value : values) {
                    connection.setRequestProperty(name, value);
                }
            }
        }
    }

    public void applyAfter(HttpURLConnection connection) throws URISyntaxException, IOException {
        if(cookieManager != null) {
            URI uri = connection.getURL().toURI();
            cookieManager.put(uri, connection.getHeaderFields());
        }
    }

    public void addHeader(String name, String value) {
        commonHeaders.put(name, value);
    }

    public void addHeader(String name, long value) {
        commonHeaders.put(name, Long.toString(value));
    }

    public void addHeader(String name, double value) {
        commonHeaders.put(name, Double.toString(value));
    }

    public Multimap<String, String> getHeaders() {
        return ImmutableMultimap.copyOf(commonHeaders);
    }

    public void removeHeader(String name) {
        commonHeaders.removeAll(name);
    }

    public void removeHeader(String name, String value) {
        commonHeaders.remove(name, value);
    }

    public void removeAllHeaders() {
        commonHeaders.clear();
    }
}