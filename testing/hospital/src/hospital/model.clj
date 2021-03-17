(ns hospital.model
  (:require [schema.core :as s]))

(def fila-vazia clojure.lang.PersistentQueue/EMPTY)

(defn novo-hospital []
  {:espera       fila-vazia
   :laboratorio1 fila-vazia
   :laboratorio2 fila-vazia
   :laboratorio3 fila-vazia})

(s/def PacienteID s/Str)
(s/def Departamento [PacienteID])
(s/def Hospital {s/Keyword Departamento})

;; (s/validate PacienteID "Guilherme")
;; (s/validate PacienteID 15)
;; (s/validate Departamento ["Guilherme" "Daniela"])
;; (s/validate Hospital {:espera ["Guilherme" "Daniela"]})