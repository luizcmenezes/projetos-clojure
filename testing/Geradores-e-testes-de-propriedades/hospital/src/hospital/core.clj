(ns hospital.core
  (:require [clojure.test.check.generators :as gen]))

(gen/sample gen/boolean 3)
(gen/sample gen/int)
(gen/sample gen/string)
(gen/sample gen/string-alphanumeric 5)

(gen/sample (gen/vector gen/int 15) 100)
(gen/sample (gen/vector gen/int 1 5) 5)
(gen/sample (gen/vector gen/int) 5)
