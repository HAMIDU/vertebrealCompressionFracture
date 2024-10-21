package com.kariza.onlineshopping;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public final class HttpTestUtil {
    private HttpTestUtil() {}

    public static MockHttpServletRequestBuilder getBaseMvcBuilder(MediaType contentType, MediaType acceptType,
                                                                  HttpMethod method, String url) {
        return MockMvcRequestBuilders.request(method, url)
                .contentType(contentType)
                .accept(acceptType);
    }

    public static MockHttpServletRequestBuilder getBaseJsonMvcBuilder(HttpMethod method, String url) {
        return getBaseMvcBuilder(MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, method, url);
    }
}
