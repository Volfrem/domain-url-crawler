package com.volfrem.crawler;

import com.volfrem.crawler.exception.DomainUrlParameterNotDefinedException;
import com.volfrem.crawler.filter.DomainInternalUrlFilter;
import com.volfrem.crawler.filter.StaticAssetUrlFilter;
import com.volfrem.crawler.provider.QueueUrlProvider;
import com.volfrem.crawler.reader.DomainUrlReader;
import com.volfrem.crawler.reader.UrlReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class App {
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        validateArguments(args);

        LOGGER.info("Starting Domain Url Crawler Application!");
        UrlReader urlReader = DomainUrlReader.of()
                .withUrlProvider(QueueUrlProvider.of(args[0]))
                .withUrlFilter(DomainInternalUrlFilter.of(args[1]))
                .withStaticAssetFilter(StaticAssetUrlFilter.of())
                .build()
                .read();

        LOGGER.info("Found domain internal urls!");
        Set<String> visitedUrls = urlReader.getVisitedUrls();
        visitedUrls.forEach(LOGGER::info);
        LOGGER.info("-------------------------------------");
        LOGGER.info("Found external urls!");
        Set<String> externalUrls = urlReader.getFilteredOutUrls();
        externalUrls.forEach(LOGGER::info);
        LOGGER.info("-------------------------------------");
        LOGGER.info("Found static asset urls!");
        Set<String> staticAssetUrls = urlReader.getStaticAssetUrls();
        staticAssetUrls.forEach(LOGGER::info);
        LOGGER.info("-------------------------------------");
        LOGGER.info("Domain Url Crawler Finished!");
    }

    private static void validateArguments(String[] args) {
        if (args.length == 0) {
            throw new DomainUrlParameterNotDefinedException("Url should be defined in program arguments " +
                    "as first parameter!");
        }
        if (args.length == 1) {
            throw new DomainUrlParameterNotDefinedException("Domain name to filter " +
                    "should be defined in program arguments as second parameter!");
        }
    }
}
