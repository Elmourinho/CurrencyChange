package com.elmar.currencyexchangeapp.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
@Data
public class ApplicationProperties {

private final PublicApi publicApi = new PublicApi();

    public PublicApi getPublicApi() {
        return publicApi;
    }

    @Data
    public static class PublicApi {
        private String url;
        private String key;
        private String supportedCurrencies;
    }
}
