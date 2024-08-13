package kr.gyk.adobby.unsolved_backend.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${openai.key}") private String apiKey;

    @Bean
    public WebClient webClient (WebClient.Builder builder) {
        return builder
                .baseUrl("https://api.openai.com/v1/chat/completions")
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                    httpHeaders.add("Authorization", "Bearer " + apiKey);
                })
                .build();
    }

    @Configuration
    public class WebFluxConfiguration implements WebFluxConfigurer {
        @Override
        public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
            configurer.defaultCodecs().maxInMemorySize(1024 * 1024);
        }
    }

}
