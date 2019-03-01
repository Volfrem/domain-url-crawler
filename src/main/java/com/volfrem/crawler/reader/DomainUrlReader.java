package com.volfrem.crawler.reader;

import com.volfrem.crawler.exception.DomainUrlParameterNotDefinedException;
import com.volfrem.crawler.exception.DomainUrlReadException;
import com.volfrem.crawler.filter.UrlFilter;
import com.volfrem.crawler.provider.UrlProvider;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class DomainUrlReader implements UrlReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(DomainUrlReader.class);
    // Simulate actual user
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 " +
            "(KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36";
    private static final String VALID_CONTENT_TYPE = "text/html";
    private static final int CORRECT_STATUS_CODE = 200;
    private static final int URL_VISIT_LIMIT = 1000;

    private UrlProvider urlProvider;
    private UrlFilter urlFilter;
    private Set<String> visitedUrls;
    private Set<String> filteredOutUrls;

    private DomainUrlReader() {
        visitedUrls = new HashSet<>();
        filteredOutUrls = new HashSet<>();
    }

    public static DomainUrlReaderBuilder of() {
        return new DomainUrlReaderBuilder();
    }

    @Override
    public UrlReader read() {
        // Make sure there will be no infinite loop
        int urlLimitCounter = 0;

        while (!urlProvider.isEmpty() && urlLimitCounter < URL_VISIT_LIMIT) {
            String nextUrl = urlProvider.next();

            try {
                Connection connection = Jsoup.connect(nextUrl).userAgent(USER_AGENT);
                Document document = connection.get();
                validateResponse(connection.response());
                Elements documentUrlElements = document.select("a[href]");
                Set<String> newUrls = documentUrlElements.stream()
                        .map(urlElement -> urlElement.absUrl("href"))
                                // Urls are filtered to be sure url provider will not contain urls already visited
                        .filter(url -> !visitedUrls.contains(url))
                        .collect(Collectors.toSet());
                handleValidUrls(newUrls);
                handleFilteredOutUrls(newUrls);
                urlLimitCounter++;
            } catch (IOException e) {
                LOGGER.error("Error occurred while loading url: {}", nextUrl);
            }
        }
        return this;
    }

    private void handleValidUrls(Set<String> newUrls) {
        Set<String> validUrls = newUrls.stream()
                // Also urls outside the domain are filtered out
                .filter(urlFilter::isValid)
                .collect(Collectors.toSet());

        validUrls.forEach(urlProvider::add);
        visitedUrls.addAll(validUrls);
    }

    private void handleFilteredOutUrls(Set<String> newUrls) {
        filteredOutUrls.addAll(newUrls.stream()
                .filter(url -> !urlFilter.isValid(url))
                .collect(Collectors.toSet()));
    }

    private void validateResponse(Connection.Response response) {
        if (response.statusCode() != CORRECT_STATUS_CODE || !response.contentType().contains(VALID_CONTENT_TYPE)) {
            throw new DomainUrlReadException(String.format("Failed to read URL: %s", response.url()));
        }
    }

    public Set<String> getFilteredOutUrls() {
        return filteredOutUrls;
    }

    public Set<String> getVisitedUrls() {
        return visitedUrls;
    }

    public static class DomainUrlReaderBuilder {
        private UrlProvider urlProvider;
        private UrlFilter urlFilter;

        private DomainUrlReaderBuilder() {
        }

        public DomainUrlReaderBuilder withUrlProvider(UrlProvider urlProvider) {
            this.urlProvider = urlProvider;
            return this;
        }

        public DomainUrlReaderBuilder withUrlFilter(UrlFilter urlFilter) {
            this.urlFilter = urlFilter;
            return this;
        }

        public DomainUrlReader build() {
            validateParameters();
            DomainUrlReader domainUrlReader = new DomainUrlReader();
            domainUrlReader.urlProvider = urlProvider;
            domainUrlReader.urlFilter = urlFilter;
            return domainUrlReader;
        }

        private void validateParameters() {
            if (urlProvider == null) {
                throw new DomainUrlParameterNotDefinedException("urlProvider not defined for DomainUrlReader!");
            }
            if (urlFilter == null) {
                throw new DomainUrlParameterNotDefinedException("urlFilter not defined for DomainUrlReader!");
            }
        }
    }
}
