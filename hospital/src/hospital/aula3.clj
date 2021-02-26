(ns hospital.aula3
  (:use clojure.pprint)
  (:require [schema.core :as s]))

(s/set-fn-validation! true)

(def PosInt (s/pred pos-int? 'inteiro-positivo))

(def Paciente
  {:id PosInt, :nome s/Str})

(s/defn novo-paciente :- Paciente
  [id :- PosInt, nome :- s/Str]
  {:id id, :nome nome})

;; (pprint (novo-paciente 15, "Guilherme"))
;; (pprint (novo-paciente -15, "Guilherme"))

(defn maior-ou-igual-a-zero [x] (>= x 0))
(def ValorFinanceiro (s/constrained s/Num maior-ou-igual-a-zero))

(def Pedido
  {:paciente Paciente
   :valor ValorFinanceiro
   :procedimento s/Keyword})

(s/defn novo-pedido :- Pedido
  [paciente :- Paciente
   valor :- ValorFinanceiro
   procedimento :- s/Keyword]
  {:paciente paciente, :valor valor, :procedimento procedimento})

(pprint (novo-pedido (novo-paciente 15, "Guilherme"), 15.53, :raio-x))
;; (pprint (novo-pedido (novo-paciente -15, "Guilherme"), 15.53, :raio-x))