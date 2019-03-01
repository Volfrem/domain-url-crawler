package com.volfrem.crawler.filter;

public class DomainInternalUrlFilter implements UrlFilter {
    private final String domainName;

    private DomainInternalUrlFilter(String domainName) {
        this.domainName = domainName;
    }

    public static DomainInternalUrlFilter of(String domainName) {
        return new DomainInternalUrlFilter(domainName);
    }

    @Override
    public boolean isValid(String url) {
        return url.startsWith(domainName);
    }
}
