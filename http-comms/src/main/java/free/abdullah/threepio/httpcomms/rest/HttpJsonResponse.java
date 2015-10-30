package free.abdullah.threepio.httpcomms.rest;

import com.google.common.base.Preconditions;
import com.google.common.io.CharStreams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import free.abdullah.threepio.httpcomms.ContentType;
import free.abdullah.threepio.httpcomms.HttpException;
import free.abdullah.threepio.httpcomms.HttpResponse;

/**
 * Created by abdullah on 4/15/15.
 */
public class HttpJsonResponse<R extends JsonResponseObject> extends HttpResponse {

    protected R responseObject;

    public HttpJsonResponse(R responseObject) {
        Preconditions.checkNotNull(responseObject);

        this.responseObject = responseObject;
    }

    public R getResponseObject() {
        return responseObject;
    }

    @Override
    public void processResponse(HttpURLConnection connection) throws HttpException {
        InputStreamReader isr = null;

        try {
            checkCanceled();
            if (isResponseOK(connection)) {
                checkContentType(connection);

                String contentEncoding = getContentEncoding(connection);
                isr = new InputStreamReader(connection.getInputStream(), contentEncoding);

                checkCanceled();
                String jsonString = CharStreams.toString(isr);
                responseObject.fromJson(new JSONObject(jsonString));
            }
            else {
                int responseCode = getResponseCode(connection);
                String statusMessage = getStatusMessage(connection);
                throw new HttpException(responseCode, statusMessage);
            }
        }
        catch (IOException e) {
            throw new HttpException("Exception while reading data from response stream", e);
        }
        catch (JSONException e) {
            throw new HttpException("Exception in decoding JSON response", e);
        }
        finally {
            if (isr != null) {
                try {
                    isr.close();
                }
                catch (IOException e) {
                }
            }
        }
    }

    protected void checkContentType(HttpURLConnection connection) throws HttpException {
        String contentType = connection.getContentType();
        if (!contentType.equals(ContentType.APP_JSON)) {
            String message = "Invalid content type of response, expected 'application/json' but found " + contentType;
            throw new HttpException(message);
        }
    }

    protected String getContentEncoding(HttpURLConnection connection) {
        String contentEncoding = connection.getContentEncoding();
        if (contentEncoding == null) {
            return "UTF-8";
        }
        return contentEncoding;
    }
}
