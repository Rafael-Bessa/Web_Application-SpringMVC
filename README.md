# Web_Application-SpringMVC

# Sobre o projeto / Regras de negócio 
### Precisamos desenvolver uma aplicação Web tradicional (server-side) para realizar análise de milhares de transações financeiras e identificar possíveis transações suspeitas.

### Para realizar essa análise, precisaremos desenvolver uma tela para upload de arquivos, que deve suportar diversos formatos distintos, bem como implementar algoritmos para extração, validação e persistência das informações. Boas práticas de orientação a objetos, design patterns e princípios SOLID serão essenciais. Também foi desenvolvido o mecanismo de autenticação de usuários.

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

### Implemente o controle de acesso na aplicação, que deverá conter uma página de login. A aplicação também deve restringir o acesso à todas as páginas(exceto a página de login) para os usuários que não estejam previamente autenticados, bem como um botão para o usuário realizar o logout

### Para melhorar o controle das importações realizadas na aplicação, será necessário vincular o usuário que efetuou cada importação. Na funcionalidade de importar transações será necessário registrar o usuário que está realizando a importação, que no caso será o usuário logado disparando tal ação.
