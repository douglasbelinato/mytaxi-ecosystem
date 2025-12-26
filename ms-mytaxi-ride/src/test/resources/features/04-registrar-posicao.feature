# language: pt
Funcionalidade: Registrar Posição
  Como um motorista
  Eu quero registrar minha posição durante a corrida
  Para que o passageiro possa acompanhar minha localização

  Contexto:
    Dado que o serviço de corridas está disponível

  Cenário: Registrar posição com sucesso
    Dado que existe um passageiro com ID gerado
    E que existe um motorista com ID gerado
    E o mock de busca de conta está configurado para o passageiro
    E o mock de busca de conta está configurado para o motorista
    E existe uma corrida solicitada pelo passageiro
    E o motorista aceitou a corrida
    E o motorista iniciou a corrida
    Quando o motorista registra a posição com latitude "-24.955282" e longitude "-53.459054"
    Entao a posição deve ser registrada com sucesso

  Cenário: Não deve permitir registrar posição com latitude e longitude inválidas
    Dado que existe um ID de corrida qualquer
    Quando o motorista tenta registrar a posição com latitude "-120.0" e longitude "-290.0"
    Entao o registro deve falhar com status 422
    E a mensagem de erro deve conter "latitude must be between -90 and 90 degrees and longitude must be between -180 and 180 degrees"

  Cenário: Não deve permitir registrar posição em corrida com status inválido
    Dado que existe um passageiro com ID gerado
    E o mock de busca de conta está configurado para o passageiro
    E existe uma corrida solicitada pelo passageiro
    Quando o motorista tenta registrar a posição sem ter iniciado a corrida
    Entao o registro deve falhar com status 422
    E a mensagem de erro deve ser "validation.ride.must.be.in.progress.to.register.position"

  Cenário: Não deve permitir registrar posição em corrida que não existe
    Dado que existe um ID de corrida inexistente
    Quando o motorista tenta registrar a posição na corrida inexistente
    Entao o registro deve falhar com status 404
    E a mensagem de erro deve ser "validation.ride.not.found"
