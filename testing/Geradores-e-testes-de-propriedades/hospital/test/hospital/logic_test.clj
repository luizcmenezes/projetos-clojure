(ns hospital.logic-test
  (:require [clojure.test :refer [deftest testing is]]
            [hospital.logic :as lg]
            [hospital.model :as h.model]
            [clojure.test.check.generators :as gen]
            [schema.core :as s]))

(s/set-fn-validation! true)

;; Testes baseados em exemplos;
(deftest cabe-na-fila?-test

  (testing "Que cabe numa fila vazia"
    (is (lg/cabe-na-fila? {:espera []}, :espera)))

  (testing "Que não cabe na fila quando a fila está cheia"

    (is (not (lg/cabe-na-fila? {:espera [1 5 37 54 21]}, :espera))))

  (testing "Que não cabe na fila quando tem mais do que uma fila cheia"
    (is (not (lg/cabe-na-fila? {:espera [1 2 3 4 5 6]}, :espera))))

  (testing "Que cabe na fila quando tem gente mas não está cheia"
    (is (lg/cabe-na-fila? {:espera [1 2 3 4]}, :espera))
    (is (lg/cabe-na-fila? {:espera [1 2]}, :espera)))

  (testing "Que não cabe quando o departamento não existe"
    (is (not (lg/cabe-na-fila? {:espera [1 2 3 4]}, :raio-x)))))

