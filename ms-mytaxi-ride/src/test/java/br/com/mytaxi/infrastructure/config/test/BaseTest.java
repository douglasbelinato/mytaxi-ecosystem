package br.com.mytaxi.infrastructure.config.test;

import br.com.mytaxi.infrastructure.config.test.apimock.ApiMockIntegration;
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
