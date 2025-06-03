package br.com.mytaxi.domain.model.account;

import br.com.mytaxi.domain.model.common.Candidate;
import br.com.mytaxi.domain.model.common.CarPlate;
import br.com.mytaxi.domain.model.common.Constraints;
import br.com.mytaxi.domain.model.common.Cpf;
import br.com.mytaxi.domain.model.common.Email;
import br.com.mytaxi.domain.model.common.Id;
import br.com.mytaxi.domain.model.common.Text;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
@ToString
public final class Account {

    @EqualsAndHashCode.Include
    private final Id id;
    private final Text name;
    private final Text surname;
    private final Email email;
    private final Cpf cpf;
    private final CarPlate carPlate;
    private final Text password;
    private final boolean isPassenger;
    private final boolean isDriver;

    public static Candidate<Account> create(String name, String surname, String email, String cpf, String carPlate,
                                            String password, boolean isPassenger, boolean isDriver) {
        var nameCandidate = Text.create("name", name, 3, 20);
        var surnameCandidate = Text.create("surname", surname, 3, 80);
        var emailCandidate = Email.create(email, 12, 80);
        var cpfCandidate = Cpf.create(cpf);
        var carPlateCandidate = CarPlate.create(carPlate);
        CarPlate carPlateDomain = null;
        var passwordCandidate = Text.create("password", password, 6, 20);
        var constraintsBuilder = Constraints.builder()
                .fieldName("account")
                .addFromCandidates(List.of(
                        nameCandidate,
                        surnameCandidate,
                        emailCandidate,
                        cpfCandidate,
                        passwordCandidate));
        if (isPassenger == isDriver) {
            constraintsBuilder.add("isPassenger",
                    "choose registration either as a passenger or a driver");
        }
        if (isDriver) {
            constraintsBuilder.addFromCandidates(List.of(carPlateCandidate));
            carPlateDomain = carPlateCandidate.getValue();
        }
        var constraints = constraintsBuilder.build();
        var candidateBuilder = Candidate.<Account>builder().constraints(constraints);
        if (constraints.doesNotExist()) {
            candidateBuilder.value(Account.builder()
                    .id(Id.create().getValue())
                    .name(nameCandidate.getValue())
                    .surname(surnameCandidate.getValue())
                    .email(emailCandidate.getValue())
                    .cpf(cpfCandidate.getValue())
                    .carPlate(carPlateDomain)
                    .password(passwordCandidate.getValue())
                    .isPassenger(isPassenger)
                    .isDriver(isDriver)
                    .build());
        }
        return candidateBuilder.build();
    }

    public static Candidate<Account> of(String id, String name, String surname, String email, String cpf,
                                        String carPlate, String password, boolean isPassenger,
                                        boolean isDriver) {
        var idCandidate = Id.of(id);
        var nameCandidate = Text.create("name", name, 3, 20);
        var surnameCandidate = Text.create("surname", surname, 3, 80);
        var emailCandidate = Email.create(email, 12, 80);
        var cpfCandidate = Cpf.create(cpf);
        var carPlateCandidate = CarPlate.create(carPlate);
        CarPlate carPlateDomain = null;
        var passwordCandidate = Text.create("password", password, 6, 20);
        var constraintsBuilder = Constraints.builder()
                .fieldName("account")
                .addFromCandidates(List.of(
                        idCandidate,
                        nameCandidate,
                        surnameCandidate,
                        emailCandidate,
                        cpfCandidate,
                        passwordCandidate));
        if (isPassenger == isDriver) {
            constraintsBuilder.add("isPassenger",
                    "choose registration either as a passenger or a driver");
        }
        if (isDriver) {
            constraintsBuilder.addFromCandidates(List.of(carPlateCandidate));
            carPlateDomain = carPlateCandidate.getValue();
        }
        var constraints = constraintsBuilder.build();
        var candidateBuilder = Candidate.<Account>builder().constraints(constraints);
        if (constraints.doesNotExist()) {
            candidateBuilder.value(Account.builder()
                    .id(idCandidate.getValue())
                    .name(nameCandidate.getValue())
                    .surname(surnameCandidate.getValue())
                    .email(emailCandidate.getValue())
                    .cpf(cpfCandidate.getValue())
                    .carPlate(carPlateDomain)
                    .password(passwordCandidate.getValue())
                    .isPassenger(isPassenger)
                    .isDriver(isDriver)
                    .build());
        }
        return candidateBuilder.build();
    }

}
