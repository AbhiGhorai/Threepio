package free.abdullah.threepio.httpcomms;

import android.support.annotation.NonNull;

import com.google.common.base.Preconditions;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;

/**
 * Created by abdullah on 4/13/15.
 */
public abstract class HttpResponse {

    private WeakReference<HttpRequest> request;

    public HttpResponse() {

    }

    protected void checkCanceled() throws HttpException {
        HttpRequest req = request.get();
        if (req != null) {
            req.checkCanceled();
        }
    }

    void bindRequest(HttpRequest request) {
        this.request = new WeakReference<HttpRequest>(request);
    }

    void unbindRequest() {
        request.clear();
        request = null;
    }

    protected boolean isResponseOK(@NonNull HttpURLConnection connection) {
        return getResponseCode(connection) == HttpURLConnection.HTTP_OK;
    }

    protected int getResponseCode(@NonNull HttpURLConnection connection) {
        Preconditions.checkNotNull(connection);

        try {
            return connection.getResponseCode();
        }
        catch (IOException e) {
            return -1;
        }
    }

    protected String getStatusMessage(@NonNull HttpURLConnection connection) {
        Preconditions.checkNotNull(connection);

        try {
            return connection.getResponseMessage();
        }
        catch (IOException e) {
            return null;
        }
    }

    public abstract void processResponse(HttpURLConnection connection) throws HttpException;
}
