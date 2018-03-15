package mobpair.com.installtrack.toolbox;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import mobpair.com.installtrack.AuthFailureError;
import mobpair.com.installtrack.Header;
import mobpair.com.installtrack.Request;
import mobpair.com.installtrack.mock.TestRequest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

public class BaseHttpStackTest {
    private static final Request<?> REQUEST = new TestRequest.Get();
    private static final Map<String, String> ADDITIONAL_HEADERS = Collections.emptyMap();


    private InputStream mContent;


    @Test
    public void legacyRequestWithoutBody() throws Exception {
        BaseHttpStack stack = new BaseHttpStack() {
            @Override
            public HttpResponse executeRequest(
                    Request<?> request, Map<String, String> additionalHeaders)
                    throws IOException, AuthFailureError {
                assertSame(REQUEST, request);
                assertSame(ADDITIONAL_HEADERS, additionalHeaders);
                return new HttpResponse(12345, Collections.<Header>emptyList());
            }
        };
        org.apache.http.HttpResponse resp = stack.performRequest(REQUEST, ADDITIONAL_HEADERS);
        assertEquals(12345, resp.getStatusLine().getStatusCode());
        assertEquals(0, resp.getAllHeaders().length);
        assertNull(resp.getEntity());
    }

    @Test
    public void legacyResponseWithBody() throws Exception {
        BaseHttpStack stack = new BaseHttpStack() {
            @Override
            public HttpResponse executeRequest(
                    Request<?> request, Map<String, String> additionalHeaders)
                    throws IOException, AuthFailureError {
                assertSame(REQUEST, request);
                assertSame(ADDITIONAL_HEADERS, additionalHeaders);
                return new HttpResponse(
                        12345,
                        Collections.<Header>emptyList(),
                        555,
                        mContent);
            }
        };
        org.apache.http.HttpResponse resp = stack.performRequest(REQUEST, ADDITIONAL_HEADERS);
        assertEquals(12345, resp.getStatusLine().getStatusCode());
        assertEquals(0, resp.getAllHeaders().length);
        assertEquals(555L, resp.getEntity().getContentLength());
        assertSame(mContent, resp.getEntity().getContent());
    }

    @Test
    public void legacyResponseHeaders() throws Exception {
        BaseHttpStack stack = new BaseHttpStack() {
            @Override
            public HttpResponse executeRequest(
                    Request<?> request, Map<String, String> additionalHeaders)
                    throws IOException, AuthFailureError {
                assertSame(REQUEST, request);
                assertSame(ADDITIONAL_HEADERS, additionalHeaders);
                List<Header> headers = new ArrayList<>();
                headers.add(new Header("HeaderA", "ValueA"));
                headers.add(new Header("HeaderB", "ValueB_1"));
                headers.add(new Header("HeaderB", "ValueB_2"));
                return new HttpResponse(12345, headers);
            }
        };
        org.apache.http.HttpResponse resp = stack.performRequest(REQUEST, ADDITIONAL_HEADERS);
        assertEquals(12345, resp.getStatusLine().getStatusCode());
        assertEquals(3, resp.getAllHeaders().length);
        assertEquals("HeaderA", resp.getAllHeaders()[0].getName());
        assertEquals("ValueA", resp.getAllHeaders()[0].getValue());
        assertEquals("HeaderB", resp.getAllHeaders()[1].getName());
        assertEquals("ValueB_1", resp.getAllHeaders()[1].getValue());
        assertEquals("HeaderB", resp.getAllHeaders()[2].getName());
        assertEquals("ValueB_2", resp.getAllHeaders()[2].getValue());
        assertNull(resp.getEntity());
    }
}
