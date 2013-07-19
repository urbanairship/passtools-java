package digitalwallet.mock;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import java.io.*;

public class MockEntity implements HttpEntity {
    private static final String defaultBody = "{\"Foo\":\"Bar\"}";
    private final static Header contentType = new BasicHeader(HTTP.CONTENT_TYPE, "application/json");
    private final static Header contentEncoding = new BasicHeader(HTTP.CONTENT_ENCODING, "json");

    private String body;

    public MockEntity() {
        body = defaultBody;
    }

    public MockEntity(final String body) {
        this.body = body;
    }

    public MockEntity(final InputStream is) {
        BufferedReader br = null;

        try {
            StringBuilder sb = new StringBuilder();
            String line;

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            this.body = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean isRepeatable() {
        return false;
    }

    @Override
    public boolean isChunked() {
        return false;
    }

    @Override
    public long getContentLength() {
        return body.length();
    }

    @Override
    public Header getContentType() {
        return contentType;
    }

    @Override
    public Header getContentEncoding() {
        return contentEncoding;
    }

    @Override
    public InputStream getContent() throws IOException, IllegalStateException {
        return new ByteArrayInputStream(body.getBytes());
    }

    @Override
    public void writeTo(OutputStream outputStream) throws IOException {
        outputStream.write(body.getBytes());
    }

    @Override
    public boolean isStreaming() {
        return false;
    }

    @Override
    public void consumeContent() throws IOException {
        // we're not streaming, nothing to consume
    }
}
