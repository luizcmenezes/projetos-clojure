(ns hospital.aula2
  (:use clojure.pprint)
  (:require [schema.core :as s]))

(s/set-fn-validation! true)

;; (s/defrecord Paciente
;;              [id :- Long, nome :- s/Str])

;; (pprint (Paciente. 15 "Guilherme"))
;; (pprint (Paciente. "15" "Guilherme"))

(def Paciente
  "Schema de um paciente"
  {:id s/Num, :nome s/Str})

;; (pprint (s/explain Paciente))
;; (pprint (s/validate Paciente {:id 15, :nome "guilherme"}))

;; (pprint (s/validate Paciente {:id 15, :name "guilherme"}))

(s/defn novo-paciente :- Paciente
  [id :- s/Num, nome :- s/Str]
  {:id id, :nome nome})

;; (pprint (novo-paciente 15 "Guilherme"))

;; Função pura, simples, altamente testavel
(defn estritamente-positivo? [x]
  (> x 0))

(def EstritamentePositivo (s/pred estritamente-positivo? 'estritamente-positivo))

;; (pprint (s/validate EstritamentePositivo 15))

(def Paciente 
  "Schema de um paciente"
  {:id (s/constrained s/Int pos?), :nome s/Str})

;; (pprint (s/validate Paciente {:id 1, :nome "guilherme"}))

;; Caminho díficil entendimento
;; (def Paciente
;;   "Schema de um paciente"
;;   {:id (s/constrained s/Int #(> % 0) 'inteiro-estritamente-positivo), :nome s/Str})

;; (pprint (s/validate Paciente {:id 1, :nome "guilherme"}))