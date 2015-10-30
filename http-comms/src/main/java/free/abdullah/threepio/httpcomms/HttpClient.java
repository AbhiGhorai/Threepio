package free.abdullah.threepio.httpcomms;

import android.support.annotation.NonNull;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;

/**
 * Created by abdullah on 4/13/15.
 */
public class HttpClient {

    //Some comments
    protected WebContext context;

    public HttpClient() {
        this(null);
    }

    public HttpClient(WebContext context) {
        this.context = context;
    }

    public void execute(@NonNull HttpRequest request, @NonNull HttpResponse response) throws HttpException {
        response.bindRequest(request);

        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) request.getUrl().openConnection();

            if (context != null) {
                context.applyBefore(connection);
            }
            request.checkCanceled();
            request.processRequest(connection);

            if (context != null) {
                context.applyAfter(connection);
            }
            response.checkCanceled();
            response.processResponse(connection);
            response.checkCanceled();
        }
        catch (IOException e) {
            throw new HttpException("IO exception while performing request", e);
        }
        catch (URISyntaxException e) {
            throw new HttpException("Exception while setting or getting cookies", e);
        }
        catch (HttpException e) {
            throw  e;
        }
        catch (Exception e) {
            throw new HttpException("Something went wrong", e);
        }
        finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
