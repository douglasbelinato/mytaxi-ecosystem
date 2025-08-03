package br.com.mytaxi.infrastructure.interfaces.output.database.entity.transaction;

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
@Table(name = "transaction")
public class TransactionEntity {

    @Id
    private String id;
    @Column(name = "ride_id")
    private String rideId;
    private BigDecimal amount;
    private String status;

}
