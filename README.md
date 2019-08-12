# b2w-starwars
Aplicação para avaliação

## Arquitetura
Api baseada em arquitetura rest, sendo orquestrada através do docker-compose e publicada no tomcat. A base de dados esolhida para o projeto é baseada em banco de dados não relacional. O acesso ao container docker é gerenciado pelo Nginx, onde apenas ele pode ser consumido via http e assim aumentando a segurança da arquitetura, uma vez que nem o servidor nem a base de dados tem o acesso via http.

### API
Api desenvolvida usando rest sendo seu framework o jersey, cujo apresenta uma gama de funções feitas diretamente para a arquitetura rest o que facilita o desenvolvimento e aumenta a produtividade
versão jersey -


#### Cache
De acordo com as politicas rest, o ehcache do apache foi utilizado para chachear os dados obtidos através da api swapi.co para evitar de sempre ter que consumir o serviço, e assim melhorando a performance de resposta da API.
versao ehcache -


### Proxy
O Nginx foi utilizado por ser uma ferramenta bem versátil e já estar preparada para fazer o proxy reverso, ou seja, redirecionando a chamada para o servidor devido.
versão Nginx

### Servidor
O servidor escolhido para essa arquitetura é o tomcat, devido a ser leve e não demandar muito da maquina hospedeira em relação a recursos e assim agilizando o processo de deploy e de desenvolvimento.
versão Tomcat


### Bando de Dados
Para essa tarefa o MondoDB foi utilizado como banco de dados não relacional, pois já está bem consolidado e popular. Com isso tem muito mais ferramentas de integração com o java consolidado







