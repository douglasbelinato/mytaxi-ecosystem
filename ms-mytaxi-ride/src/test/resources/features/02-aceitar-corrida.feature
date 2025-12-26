# language: pt
Funcionalidade: Aceitar Corrida
  Como um motorista
  Eu quero aceitar uma corrida solicitada
  Para que eu possa atender o passageiro

  Contexto:
    Dado que o serviço de corridas está disponível

  Cenário: Aceitar uma corrida com sucesso
    Dado que existe um passageiro com ID gerado
    E que existe um motorista com ID gerado
    E o mock de busca de conta está configurado para o passageiro
    E o mock de busca de conta está configurado para o motorista
    E existe uma corrida solicitada pelo passageiro
    Quando o motorista aceita a corrida
    Entao a corrida deve ser aceita com sucesso

  Cenário: Não deve permitir que um passageiro aceite uma corrida
    Dado que existe um passageiro com ID gerado
    E o mock de busca de conta está configurado para o passageiro
    E existe uma corrida solicitada pelo passageiro
    Quando o passageiro tenta aceitar a corrida
    Entao a aceitação deve falhar com status 422
    E a mensagem de erro deve ser "validation.ride.account.do.not.belong.to.a.driver"

  Cenário: Não deve permitir que um motorista com corrida ativa aceite outra corrida
    Dado que existe um passageiro "A" com ID gerado
    E que existe um passageiro "B" com ID gerado
    E que existe um motorista com ID gerado
    E o mock de busca de conta está configurado para o passageiro "A"
    E o mock de busca de conta está configurado para o passageiro "B"
    E o mock de busca de conta está configurado para o motorista
    E existe uma corrida solicitada pelo passageiro "A"
    E o motorista já aceitou a corrida do passageiro "A"
    E existe uma corrida solicitada pelo passageiro "B"
    Quando o motorista tenta aceitar a corrida do passageiro "B"
    Entao a aceitação deve falhar com status 422
    E a mensagem de erro deve ser "validation.driver.already.has.a.ride.assigned"

  Cenário: Não deve permitir aceitar uma corrida com status inválido
    Dado que existe um passageiro com ID gerado
    E que existe um motorista "A" com ID gerado
    E que existe um motorista "B" com ID gerado
    E o mock de busca de conta está configurado para o passageiro
    E o mock de busca de conta está configurado para o motorista "A"
    E o mock de busca de conta está configurado para o motorista "B"
    E existe uma corrida solicitada pelo passageiro
    E o motorista "A" já aceitou a corrida
    Quando o motorista "B" tenta aceitar a mesma corrida
    Entao a aceitação deve falhar com status 422
    E a mensagem de erro deve ser "validation.invalid.ride.status.to.be.accepted"

  Cenário: Não deve permitir aceitar uma corrida que não existe
    Dado que existe um motorista com ID gerado
    E existe um ID de corrida inexistente
    Quando o motorista tenta aceitar a corrida inexistente
    Entao a aceitação deve falhar com status 404
    E a mensagem de erro deve ser "validation.ride.not.found"