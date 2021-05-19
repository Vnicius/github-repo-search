# Github Repo APP

Exemplo de App Android utilizando a API de busca de Repositórios do Github.

## Referências

## Design

![Design das telas](./images/screens.png)

<div style="text-align:center">
<a href="https://www.figma.com/file/NnPw1t8RDT3MtcbouWoMQ3/github-repo-search?node-id=0%3A1">Projeto Figma</a>
</div>

### Splash Screen

Tela de Splash (_launch screen_) com a logo do app. Não só funciona como uma tela de apresentão inicial, mas também pode ser utilizada para o carregamento inicial das informações.

### Search Screen

<div align="center">
<img width="200px" src="./images/splashscreen.png" alt="splash screen" />
</div>

Tela para realizar a busca dos repositórios. Essa tela possui 4 estados: vazia,
busca sem resultado, erro na busca e busca com resultados.

**Vazia**

<div align="center">
<img width="200px" src="./images/emptysearch.png" alt="splash screen" />
</div>

Esse estado apenas contém uma mensagem dizendo que a lista está vazia.

**Sem resultados**

<div align="center">
<img width="200px" src="./images/noresults.png" alt="splash screen" />
</div>

Esse estado apresenta uma mensagem dizendo que a última busca não retornou nenhum
resultado.

**Erro na busca**

<div align="center">
<img width="200px" src="./images/searcherror.png" alt="splash screen" />
</div>

Esse estado apresenta uma mensagem mostando que ocorreu algum erro durante a busca
(erro da API, erro por falta de conexão com a internet).

**Resultado da busca**

<div align="center">
<img width="200px" src="./images/searchresult.png" alt="splash screen" />
</div>

Esse estado apresenta uma lista com os resultados da busca realizada.

Cada item da lista apresenta: imagem do dono do repositório; nome do dono;
nome do repositório; descrição do repositório; quantidade de estrelas; principal
linguagem de programação do repositório.

### Details Screen

<div align="center">
<img width="200px" src="./images/details.png" alt="splash screen" />
</div>

Essa tela apresenta os detalhes do repositório selecionado. Nela é apresentada:
imagem do dono do repositório; nome do dono; e nome do repositório.

### About Screen

<div align="center">
<img width="200px" src="./images/aboutscreen.png" alt="splash screen" />
</div>

Tela com iformações básicas do app. Nessa versão é apenas utilizada para apresentar
a navegação.

## Arquiteturas

### RepoSearch

![Arquitetura da tela de busca](./images/reposearch-arch.png)

### RepoDetails

![Arquitetura da tela de detalhes](./images/repodetails-arch.png)
