package com.eric.koo.starter.web.api;

import com.eric.koo.starter.web.WebConstant;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@ToString
@Configuration
@ConfigurationProperties(prefix = WebConstant.PROPERTIES_PREFIX_API)
public class ApiProperties {

    private boolean performanceMonitorEnabled = true;
    private ApiLoggingProperties logging = new ApiLoggingProperties();

    @Getter
    @Setter
    @ToString
    public static class ApiLoggingProperties {

        private boolean enabled = true;
        private int maxPayloadLength = 10000;
        private ApiRequestLoggingProperties request = new ApiRequestLoggingProperties();
        private ApiResponseLoggingProperties response = new ApiResponseLoggingProperties();

        @Getter
        @Setter
        @ToString
        public static class ApiRequestLoggingProperties {

            private boolean clientInfoEnabled;
            private boolean headersEnabled;
            private String[] maskedHeaders = {};
        }

        @Getter
        @Setter
        @ToString
        public static class ApiResponseLoggingProperties {

            private boolean contentTypeEnabled;
        }
    }
}
