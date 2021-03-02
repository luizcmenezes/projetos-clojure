(ns financeiro.db-test
  (:require [midje.sweet :refer :all]
            [financeiro.db :refer :all]))

(defn limpar []
  (reset! registros []))

(facts "Guarda uma transação num átomo"
       (against-background [(before :facts (limpar))]
                           (fact "a coleção de transações inicia vazia"
                                 (count (transacoes)) => 0)
                           (fact "a transação é o primeiro registro"
                                 (registrar {:valor 7 :tipo "receita"}) => {:id 1 :valor 7 :tipo "receita"}
                                 (count (transacoes)) => 1)))
