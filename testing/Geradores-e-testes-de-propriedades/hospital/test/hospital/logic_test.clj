(ns hospital.logic-test
  (:require [clojure.test :refer [deftest testing is]]
            [hospital.logic :as lg]
            [hospital.model :as h.model]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.clojure-test :refer (defspec)]
            [clojure.test.check.properties :as prop]
            [schema.core :as s]))

(s/set-fn-validation! true)

;; Testes baseados em exemplos;
(deftest cabe-na-fila?-test

  (testing "Que cabe numa fila vazia"
    (is (lg/cabe-na-fila? {:espera []}, :espera)))

  (testing "Que cabe pessoas em filas de tamanho até 4 inclusive"
    (doseq [fila (gen/sample (gen/vector gen/string-alphanumeric 0 4) 100)]
      (is (lg/cabe-na-fila? {:espera fila} :espera))))

  (testing "Que não cabe na fila quando a fila está cheia"

    (is (not (lg/cabe-na-fila? {:espera [1 5 37 54 21]}, :espera))))

  (testing "Que não cabe na fila quando tem mais do que uma fila cheia"
    (is (not (lg/cabe-na-fila? {:espera [1 2 3 4 5 6]}, :espera))))

  (testing "Que cabe na fila quando tem gente mas não está cheia"
    (is (lg/cabe-na-fila? {:espera [1 2 3 4]}, :espera))
    (is (lg/cabe-na-fila? {:espera [1 2]}, :espera)))

  (testing "Que não cabe quando o departamento não existe"
    (is (not (lg/cabe-na-fila? {:espera [1 2 3 4]}, :raio-x)))))

;; (deftest chega-em-test
;;   (testing "Que é colocada uma pessoa em filas menores que 5"
;;     (doseq [fila (gen/sample (gen/vector gen/string-alphanumeric 0 4) 10)
;;             pessoa (gen/sample gen/string-alphanumeric 5 )]
;;       (println pessoa fila)
;;       ;; só para mostrar que são 50 asserts (10 * 5)
;;       )
;;     ))

(defspec explorando-a-api 10
  (prop/for-all
   [fila (gen/vector gen/string-alphanumeric 0 4)
    pessoa gen/string-alphanumeric]
   (println pessoa fila)
   true
   ))
