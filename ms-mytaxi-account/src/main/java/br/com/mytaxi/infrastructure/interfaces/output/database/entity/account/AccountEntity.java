package br.com.mytaxi.infrastructure.interfaces.output.database.entity.account;

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
@Table(name = "account")
public final class AccountEntity {

    @Id
    private String id;
    private String name;
    private String surname;
    private String email;
    private String cpf;
    @Column(name = "car_plate")
    private String carPlate;
    private String password;
    @Column(name = "is_passenger")
    private boolean isPassenger;
    @Column(name = "is_driver")
    private boolean isDriver;

}
