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

(pprint (s/explain Paciente))
(pprint (s/validate Paciente {:id 15, :nome "guilherme"}))

;; (pprint (s/validate Paciente {:id 15, :name "guilherme"}))

(s/defn novo-paciente :- Paciente
  [id :- s/Num, nome :- s/Str]
  {:id id, :nome nome})

(pprint (novo-paciente 15 "Guilherme"))