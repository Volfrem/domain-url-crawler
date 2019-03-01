package com.volfrem.crawler.filter;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DomainInternalUrlFilterUTest {
    @Test
    public void filterInvalidUrl() {
        assertFalse(DomainInternalUrlFilter.of("https://wiprodigital.com").isValid("https://twitter.com"));
    }

    @Test
    public void filterValidUrl() {
        assertTrue(DomainInternalUrlFilter.of("https://wiprodigital.com")
                .isValid("https://wiprodigital.com/who-we-are/#wdteam_meetus"));
    }
}
