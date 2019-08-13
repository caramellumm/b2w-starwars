# API-StarWars
Aplicação criada para o DesafioB2W, que consistiu em criar uma API para manter planetas e consumir uma API externa para verificar se os planetas cadastrados já tiveram aparições em algum filme da franquina do **StarWars**
<br/>

## Arquitetura
* Api baseada em arquitetura rest, sendo orquestrada através do docker-compose e publicada no tomcat. A base de dados esolhida para o projeto é baseada em banco de dados não relacional. O acesso ao container docker é gerenciado pelo Nginx, onde apenas ele pode ser consumido via http e assim aumentando a segurança da arquitetura, uma vez que nem o servidor nem a base de dados tem o acesso via http.
<br/>

### AWS
* O servidor para a hospedagem de toda arquitetura foi a plataforma de serviços da AWS(Amazon Web Services), pois é uma das plataformas mais utilizadas para serviços na nuvem, o que torna essa plataforma mais do que indicada para hospedar seus serviços.
<br/>

### API
* Api desenvolvida usando rest sendo seu framework o jersey, cujo apresenta uma gama de funções feitas diretamente para a arquitetura rest o que facilita o desenvolvimento e aumenta a produtividade
versão jersey -
<br/>

### Cache
* De acordo com as politicas rest, o ehcache do apache foi utilizado para chachear os dados obtidos através da api swapi.co para evitar de sempre ter que consumir o serviço, e assim melhorando a performance de resposta da API.
versao ehcache -
<br/>

### Proxy
* O Nginx foi utilizado por ser uma ferramenta bem versátil e já estar preparada para fazer o proxy reverso, ou seja, redirecionando a chamada para o servidor devido.
versão Nginx
<br/>

### Servidor da Aplicação
* O servidor escolhido para essa arquitetura é o tomcat, devido a ser leve e não demandar muito da maquina hospedeira em relação a recursos e assim agilizando o processo de deploy e de desenvolvimento.
versão Tomcat
<br/>

### Bando de Dados
* Para essa tarefa o MondoDB foi utilizado como banco de dados não relacional, pois já está bem consolidado e popular. Com isso tem muito mais ferramentas de integração com o java consolidado
<br/>

## Docker
* É uma ferramenta baseada em containers e assim facilitando o trabalho do desenvolvedor ao montar um ambiente produtivo, pois pelo fato dos containers trabalharem com imagens faz com o que se possa isolar o ambiente de trabalho e assim criar qualquer tipo de arquitetura dentro de uma maquina
<br/>

### Docker-Compose
* O docker compose é justamente a orquestração de multiplos containers, onde dentro desse arquivo descritivo é inserido quais as imagens serão usadas, qual rede vão acessar no continer, a ordem de start das imagens dentre outras coisas. Abaixo segue comando usado para o start da api e seus serviços pelo docker-compose

```
$ sudo docker-compose up
```
<br/>

## Swagger
* É uma das ferramentas de mercado mais conhecidas para projetar, criar, documentar e consumir serviços baseados na arquitetura rest, então nada mais justo do que usar essa ótima ferramenta para documentar a api. Link para a documentação:
[Documentacao API-StarWars Swagger](http://ec2-18-228-116-113.sa-east-1.compute.amazonaws.com/dist/index.html)
<br/>

## Testes Unitários
* Teste unitário consiste em testar os fluxos de seu sistema em forma de componentes, para que seja assegurado que a lógica criada está de acordo com o esperado

### Testes
* Para efetuar os testes na api criada, foi utulizado o docker-compose com orquestrador e para esse continer é utilizado o MongoDb e o Maven para executar a rotina de testes. Comando para rodar os testes abaixo:

```
$ sudo docker-compose -f docker-compose-test.yml up
```

### Eclemma
* Para verificar a cobertura de testes tanto em pequenos componentes como no todo da aplicação, foi utilizado o plugin do eclemma, que é instalado no eclipse. E uma vez que os testes são processados por esse plugin, é gerado gráficos com a estatística do que foi ou não testado. Link para visualizar a cobertura:
[Cobertura de Testes](http://ec2-18-228-116-113.sa-east-1.compute.amazonaws.com/cobertura-de-teste/index.html)

Link para o plugin: [EclEmma](https://www.eclemma.org/)


