# geographic

Segue a informação de todos os itens atendidos no desenvolvimento.

DESAFIO 
==================================================================

O desafio consiste em criar um microservice que consulta 2 APIs externas, gera um CSV e um JSON e faz o download. 

ESPECIFICAÇÕES 
==================================================================
a. API usada para consultar os estados do Brasil:
    https://servicodados.ibge.gov.br/api/v1/localidades/estados
b. API usada para consultar as cidades:
    https://servicodados.ibge.gov.br/api/v1/localidades/estados/{UF}/municipios 
c. Os campos do CSV/JSON são: 
   - idEstado 
   - siglaEstado 
   - regiaoNome 
   - nomeCidade 
   - nomeMesorregiao 
   - nomeFormatado {cidade/UF}
d. Diagrama de classes gerado na pasta /uml
e. NO CSV, a primeira linha (cabeçalho) contem o nome de cada campo e a(s) linha(s) subsequente(s) contem os valores resultante da consulta a API.
f. Um enpoint generico retorna um JSON ou CSV (todo preparado para implementar o XML) com todos os dados.
g. Um endpoint que retorna todos os dados da consulta em formato JSON (sem download).
h. Um endpoint que envia um parâmetro ("nomeCidade"), e retorna somente o ID da cidade.  
i. Foi implementado caché no item (d), para que quando o nome de uma cidade for enviado mais de uma vez, evitar a chamada do serviço externo.
j. O endpoint do CSV/JSON retorna um objeto do tipo "java.io.OutputStream" como saída da transformação.

TECNOLOGIA
==================================================================
- Spring boot e suas bibliotecas
- Logger das clases usando org.slf4j.LoggerFactory
- Testes Unitarios usando JUnit
- A estrutura da biblioteca é flexível a ponto de permitir o fácil desenvolvimento de futuros formatos de exportação, como XML. 
- Implementado Adapter Pattern para a gerar os arquivos.
- Implementação de Circuit Breaker no acesso aos serviços externos
- Implementação do Swagger nas API
- Implementação do Cache com @Cacheable

