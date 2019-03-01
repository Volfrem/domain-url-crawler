package com.volfrem.crawler.filter;

public class DomainInternalUrlFilter implements UrlFilter {
    private final String domainName;

    private DomainInternalUrlFilter(String domainName) {
        this.domainName = domainName;
    }

    public static DomainInternalUrlFilterBuilder of(String domainName) {
        return new DomainInternalUrlFilterBuilder(domainName);
    }

    @Override
    public boolean isValid(String url) {
        return url.startsWith(domainName);
    }

    public static class DomainInternalUrlFilterBuilder {
        private final String domainName;

        private DomainInternalUrlFilterBuilder(String domainName) {
            this.domainName = domainName;
        }

        public DomainInternalUrlFilter build() {
            return new DomainInternalUrlFilter(domainName);
        }
    }
}
