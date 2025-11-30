package br.com.mytaxi.integrationtests.config;

import br.com.mytaxi.integrationtests.config.apimock.ApiMockIntegration;
import io.restassured.RestAssured;
import jakarta.inject.Inject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@Execution(ExecutionMode.SAME_THREAD)
public abstract class BaseTest {

    @Inject
    protected ApiMockIntegration apiMockIntegration;

    static {
        RestAssured.port = 8080;
    }

    @AfterEach
    public void tearDown() {
        apiMockIntegration.resetAll();
    }

}
