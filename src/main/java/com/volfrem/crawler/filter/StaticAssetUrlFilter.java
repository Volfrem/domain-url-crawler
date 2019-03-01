package com.volfrem.crawler.filter;

public class StaticAssetUrlFilter implements UrlFilter {

    private StaticAssetUrlFilter() {
    }

    public static StaticAssetUrlFilter of() {
        return new StaticAssetUrlFilter();
    }

    @Override
    public boolean isValid(String url) {
        return url.endsWith("png") || url.endsWith("jpg") || url.endsWith("jpeg") || url.endsWith("pdf");
    }
}
