# reactive-spring-webflux

#### Resumo

Curso Spring Webflux Reactive Pragmatic Code School Udemy.
O projeto consiste de 3 módulos, explorando a biblioteca reativa do Spring Web Flux, sendo eles:
 - *movies-info-service*: módulo responsável pela criação de um filme usando webflux e endpoints nao bloquantes
 - *movies-review-service*: módulo com objetivo de criar um comentário e uma nota para um dado filme implementado com programacao web funcional
 - *movies-service*: módulo que busca informações de um filme nos 2 módulos anteriores de forma reativa e nao bloqueante

Para executar os módulos *movies-info-service* e *movies-review-service* é necessário estar com um banco mongodb rodando.


#### Usar o Mongo DB com o Docker

- Execute um container docker com o **MongoDB**.
```
docker run -d -p 27017:27017 --name pcs-mongodb mongo:latest
```

#### Install Mongo DB in MAC

- Or you can run the below command to install the **MongoDB**.
```
brew services stop mongodb
brew uninstall mongodb

brew tap mongodb/brew
brew install mongodb-community
```

- How to restart MongoDB in your local machine.
```
brew services restart mongodb-community
```

#### Install Mongo DB in Windows

- Follow the steps in the below link to install Mongo db in Windows.
https://docs.mongodb.com/manual/tutorial/install-mongodb-on-windows/


#### Repositorio do curso

https://github.com/dilipsundarraj1/reactive-spring-webflux
