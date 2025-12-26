package br.com.mytaxi.integrationtests.cucumber;

import br.com.mytaxi.integrationtests.config.apimock.ApiMockIntegration;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.spring.CucumberContextConfiguration;
import io.restassured.RestAssured;
import jakarta.inject.Inject;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public class CucumberSpringConfiguration {

    @Inject
    private ApiMockIntegration apiMockIntegration;

    @Inject
    private ScenarioContext scenarioContext;

    static {
        RestAssured.port = 8080;
    }

    @Before
    public void setUp() {
        scenarioContext.reset();
    }

    @After
    public void tearDown() {
        apiMockIntegration.resetAll();
    }

}
