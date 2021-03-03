(ns hospital.logic-test
  (:require [clojure.test :refer :all]
            [hospital.logic :refer :all]))

(deftest cabe-na-fila?-test
  (testing "Que cabe na fila"
    (is (cabe-na-fila? {:espera []} :espera)))
  
  (testing "Que não cabe na fila quando a fila está cheia"
    (is (not (cabe-na-fila? {:espera [1 2 3 4 5]} :espera))))
  
  (testing "Que não cabe na fila quando tem mais do que uma fila cheia"
    (is (not (cabe-na-fila? {:espera [1 2 3 4 5]} :espera))))
  
  (testing "Que cabe na fila quando está próximo do limite"
    (is (cabe-na-fila? {:espera [1 2 3 4]} :espera))))