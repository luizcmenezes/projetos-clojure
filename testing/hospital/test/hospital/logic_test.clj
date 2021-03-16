(ns hospital.logic-test
  (:require [clojure.test :refer :all]
            [hospital.logic :refer :all]))


(deftest cabe-na-fila?-test

  (let
   [hospital-cheio {:espera [1 35 42 64 21]}])

;; Borda do zero
  (testing "Que cabe numa fila vazia"
    (is (cabe-na-fila? {:espera []} :espera)))

  ;; Borda do limite
  (testing "Que não cabe na fila quando a fila está cheia"
    (is (not (cabe-na-fila? {:espera [1 2 3 4 5]} :espera))))

  ;; One off da borda do limite pra cima
  (testing "Que não cabe na fila quando tem mais do que uma fila cheia"
    (is (not (cabe-na-fila? {:espera [1 2 3 4 5]} :espera))))

  ;; Dentro das bordas
  (testing "Que cabe na fila quando está próximo do limite"
    (is (cabe-na-fila? {:espera [1 2 3 4]} :espera))
    (is (cabe-na-fila? {:espera [1 2]} :espera)))

  (testing "Quando o departamento não existe"
    (is (not (cabe-na-fila? {:espera [1 2 3 4]}, :raio-x)))))

(deftest chega-em-test
  (testing "aceita pessoas enquanto cabem pessoas na fila"
    (is (= {:espera [1 2 3 4 5]}
             (chega-em {:espera [1 2 3 4]} :espera 5)))
    (is (= {:espera [1 2 5]}
             (chega-em {:espera [1 2]} :espera 5)))

    ;; (is (= {:hospital {:espera [1 2 3 4 5]} :resultado :sucesso}
    ;;        (chega-em {:espera [1 2 3 4]} :espera 5)))
    ;; (is (= {:hospital {:espera [1 2 5]} :resultado :sucesso}
    ;;        (chega-em {:espera [1 2]} :espera 5)))
    )

  (testing "Não aceita quando a fila está cheia"
    (let
     [hospital-cheio {:espera [1 35 42 64 21]}]

      (is (thrown? clojure.lang.ExceptionInfo "Não cabe ninguém neste departamento"
                     (chega-em hospital-cheio :espera 76)))

      #_(is (thrown? IllegalStateException
                     (chega-em hospital-cheio :espera 76)))
      #_(is (nil? (chega-em hospital-cheio :espera 76)))

      #_(is (try
              (chega-em hospital-cheio :espera 76)
              false
              (catch clojure.lang.ExceptionInfo e
                (= :impossivel-colocar-pessoa-na-fila (:tipo  (ex-data e))))))

      #_(is (= {:hospital hospital-cheio :resultado :impossivel-colocar-pessoa-na-fila}
             (chega-em hospital-cheio :espera 76)))
      
      )))

(deftest transfere-test
  (testing "aceita pessoas se cabe"
    (let [hospital-original {:espera [5] :raio-x []}]
     (is (= {:espera []
             :raio-x [5]}
            (transfere hospital-original :espera :raio-x)))
      )
    (let [hospital-original {:espera [51 5] :raio-x [13]}]
      (is (= {:espera [5]
              :raio-x [13 51]}
             (transfere hospital-original :espera :raio-x))))
    )
  (testing "recusa pessoas se não cabe"
    (let [hospital-cheio {:espera [5] :raio-x [1 2 53 42 13]}]
      (is (thrown? clojure.lang.ExceptionInfo
             (transfere hospital-cheio :espera :raio-x))))
    
    ))