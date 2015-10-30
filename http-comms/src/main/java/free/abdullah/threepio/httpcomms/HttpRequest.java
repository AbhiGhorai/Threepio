package free.abdullah.threepio.httpcomms;

import android.support.annotation.NonNull;

import com.google.common.base.Preconditions;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by abdullah on 4/11/15.
 */
public abstract class HttpRequest {

    protected URL url;
    protected ArrayListMultimap<String, String> headers;

    private String requestMethod;
    private boolean canceled;

    public HttpRequest(String url, String requestMethod) throws MalformedURLException {
        Preconditions.checkNotNull(url);
        Preconditions.checkNotNull(requestMethod);

        this.url = new URL(url);
        this.requestMethod = requestMethod;
        headers = ArrayListMultimap.create();
    }

    public HttpRequest(URL url, String requestMethod) {
        Preconditions.checkNotNull(url);
        Preconditions.checkNotNull(requestMethod);

        this.url = url;
        this.requestMethod = requestMethod;
        headers = ArrayListMultimap.create();
    }

    public abstract void processRequest(@NonNull HttpURLConnection connection) throws HttpException;

    public URL getUrl() {
        return url;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void cancel() {
        canceled = true;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void addHeader(String name, String value) {
        headers.put(name, value);
    }

    public void addHeader(String name, long value) {
        headers.put(name, Long.toString(value));
    }

    public void addHeader(String name, double value) {
        headers.put(name, Double.toString(value));
    }

    public List<String> getHeaders(String name) {
        return headers.get(name);
    }

    public String getFirstHeader(String name) {
        List<String> values = headers.get(name);
        if (values != null && values.size() > 0) {
            return values.get(0);
        }
        return null;
    }

    public Multimap<String, String> getHeaders() {
        return ImmutableMultimap.copyOf(headers);
    }

    public void removeHeader(String name) {
        headers.removeAll(name);
    }

    public void removeHeader(String name, String value) {
        headers.remove(name, value);
    }

    public void removeAllHeaders() {
        headers.clear();
    }

    public void checkCanceled() throws HttpException {
        if (isCanceled()) throw new HttpException(true);
    }

    protected void setHeaders(HttpURLConnection connection) {
        for (String name : headers.keySet()) {
            List<String> values = headers.get(name);
            for (String value : values) {
                connection.setRequestProperty(name, value);
            }
        }
    }
}
