# language: pt
Funcionalidade: Solicitar Corrida
  Como um passageiro
  Eu quero solicitar uma corrida
  Para que eu possa me deslocar de um ponto a outro

  Contexto:
    Dado que o serviço de corridas está disponível

  Cenário: Solicitar uma corrida com sucesso
    Dado que existe um passageiro com ID gerado
    E o mock de busca de conta está configurado para o passageiro
    Quando o passageiro solicita uma corrida
    Entao a corrida deve ser criada com sucesso
    E o ID da corrida deve ser válido

  Cenário: Não deve permitir que um motorista solicite uma corrida
    Dado que existe um motorista com ID gerado
    E o mock de busca de conta está configurado para o motorista
    Quando o motorista tenta solicitar uma corrida
    Entao a solicitação deve falhar com status 422
    E a mensagem de erro deve ser "validation.ride.account.do.not.belong.to.a.passenger"

  Cenário: Não deve permitir que um passageiro com corrida ativa solicite outra corrida
    Dado que existe um passageiro com ID gerado
    E o mock de busca de conta está configurado para o passageiro
    E o passageiro já possui uma corrida solicitada
    Quando o passageiro tenta solicitar outra corrida
    Entao a solicitação deve falhar com status 422
    E a mensagem de erro deve ser "validation.active.ride.found"
