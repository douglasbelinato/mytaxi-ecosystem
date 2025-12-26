package br.com.mytaxi.integrationtests.cucumber;

import br.com.mytaxi.infrastructure.input.rest.dto.exception.ExceptionRS;
import br.com.mytaxi.infrastructure.input.rest.dto.requestride.RequestRideRS;
import io.cucumber.spring.ScenarioScope;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@ScenarioScope
@Getter
@Setter
public class ScenarioContext {

    private String passengerId;
    private String driverId;
    private String rideId;
    private RequestRideRS requestRideResponse;
    private ExceptionRS exceptionResponse;
    private int expectedHttpStatus;

    private final Map<String, String> passengerIds = new HashMap<>();
    private final Map<String, String> driverIds = new HashMap<>();
    private final Map<String, String> rideIds = new HashMap<>();

    public void setPassengerId(String identifier, String id) {
        passengerIds.put(identifier, id);
        if (identifier.isEmpty() || identifier.equals("default")) {
            this.passengerId = id;
        }
    }

    public String getPassengerId(String identifier) {
        if (identifier == null || identifier.isEmpty() || identifier.equals("default")) {
            return passengerId;
        }
        return passengerIds.get(identifier);
    }

    public void setDriverId(String identifier, String id) {
        driverIds.put(identifier, id);
        if (identifier.isEmpty() || identifier.equals("default")) {
            this.driverId = id;
        }
    }

    public String getDriverId(String identifier) {
        if (identifier == null || identifier.isEmpty() || identifier.equals("default")) {
            return driverId;
        }
        return driverIds.get(identifier);
    }

    public void setRideId(String identifier, String id) {
        rideIds.put(identifier, id);
        if (identifier.isEmpty() || identifier.equals("default")) {
            this.rideId = id;
        }
    }

    public String getRideId(String identifier) {
        if (identifier == null || identifier.isEmpty() || identifier.equals("default")) {
            return rideId;
        }
        return rideIds.get(identifier);
    }

    public void reset() {
        passengerId = null;
        driverId = null;
        rideId = null;
        requestRideResponse = null;
        exceptionResponse = null;
        expectedHttpStatus = 0;
        passengerIds.clear();
        driverIds.clear();
        rideIds.clear();
    }

}
