# language: pt
Funcionalidade: Buscar Corrida
  Como um usuário do sistema
  Eu quero buscar informações de uma corrida
  Para que eu possa visualizar seus detalhes

  Contexto:
    Dado que o serviço de corridas está disponível

  Cenário: Buscar uma corrida solicitada com sucesso
    Dado que existe um passageiro com ID gerado
    E o mock de busca de conta retorna os dados:
      | nome  | sobrenome | email              | isPassenger |
      | Alice | Silva     | alice@host.com.br  | true        |
    E existe uma corrida solicitada pelo passageiro com:
      | latitudeFrom          | longitudeFrom      | latitudeTo           | longitudeTo         |
      | -23.529287790573242  | -46.675448474977   | -24.908133115138398  | -53.49481208223108  |
    Quando eu busco a corrida pelo ID
    Entao a busca deve retornar sucesso com status 200
    E os detalhes da corrida devem conter:
      | campo            | valor                |
      | passengerId      | <passageiro-id>      |
      | passengerName    | Alice                |
      | passengerSurname | Silva                |
      | passengerEmail   | alice@host.com.br    |
      | status           | REQUESTED            |
      | driverId         | null                 |

  Cenário: Não deve encontrar uma corrida inexistente
    Dado que existe um ID de corrida inexistente
    Quando eu busco a corrida inexistente pelo ID
    Entao a busca deve falhar com status 404
    E a mensagem de erro deve ser "validation.ride.not.found"
