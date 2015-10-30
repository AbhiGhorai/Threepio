package free.abdullah.threepio.httpcomms;

/**
 * Created by abdullah on 4/13/15.
 */
public class HttpException extends Exception {

    private boolean requestCanceled = false;
    private int responseCode;
    private String responseMessage;

    public HttpException(boolean requestCanceled) {
        super("Request canceled");
        this.requestCanceled = true;
    }

    public HttpException(String detailMessage) {
        super(detailMessage);
    }

    public HttpException(Exception cause) {
        super(cause.getMessage(), cause);
    }

    public HttpException(String detailMessage, Exception cause) {
        super(detailMessage, cause);
    }

    public HttpException(int responseCode, String responseMessage) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
    }

    public boolean isRequestCanceled() {
        return requestCanceled;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }
}
