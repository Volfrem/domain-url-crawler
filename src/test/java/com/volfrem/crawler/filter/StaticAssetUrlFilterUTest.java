package com.volfrem.crawler.filter;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StaticAssetUrlFilterUTest {
    @Test
    public void filterNonStaticAssetUrl() {
        assertFalse(StaticAssetUrlFilter.of().isValid("https://twitter.com"));
    }

    @Test
    public void filterStaticAssetUrl() {
        assertTrue(StaticAssetUrlFilter.of().isValid("https://wiprodigital.com/who-we-are/pic.png"));
    }
}
