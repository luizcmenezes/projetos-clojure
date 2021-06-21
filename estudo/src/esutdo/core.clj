(ns esutdo.core)

;(defn multiplo-de-3? [dividendo]
;  (= 0 (mod dividendo 3)))
;
;
;(println "Resultado 1:" (multiplo-de-3? 9))
;
;(println "resultado 2:" (multiplo-de-3? 20))
;
;(defn par? [n]
;  (if (even? n)
;    "Sim"
;    "Não"))
;
;(par? 20)
;
;(defn saldo [valor]
;  (cond (< valor 0) "Negativo"
;        (> valor 0) "Positivo"
;        :else "Zero"))
;
;(saldo 1)

;(defn fizz-buzz [n]
;  (cond (= 0 (mod n 3)) "Fizz"
;        (= 0 (mod n 5)) "Buzz"
;        ((= 0 (mod n 3)) (= 0 (mod n 5))) "FizzBuzz"
;        :else n))

(defn divisivel-por? [dividendo divisor]
  (zero? (mod dividendo divisor)))

(defn fizzbuzz

  [numero]
  (cond (and (divisivel-por? numero 3)
             (divisivel-por? numero 5)) "FizzBuzz"
        (divisivel-por? numero 3) "Fizz"
        (divisivel-por? numero 5) "Buzz"
        :else numero))

;(def de-um-ate-5 '(1 2 3 4 5))
;(def de-um-ate-15 (range 1 16))
(def numeros-vetorizados [1 2 3 4 5])
;
(map fizzbuzz numeros-vetorizados)
;
;(def cantor-arretado (vector "Chico Cézar" "Catolé do Rocha" 26 "janeiro" 1964))
;
;(get cantor-arretado 0)
;(get cantor-arretado 4)
;(last cantor-arretado)
;(conj cantor-arretado "MPB")
;(println cantor-arretado)
;cantor-arretado
;
;(def cantora-arretada (list "Renata Arruda" "João Pessoa" 23 "dezembro" 1967))

;(nth cantora-arretada 0)
;(nth cantora-arretada 4)
;(last cantora-arretada)
;(first cantora-arretada)
;(conj cantora-arretada "MPB")
;(hash-set "Chico Cesar" "Renata Arruda")
;{"Chico César" "Renata Arruda"}
;(def artistas #{"Chico César" "Renata Arruda"})
;(conj artistas "Jackson do Pandeiro")
;(conj artistas "Chico César")
;(hash-map :valor 200, :tipo "receita")
(def transacao {:valor 200, :tipo "receita"})
(reduce + [1 2 3])
(assoc transacao :categoria "Educação")
(get transacao :valor)

(def transacao-desnecessaria {:valor   34
                              :tipo    "despesa"
                              :rotulos '("desnecessária"
                                          "cartao")})

(:rotulos transacao-desnecessaria)

(:rotulos transacao)

(defn resumo [transacao]
  (select-keys transacao [:valor :tipo :data]))

(def transacoes
  [{:valor 33.0 :tipo "despesa" :comentario "Almoço" :data "19/11/2016"}
   {:valor 2700.0 :tipo "receita" :comentario "Bico" :data "01/12/2016"}
   {:valor 29.0 :tipo "despesa" :comentario "Livro de clojure" :data "03/12/2016"}])

(map resumo transacoes)

(defn despesa? [transacao]
  (= (:tipo transacao) "despesa"))

(filter despesa? transacoes)

(defn so-valor
  "Funçãoq ue pega só o valor de uma transação"
  [transacao]
  (:valor transacao))

(map so-valor (filter despesa? transacoes))
(reduce + (map so-valor (filter despesa? transacoes)))

(defn valor-grande?
  "Função que retorna o maior valor da transação"
  [transacao]
  (> (:valor transacao) 100))

(filter valor-grande? transacoes)

(def transacoes
  [{:valor 33.0M :tipo "despesa" :comentario "Almoço" :moeda "R$" :data "19/11/2016"}
   {:valor 2700.0M :tipo "receita" :comentario "Bico" :moeda "R$" :data "01/12/2016"}
   {:valor 29.0M :tipo "despesa" :comentario "Livro de clojure" :moeda "R$" :data "03/12/2016"}])

(defn valor-sinalizado [transacao]
  (let [moeda (:moeda transacao "R$")
        valor (:valor transacao)]
    (if (= (:tipo transacao) "despesa")
      (str moeda " -" valor)
      (str moeda " +" valor))))

(valor-sinalizado (first transacoes))
(valor-sinalizado (second transacoes))

(def transacao-aleatoria {:valor 9.0})
(valor-sinalizado transacao-aleatoria)

(defn data-valor [transacao]
  (str (:data transacao) " => " (valor-sinalizado transacao)))

(data-valor (first transacoes))
;(map data-valor transacoes)

(defn transacao-em-yuan [transacao]
  (assoc transacao :valor (* 2.15 (:valor transacao))
                   :moeda "¥"))

(transacao-em-yuan (first transacoes))

(def cotacoes
  {:yuan {:cotacao 2.15M :simbolo "¥"}
   :euro {:cotacao 0.28M :simbolo "€"}})

(defn transacao-em-yuan [transacao]
  (assoc transacao :valor (* (:cotacao (:yuan cotacoes))
                             (:valor transacao))
                   :moeda (:simbolo (:yuan cotacoes))))

(transacao-em-yuan (first transacoes))

(defn transacao-em-yuan [transacao]
  (assoc transacao :valor (* (get-in cotacoes [:yuan :cotacao])
                             (:valor transacao))
                   :moeda (get-in cotacoes [:yuan :simbolo])))

(transacao-em-yuan (first transacoes))

(defn transacao-em-yuan [transacao]
  (let [yuan (:yuan cotacoes)]
    (assoc transacao :valor (* (:cotacao yuan) (:valor transacao))
                     :moeda (:simbolo yuan))))

(defn transacao-em-yuan
  "Destructuring"
  [transacao]
  (let [{yuan :yuan} cotacoes]
    (assoc transacao :valor (* (:cotacao yuan) (:valor transacao))
                     :moeda (:simbolo yuan))))

(transacao-em-yuan (first transacoes))

(data-valor (first transacoes))
(data-valor (transacao-em-yuan (first transacoes)))

(defn texto-resumo-em-yuan [transacao]
  (data-valor (transacao-em-yuan transacao)))

(map texto-resumo-em-yuan transacoes)

(defn texto-resumo-em-yuan [transacao]
  (-> (transacao-em-yuan transacao)
      (data-valor)))

(map texto-resumo-em-yuan transacoes)

(defn transacao-em-outra-moeda [moeda transacao]
  (let [{{cotacao :cotacao simbolo :simbolo} moeda} cotacoes]
    (assoc transacao :valor (* cotacao (:valor transacao))
                     :moeda simbolo)))

(transacao-em-outra-moeda :euro (first transacoes))
(transacao-em-outra-moeda :euro (last transacoes))
(transacao-em-outra-moeda :yuan (first transacoes))
(transacao-em-outra-moeda :yuan (last transacoes))

(def transacao-em-euro (partial transacao-em-outra-moeda :euro))
(def transacao-em-yuan (partial transacao-em-outra-moeda :yuan))

(transacao-em-euro (first transacoes))
(transacao-em-yuan (first transacoes))

(map transacao-em-yuan transacoes)
(map transacao-em-euro transacoes)

(def membros-fundadores
  (list "Argentina" "Brasil" "Paraguai" "Uruguai"))

membros-fundadores

(def membros-plenos
  (cons "Venezuela" membros-fundadores))

membros-plenos

(rest membros-plenos)

(identical? (rest membros-plenos) membros-fundadores)