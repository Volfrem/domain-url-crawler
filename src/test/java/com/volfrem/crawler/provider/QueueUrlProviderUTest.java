package com.volfrem.crawler.provider;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class QueueUrlProviderUTest {
    @Test
    public void returnsCorrectNextUrl() {
        UrlProvider urlProvider = QueueUrlProvider.of("https://wiprodigital.com");
        assertEquals("https://wiprodigital.com", urlProvider.next());
        assertTrue(urlProvider.isEmpty());
    }
}
