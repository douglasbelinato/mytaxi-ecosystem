package br.com.mytaxi.infrastructure.output.database.entity.ride;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "ride")
public final class RideEntity {

    @Id
    private String id;
    @Column(name = "passenger_id")
    private String passengerId;
    @Column(name = "driver_id")
    private String driverId;
    private String status;
    private BigDecimal fare;
    private Double distance;
    @Column(name = "latitude_from")
    private Double latitudeFrom;
    @Column(name = "longitude_from")
    private Double longitudeFrom;
    @Column(name = "latitude_to")
    private Double latitudeTo;
    @Column(name = "longitude_to")
    private Double longitudeTo;
    @Column(name = "last_latitude")
    private Double lastLatitude;
    @Column(name = "last_longitude")
    private Double lastLongitude;

}
