package free.abdullah.threepio.httpcomms;

import android.support.annotation.NonNull;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by abdullah on 4/11/15.
 */
public class HttpGetRequest extends HttpRequest {

    public final static String METHOD_NAME = "GET";

    public HttpGetRequest(String url) throws MalformedURLException {
        super(url, METHOD_NAME);
    }

    public HttpGetRequest(URL url) {
        super(url, METHOD_NAME);
    }

    @Override
    public void processRequest(@NonNull HttpURLConnection connection) throws HttpException {
        try {
            checkCanceled();

            setHeaders(connection);
            connection.setRequestMethod(METHOD_NAME);
            connection.setDoOutput(true);
            connection.setUseCaches(false);

            checkCanceled();
        }
        catch (ProtocolException e) {
            //Not going to happen as we are passing correct HTTP method name.
        }
    }
}
