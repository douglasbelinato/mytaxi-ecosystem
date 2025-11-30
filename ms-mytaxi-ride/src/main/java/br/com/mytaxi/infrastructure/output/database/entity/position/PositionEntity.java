package br.com.mytaxi.infrastructure.output.database.entity.position;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "position")
public class PositionEntity {

    @Id
    private String id;
    @Column(name = "ride_id")
    private String rideId;
    private Double latitude;
    private Double longitude;

}
