(ns hospital.core
  (:use clojure.pprint)
  (:require [clojure.test.check.generators :as gen]
            [schema-generators.generators :as g]
            [hospital.model :as h.model]))

(gen/sample gen/boolean 3)
(gen/sample gen/int)
(gen/sample gen/string)
(gen/sample gen/string-alphanumeric 5)

(gen/sample (gen/vector gen/int 15) 100)
(gen/sample (gen/vector gen/int 1 5) 5)
(gen/sample (gen/vector gen/int) 5)

;; O generators do schema deduz generators a partir do schema
(println (g/sample 10 h.model/PacienteID))
(pprint (g/sample 10 h.model/Departamento))
(pprint (g/sample 10 h.model/Hospital))

(pprint (g/generate h.model/Hospital))
