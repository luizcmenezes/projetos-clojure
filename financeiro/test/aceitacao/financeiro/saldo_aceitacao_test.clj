(ns financeiro.saldo-aceitacao-test
  (:require [midje.sweet :refer [against-background before after fact]]
            [cheshire.core :as json]
            [financeiro.auxiliares :refer [iniciar-servidor parar-servidor endereco-para conteudo porta-padrao]]
            [clj-http.client :as http]))

(against-background [(before :facts (iniciar-servidor porta-padrao))
                     (after :facts (parar-servidor))]
                    
                    (fact "O saldo inicial é 0" :aceitacao
                          (json/parse-string (conteudo "/saldo") true) => {:saldo 0})
                    
                    (fact "O saldo é 10 quando a única transação é uma receita de 10" :aceitacao
                          (http/post (endereco-para "/transacoes")
                                     {:body (json/generate-string {:valor 10 :tipo "receita"})})
                          (json/parse-string (conteudo "/saldo") true) => {:saldo 10})
                    )