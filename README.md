# domain-url-crawler

## How to run
    1. mvn package
    2. java -jar ./target/domain-url-crawler-1.0.0.jar https://buildit.wiprodigital.com/ https://wiprodigital.com

## Possible improvements
    1. Use Spring Boot
    2. Use lombok instead of custom builders
    3. Improve filtering of urls
    4. Write result into file
    5. Add more tests
    6. Wrap Jsoup and inject it into DomainUrlReader class. To make it easier to test and substitute.