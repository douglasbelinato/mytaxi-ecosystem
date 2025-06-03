package br.com.mytaxi.infrastructure.config.test.apimock.wiremock;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WireMockServerConfig {

    @Bean
    public WireMockServer wireMockServer() {
        var wireMockServer = new WireMockServer(
                WireMockConfiguration.options().port(9000).globalTemplating(true));
        wireMockServer.start();
        return wireMockServer;
    }

}
