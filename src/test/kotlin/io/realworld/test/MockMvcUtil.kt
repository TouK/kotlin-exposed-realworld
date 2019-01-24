package io.realworld.test

import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder

fun MockHttpServletRequestBuilder.json(content: String) = content(content).contentType(MediaType.APPLICATION_JSON)
