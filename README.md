# Checkpoint 5 - Bug Hunt PetFiap

## Identificacao

**Grupo:** preencher

| Integrante | RM | Turma |
|---|---|---|
| preencher | preencher | preencher |

| Campo | |
|---|---|
| **Total de bugs corrigidos** | em andamento |
| **Total de ajustes de Clean Code** | em andamento |
| **Total de testes novos escritos** | em andamento |
| **Suite final (Run As -> JUnit Test)** | bloqueada localmente: Maven nao esta instalado no PATH |

## Parte 1 - Bugs encontrados

| # | Sintoma observado (o que fiz/vi) | Causa raiz (arquivo e linha aproximada) | Correcao aplicada | Conceito da disciplina |
|---|---|---|---|---|
| bug01 | Ao montar atendimento completo pelo Builder, o nome do pet chegava como `null`. | `AtendimentoBuilder.comPet`: atribuicao `petNome = petNome` alterava apenas o parametro local. | Troquei para `this.petNome = petNome`, preservando o valor informado no builder. | Builder e escopo de variaveis com `this`. |
| bug02 | O Builder aceitava atendimento sem nome do pet ou sem porte. | `AtendimentoBuilder.construir`: nao validava campos obrigatorios antes de chamar a factory. | Adicionei validacao de nome e porte, recusando valores nulos ou em branco. | Builder e invariantes de objeto valido. |
| bug03 | Criar atendimento com tipo `TOSA` retornava objeto da classe `Banho`. | `AtendimentoFactory.criar`: o `case "TOSA"` chamava `new Banho(...)`. | Troquei o caso para `new Tosa(...)`. | Factory Method e polimorfismo. |
| bug04 | A consulta criada pela factory nao carregava nome, porte nem tutor. | `ConsultaVeterinaria` chamava `super()` no construtor completo e descartava os parametros recebidos. | Chamei o construtor completo de `Atendimento` com protocolo, pet, porte, tutor e data. | Heranca e construtores. |
| bug05 | Duas chamadas de `GeradorProtocolo.getInstancia()` retornavam objetos diferentes e reiniciavam a numeracao. | `GeradorProtocolo.getInstancia`: criava `new GeradorProtocolo()` sem armazenar em `instancia`. | Guardei o objeto criado no campo estatico antes de retorna-lo. | Singleton e estado global sequencial. |
| bug06 | Agendar o mesmo pet no mesmo horario podia salvar duplicado quando a data vinha em outro objeto. | `AgendaService.agendar`: comparava `String` e `LocalDateTime` com `==`. | Troquei as comparacoes para `.equals()`. | Igualdade de objetos: referencia versus valor. |
| bug07 | Buscar id inexistente retornava `null` em vez de lancar `AtendimentoNaoEncontradoException`. | `AgendaService.buscarPorId`: `catch (Exception)` capturava a excecao de negocio e escondia a falha. | Removi o `try/catch` generico e deixei `orElseThrow` propagar a excecao correta. | Excecoes de dominio e fail fast. |

## Parte 2 - Ajustes de Clean Code

| # | Onde estava | Qual principio/boas praticas era violado | O que eu mudei |
|---|---|---|---|

## Parte 3 - Testes novos (regras que estavam sem cobertura)

| # | Teste escrito (classe.metodo) | Regra coberta | Resultado ao escrever (vermelho/verde) |
|---|---|---|---|

## Parte 4 - Perguntas de reflexao

### 1. A suite como contrato (Aula 15)

Usei os testes entregues como especificacao executavel. Quando `AtendimentoBuilderTest.deveMontarAtendimentoCompleto` esperava `Rex` e recebia `null`, a falha apontou diretamente para o caminho `Builder -> Factory -> Model`, entao a investigacao ficou focada no ponto em que o valor se perdia. Esse fluxo e melhor do que testar tudo com curl porque roda sem servidor, sem banco e com mensagens precisas de esperado versus obtido.

### 2. Mock e injecao de dependencia (Aulas 13 a 15)

No teste, o Mockito cria o `AtendimentoRepository` falso com `@Mock` e injeta no `AgendaService` com `@InjectMocks`. Em producao, quem faz esse papel e o Spring, usando o `@Autowired` para entregar um bean real do repository. Como o teste usa um mock, ele controla as respostas de `findByPetNome`, `findById` e `save`, sem subir Spring e sem conectar em banco.

### 3. `==` vs `.equals()` (Aula 7)

A comparacao de conflito da agenda precisa comparar valores, nao referencias. Duas `String` ou duas `LocalDateTime` podem ter o mesmo conteudo e ainda serem objetos diferentes; com `==`, o conflito pode passar despercebido. Literais como `"Rex"` podem funcionar por sorte por causa do pool de strings, mas dados vindos de requisicoes ou testes normalmente chegam em novas instancias. A correcao usa `.equals()` para comparar o valor.

### 4. Sobrescrita vs sobrecarga (Aula 7)

`Tosa` parecia redefinir a duracao, mas declarou `getDuracaoMinutos(String porte)`. Isso e sobrecarga, porque a assinatura mudou; a chamada polimorfica `getDuracaoMinutos()` continuava usando o metodo da classe `Atendimento`. Com `@Override`, o compilador teria recusado essa assinatura errada.

### 5. Singleton manual vs bean do Spring (Aula 14)

`GeradorProtocolo` deve garantir uma unica instancia e uma sequencia global de protocolos. O bug era criar um objeto novo a cada chamada de `getInstancia()`, reiniciando o contador. Ja o `AgendaService` e controlado pelo container do Spring como `@Service`; por padrao, o Spring entrega um bean singleton e gerencia o ciclo de vida dele.

### 6. Cobertura de testes: onde parar? (Aula 15)

Vale manter tambem testes que ficaram verdes de primeira, porque eles protegem regras do contrato que poderiam quebrar depois, como cancelar atendimento agendado. Em projeto real com prazo, eu priorizaria caminhos de erro e regras de negocio com maior impacto: status invalido, conflito de agenda, validacao de entrada e calculos de preco/duracao. Cobertura de 100% nao vale muito se nao protege comportamento relevante.

## Parte 5 - Espaco livre (opcional)

Maven nao esta disponivel no PATH deste ambiente, entao a execucao local da suite ficou bloqueada aqui.
