Flight Facilities

Flight Facilities é uma API de simulação de um portal de vendas de passagens aéreas, desenvolvida com o intuito de estimular o aprendizado em java e serviços cloud.

Desenvolvedores:

Alline Morais;

André Beraldi;

Cynthia Lima;

Raquel Melo;

William Vieira.
Utilização

Para iniciar a simulação é necessário registrar um usuário e em seguida realizar o login, seguindo o padrão abaixo:

    REGISTRO -> http://18.230.155.86/usuarios

{ "nome": "nome1", "email": "email@gmail.com", "senha": "senha123" }

    LOGIN -> http://18.230.155.86/login

{ "email": "email@gmail.com", "senha": "senha123" }

Após a requisição ser concluída, acessar a aba Authorization -> Header e copiar a informação do campo Authorization, excluindo apenas a palavra Bearer e utilizar essa informação no cabeçalho das requisições seguintes.

Em seguida, alimentar a base de dados com os pilares básicos da venda. É possível fazer isso através dos endpoints indicados abaixo:

Nas requisições POST, preencher as informações de

    EMPRESA -> http://18.230.155.86/empresas

{ "nome": "AZUL" }

    VOO -> http://18.230.155.86/voos

{ "idEmpresa" : 1, "origem" : "VCP", "destino" : "LIS", "valor": 2500.00, "assentosDisponiveis" : 50 }

Após cadastrar empresa e vôo, podemos iniciar uma simulação de compra de passagem com o metodo PUT. A partir desse ponto, não é mais necessário adicionar o token no cabeçalho das requisições.

    SIMULAÇÃO -> http://18.230.155.86/simulacao

{ "aeporigem" : "VCP", "aepdestino" : "LIS" }

Enviando esta requisição, você recebe as informações sobre as possibilidades de passagens para escolha, algo semelhante à:

[{ "id": 3, "idEmpresa": 3, "origem": "GRU", "destino": "BCN", "valor": 1000.0, "assentosDisponiveis": 10 }, { "id": 4, "idEmpresa": 2, "origem": "GRU", "destino": "BCN", "valor": 2000.0, "assentosDisponiveis": 149 }]

Escolhendo uma possíbilidade, anote o ID do vôo desejado e então faça a compra através do POST abaixo:

    PASSAGEM -> http://18.230.155.86/passagens

{ "idVoo" : 4, "documentoPassageiro" : "W307661-E", "tipoDeTarifa" : "PROMO" }

Após realizar a compra, dependendo do tipo de tarifa é possível consultar, atualizar e cancelar a passagem.

Regras de tarifas para as passagens aéreas:

    PROMO -> para a compra é aplicado um desconto de 20% no valor base da passagem porém não é permitida alteração ou cancelamento;

    STAND -> preço base é aplicado, é possivel alteração ou cancelamento porém com pagamento de uma taxa de R$100,00;

    FLEX -> para a compra é acrescido do valor base da passagem 10% porém é permitida alteração ou cancelamento sem qualquer cobrança.

