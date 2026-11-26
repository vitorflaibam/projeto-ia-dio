# Caderno de Gastos

O Caderno de Gastos é uma API para registrar despesas do dia a dia a partir de texto ou áudio. A pessoa pode enviar uma frase como “paguei 42 reais no mercado”, receber a confirmação narrada e depois consultar os lançamentos e o total de um mês.

O projeto usa IA apenas como ponte de conversa: ela transcreve o áudio, identifica a intenção e chama operações reais da aplicação. As regras de registro, consulta e cálculo continuam isoladas no domínio.

## O que foi desenvolvido

- Registro manual de despesas por API REST
- Registro por comando de voz, com transcrição e resposta em MP3
- Categorias voltadas à rotina: alimentação, moradia, transporte, bem-estar, lazer e outros
- Consulta por categoria
- Fechamento mensal com quantidade de lançamentos e valor total
- Banco H2 local para iniciar sem Docker

## Tecnologias

- Java 25
- Spring Boot 4
- Spring Web e Spring Data JPA
- Spring AI com OpenAI
- H2
- Gradle

## Como executar

É necessário Java 25 e uma chave da OpenAI para o fluxo de áudio.

```powershell
$env:OPENAI_API_KEY="chave_api"
gradle bootRun
```

A aplicação inicia em `http://localhost:8080`. O console do H2 fica em `http://localhost:8080/h2-console`.

## Testando a API

Registrar uma despesa sem áudio:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/lancamentos -ContentType "application/json" -Body '{"descricao":"Almoço no centro","valorEmCentavos":4250,"categoria":"ALIMENTACAO","ocorridoEm":"2026-11-26"}'
```

Consultar o fechamento de novembro de 2026:

```powershell
Invoke-RestMethod "http://localhost:8080/lancamentos/resumo?mes=2026-11"
```

Enviar um áudio em português e salvar a resposta narrada:

```powershell
curl.exe -X POST "http://localhost:8080/lancamentos/conversa" -F "audio=@comando.m4a" --output resposta.mp3
```

## Fluxo de voz

1. O arquivo é enviado para `/lancamentos/conversa`.
2. A fala é transcrita.
3. O modelo entende se deve registrar uma despesa ou fechar um mês.
4. Uma ferramenta chama o caso de uso apropriado.
5. A resposta final é convertida em áudio MP3.

## Aprendizados

Neste desafio, pratiquei a integração de modelos de linguagem com Spring AI, tool calling, transcrição, síntese de voz, persistência com JPA e a separação entre HTTP, casos de uso e domínio.
