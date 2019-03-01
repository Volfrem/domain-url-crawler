package com.volfrem.crawler.provider;

public interface UrlProvider {
    String next();

    void add(String url);

    Boolean isEmpty();
}
