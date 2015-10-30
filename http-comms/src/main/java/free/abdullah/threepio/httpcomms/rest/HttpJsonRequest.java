package free.abdullah.threepio.httpcomms.rest;

import android.support.annotation.NonNull;

import com.google.common.base.Preconditions;

import org.json.JSONException;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import free.abdullah.threepio.httpcomms.ContentType;
import free.abdullah.threepio.httpcomms.HttpException;
import free.abdullah.threepio.httpcomms.HttpPostRequest;

/**
 * Created by abdullah on 4/14/15.
 */
public class HttpJsonRequest<R extends JsonRequestObject> extends HttpPostRequest {

    protected R requestObject;
    protected Charset charset;

    public HttpJsonRequest(String url, R requestObject) throws MalformedURLException {
        this(new URL(url), requestObject, Charset.forName("UTF-8"));
    }

    public HttpJsonRequest(String url, R requestObject, Charset charset) throws MalformedURLException {
        this(new URL(url), requestObject, charset);
    }

    public HttpJsonRequest(URL url, R requestObject) {
        this(url, requestObject, Charset.forName("UTF-8"));
    }

    public HttpJsonRequest(URL url, R requestObject, Charset charset) {
        super(url);

        Preconditions.checkNotNull(requestObject);
        Preconditions.checkNotNull(charset);

        this.requestObject = requestObject;
        this.charset = charset;
    }

    public R getRequestObject() {
        return requestObject;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    public Charset getCharset() {
        return charset;
    }

    @Override
    public void processRequest(@NonNull HttpURLConnection connection) throws HttpException {
        setContentType(ContentType.APP_JSON);
        super.processRequest(connection);
    }

    @Override
    protected void writeData(@NonNull OutputStream os) throws HttpException {
        try {
            checkCanceled();
            OutputStreamWriter osw = new OutputStreamWriter(os, charset);
            String data = requestObject.toJson().toString();
            osw.write(data);
            osw.flush();
            osw.close();
        }
        catch (JSONException e) {
            throw new HttpException("Json encoding failed", e);
        }
        catch (IOException e) {
            throw new HttpException("Error while writing data to output stream", e);
        }
    }
}
