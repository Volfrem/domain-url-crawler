package com.volfrem.crawler.provider;

import com.volfrem.crawler.exception.DomainUrlInvalidActionException;

import java.util.LinkedList;
import java.util.List;

public class QueueUrlProvider implements UrlProvider {
    private List<String> urlQueue;

    private QueueUrlProvider() {
        urlQueue = new LinkedList<>();
    }

    private QueueUrlProvider(String url) {
        this();
        urlQueue.add(url);
    }

    public static QueueUrlProvider of() {
        return new QueueUrlProvider();
    }

    public static QueueUrlProvider of(String url) {
        return new QueueUrlProvider(url);
    }

    @Override
    public String next() {
        if (isEmpty()) {
            throw new DomainUrlInvalidActionException("No more urls to visit left!");
        }
        return urlQueue.remove(0);
    }

    @Override
    public void add(String url) {
        urlQueue.add(url);
    }

    @Override
    public Boolean isEmpty() {
        return urlQueue.isEmpty();
    }
}
