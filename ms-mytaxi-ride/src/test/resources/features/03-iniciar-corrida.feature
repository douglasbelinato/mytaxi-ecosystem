# language: pt
Funcionalidade: Iniciar Corrida
  Como um motorista
  Eu quero iniciar uma corrida aceita
  Para que o passageiro comece a ser atendido

  Contexto:
    Dado que o serviço de corridas está disponível

  Cenário: Iniciar uma corrida com sucesso
    Dado que existe um passageiro com ID gerado
    E que existe um motorista com ID gerado
    E o mock de busca de conta está configurado para o passageiro
    E o mock de busca de conta está configurado para o motorista
    E existe uma corrida solicitada pelo passageiro
    E o motorista aceitou a corrida
    Quando o motorista inicia a corrida
    Entao a corrida deve ser iniciada com sucesso

  Cenário: Não deve permitir iniciar uma corrida com status inválido
    Dado que existe um passageiro com ID gerado
    E o mock de busca de conta está configurado para o passageiro
    E existe uma corrida solicitada pelo passageiro
    Quando o motorista tenta iniciar a corrida sem ter aceitado
    Entao o início deve falhar com status 422
    E a mensagem de erro deve ser "validation.invalid.ride.status.to.be.in.progress"

  Cenário: Não deve permitir iniciar uma corrida que não existe
    Dado que existe um ID de corrida inexistente
    Quando o motorista tenta iniciar a corrida inexistente
    Entao o início deve falhar com status 404
    E a mensagem de erro deve ser "validation.ride.not.found"