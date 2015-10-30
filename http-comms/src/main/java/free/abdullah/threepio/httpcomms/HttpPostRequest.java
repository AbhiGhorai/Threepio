package free.abdullah.threepio.httpcomms;

import android.support.annotation.NonNull;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by abdullah on 4/11/15.
 */
public abstract class HttpPostRequest extends HttpRequest {

    public final static String METHOD_NAME = "POST";

    public HttpPostRequest(String url) throws MalformedURLException {
        super(url, METHOD_NAME);
    }

    public HttpPostRequest(URL url) {
        super(url, METHOD_NAME);
    }

    public void setContentType(String contentType) {
        if (getContentType() != null) {
            removeHeader(ContentType.HEADER_NAME);
        }

        addHeader(ContentType.HEADER_NAME, contentType);
    }

    public String getContentType() {
        return getFirstHeader(ContentType.HEADER_NAME);
    }

    public void setContentEncoding(String contentEncoding) {
        if (getContentType() != null) {
            removeHeader(ContentType.HEADER_NAME);
        }

        addHeader(ContentType.HEADER_NAME, contentEncoding);
    }

    public String getContentEncoding() {
        return getFirstHeader(ContentType.HEADER_NAME);
    }

    @Override
    public void processRequest(@NonNull HttpURLConnection connection) throws HttpException {
        try {
            checkCanceled();

            setHeaders(connection);
            connection.setRequestMethod(METHOD_NAME);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            checkCanceled();

            writeData(connection.getOutputStream());
            checkCanceled();

        }
        catch (ProtocolException e) {
            //No worries as we are passing correct HTTP method name.
        }
        catch (IOException e) {
            throw new HttpException(e);
        }

    }

    protected abstract void writeData(@NonNull OutputStream os) throws HttpException;
}
