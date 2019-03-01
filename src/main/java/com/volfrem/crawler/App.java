package com.volfrem.crawler;

import com.volfrem.crawler.exception.DomainUrlParameterNotDefinedException;
import com.volfrem.crawler.filter.DomainInternalUrlFilter;
import com.volfrem.crawler.provider.QueueUrlProvider;
import com.volfrem.crawler.reader.DomainUrlReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        validateArguments(args);

        LOGGER.info("Starting Domain Url Crawler Application!");
        DomainUrlReader.of()
                .withUrlProvider(QueueUrlProvider.of(args[0]))
                .withUrlFilter(DomainInternalUrlFilter.of(args[1])
                        .build())
                .build()
                .read();
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
