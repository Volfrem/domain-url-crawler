package com.volfrem.crawler.reader;

import java.util.Set;

public interface UrlReader {
    UrlReader read();

    Set<String> getFilteredOutUrls();

    Set<String> getVisitedUrls();
}
