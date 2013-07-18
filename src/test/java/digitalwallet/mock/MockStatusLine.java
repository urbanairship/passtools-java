package digitalwallet.mock;

import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;

public class MockStatusLine implements StatusLine {
    private final static ProtocolVersion defaultVersion = new ProtocolVersion("HTTP/1.1", 1, 1);
    private final static String defaultReasonPhrase = "OK";
    private final static int defaultStatusCode = 200;

    private ProtocolVersion version;
    private String reasonPhrase;
    private int statusCode;

    public MockStatusLine() {
        version = defaultVersion;
        reasonPhrase = defaultReasonPhrase;
        statusCode = defaultStatusCode;
    }

    public MockStatusLine(final ProtocolVersion version, int statusCode) {
        this.version = version;
        reasonPhrase = defaultReasonPhrase;
        this.statusCode = statusCode;
    }

    public MockStatusLine(final ProtocolVersion version, final String reasonPhrase, int statusCode) {
        this.version = version;
        this.reasonPhrase = reasonPhrase;
        this.statusCode = statusCode;
    }

    @Override
    public ProtocolVersion getProtocolVersion() {
        return version;
    }

    @Override
    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public String getReasonPhrase() {
        return reasonPhrase;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setReasonPhrase(final String reasonPhrase) {
        this.reasonPhrase = reasonPhrase;
    }
}
