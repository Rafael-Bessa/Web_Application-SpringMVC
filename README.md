<h1 align="center">
  <p align="center">WEB APPLICATION - SPRING MVC</p>
</h1>

![mvc](https://user-images.githubusercontent.com/104053775/204969363-fd6d0a47-2a55-425f-9ea4-f21b55ae496b.jpg)


# ** Sobre o projeto / Regras de negócio **
### Precisamos desenvolver uma aplicação Web tradicional (server-side) para realizar análise de milhares de transações financeiras e identificar possíveis transações suspeitas.

### Para realizar essa análise, precisaremos desenvolver uma tela para upload de arquivos, que deve suportar formatos CSV(Valores separados por vírgula), bem como implementar algoritmos para extração, validação e persistência das informações. Boas práticas de orientação a objetos, design patterns e princípios SOLID serão essenciais. Também será desenvolvido o mecanismo de autenticação de usuários.

- **Importar transações** : Desenvolva uma tela contendo um formulário que será utilizado para importar as transações na aplicação. Esse formulário terá apenas um campo de upload de arquivo

- A funcionalidade de upload de arquivos deve permitir importar arquivos no formato CSV, que serão os arquivos contendo todas as transações financeiras dos bancos realizadas em um determinado dia. Cada linha do arquivo CSV representa uma transação financeira distinta e as informações dela são separadas por vírgula. Uma transação financeira em nossa aplicação representa uma transferência de valor entre contas bancárias, e possui as seguintes informações:

- Banco origem 
- Agência origem 
- Conta origem 
- Banco destino
- Agência destino
- Conta destino
- Valor da transação
- Data e hora da transação

Exemplo: BANCO SANTANDER , 0001 , 00002-1 , BANCO BRADESCO , 0001 , 00001-1 , 79800.22 , 2022-01-01T08:44:00

### Após upload do arquivo CSV e leitura das transações contidas nele, será necessário gravar cada transação em banco de dados. Você deve configurar a aplicação para se conectar com algum banco de dados de sua preferência, como MySQL, Postgres, Mongo, etc., e implementar um código para salvar nele cada transação lida do arquivo CSV.

Algumas considerações importantes quanto à essa funcionalidade: **REGRAS DE NEGÓCIOS**

- Vamos considerar que os arquivos CSV são gerados por outra aplicação, que é responsável pela coleta das transações de cada instituição financeira;
- Cada arquivo CSV contém transações de apenas um determinado dia;
- A aplicação que faz a geração dos arquivos CSV pode não ser confiável e portanto devemos validar as informações antes de salvar em nosso banco de dados.
Portanto, antes de salvar as transações no banco de dados devemos seguir as seguintes regras:

- Se o arquivo que foi feito upload estiver vazio, uma mensagem de erro deve ser exibida para o usuário, indicando tal situação;

- Ler a primeira transação(primeira linha do arquivo csv) para determinar qual a data das transações desse arquivo em específico;

- Se alguma transação posterior estiver com outra data diferente, ela deve ser ignorada e não ser salva no banco de dados;

- A aplicação não deve "duplicar" transações de um determinado dia, ou seja, se o upload de transações de um determinado dia já tiver sido realizado anteriormente, uma mensagem de erro deve ser exibida ao usuário, indicando que as transações daquela data já foram realizadas;

- Todas as informações da transação são obrigatórias, ou seja, se alguma transação estiver com alguma informação faltando, ela também deve ser ignorada e nao ser salva no banco de dados.

### Para ter um melhor controle das importações que já foram realizadas na aplicação, será necessário deixar registrado cada importação que for realizada.

- Altere a código que salva as transações no banco de dados pra que também seja salva a importação que acabou de ser realizada. Por enquanto será necessário gravar apenas duas informações quanto a isso: data/hora que a importação foi realizada e data das transações dessa importação.

- Além disso, será necessário exibir numa tabela as importações que foram realizadas. Pode ser em uma página separada ou na própria página de importação, abaixo do formulário de upload.

- Os registros devem ser ordenados pela data das transações, de maneira **decrescente**.

<img src="https://trello.com/1/cards/623334966039901de5cb571e/attachments/623338e9c5d0b66fbb297f1a/previews/623338e9c5d0b66fbb297f6b/download/Screenshot_from_2022-03-17_10-26-46.png" width="1200" height ="500">

### Implemente o controle de acesso na aplicação, que deverá conter uma página de login. A aplicação também deve restringir o acesso à todas as páginas(exceto a página de login) para os usuários que não estejam previamente autenticados, bem como um botão para o usuário realizar o logout.

### Para melhorar o controle das importações realizadas na aplicação, será necessário vincular o usuário que efetuou cada importação. Na funcionalidade de importar transações será necessário registrar o usuário que está realizando a importação, que no caso será o usuário logado disparando tal ação.
<hr>

### Para visualizar os detalhes das importações, será necessário criar uma página que liste todas as transações que foram importadas. Na tabela de listagem de importações devemos adicionar uma coluna com um link que carrega uma página contendo os detalhes daquela importação, ou seja, a lista com todas as transações daquela importação em si. A única restrição é que essa página de detalhes deve trazer todas as transações que foram importadas do arquivo, ou seja, não pode utilizar paginação, por mais que sejam milhares de registros.

<img src="https://trello.com/1/cards/62334d0f29f5f3573f9dda15/attachments/62337cf8b40a5879fbb71b6a/previews/62337cf9b40a5879fbb71b9b/download/Screenshot_from_2022-03-17_15-24-29.png" width="1200" height ="500">

<hr>

## Para identificar transações e contas suspeitas, será necessário desenvolver uma funcionalidade que realize uma análise de transações. Essa é a funcionalidade mais importante da aplicação!
Ela deve analisar todas as transações de determinado mês e listar quais delas são consideradas suspeitas, além de também listar as contas bancárias com movimentações suspeitas no mês.

Uma transação deve ser considerada suspeita se o seu valor for igual ou superior a **R$100.000,00.**

Uma conta bancária deve ser considerada suspeita se o somatório de sua movimentação no mês for superior a **R$1.000.000,00**, seja enviando ou recebendo tal quantia.

<hr>

## ✔️Tecnologias utilizadas neste Repositório

- ``Java 17``
- ``IntelliJ``
- ``SPRING 2.7.5``
- ``SPRING SECURITY 5.7``
- ``Bean Validation``
- ``Lombok``
- ``SPRING DATA``
- ``H2 DATABASE (in memory)``
- ``BOOTSTRAP``
- ``HTML5 / CSS3``
- ``THYMELEAF``
- ``Selenium``

<hr>
<hr>
# COMO EU FIZ O PROJETO 
