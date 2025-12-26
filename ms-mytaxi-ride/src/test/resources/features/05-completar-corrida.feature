# language: pt
Funcionalidade: Completar Corrida
  Como um motorista
  Eu quero completar uma corrida em andamento
  Para que o serviço seja finalizado e o pagamento processado

  Contexto:
    Dado que o serviço de corridas está disponível

  Cenário: Completar uma corrida com sucesso
    Dado que existe um passageiro com ID gerado
    E que existe um motorista com ID gerado
    E o mock de busca de conta está configurado para o passageiro
    E o mock de busca de conta está configurado para o motorista
    E o mock de pagamento está configurado
    E existe uma corrida solicitada pelo passageiro
    E o motorista aceitou a corrida
    E o motorista iniciou a corrida
    E o motorista registrou as seguintes posições:
      | latitude              | longitude             |
      | -23.529287790573242  | -46.675448474977      |
      | -24.908133115138398  | -53.49481208223108    |
    Quando o motorista completa a corrida com token de cartão "card_1N3T00LkdIwHu7ixRdxpVI1Q"
    Entao a corrida deve ser completada com sucesso

  Cenário: Não deve permitir completar uma corrida com status inválido
    Dado que existe um passageiro com ID gerado
    E o mock de busca de conta está configurado para o passageiro
    E existe uma corrida solicitada pelo passageiro
    Quando o motorista tenta completar a corrida sem ter iniciado
    Entao a conclusão deve falhar com status 422
    E a mensagem de erro deve ser "validation.invalid.ride.status.to.be.completed"

  Cenário: Não deve permitir completar uma corrida que não existe
    Dado que existe um ID de corrida inexistente
    Quando o motorista tenta completar a corrida inexistente
    Entao a conclusão deve falhar com status 404
    E a mensagem de erro deve ser "validation.ride.not.found"
