(ns hospital.core)

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))


(defn mais-caro-que-100?
  [valor-bruto]
  (> valor-bruto 100))

(defn valor-descontado
  "Retorna o valor com desconto de 10% se deve aplicar desconto."
  [aplica? valor-bruto]
  (if (aplica? valor-bruto)
    (let [taxa-de-desconto (/ 10 100)
          desconto (* valor-bruto taxa-de-desconto)]
      (- valor-bruto desconto))
    valor-bruto))

(println "Função com parâmetro")
(println (valor-descontado mais-caro-que-100? 1000))
(println (valor-descontado mais-caro-que-100? 100))

(println (valor-descontado (fn [valor-bruto] (> valor-bruto 100)) 1000))
(println (valor-descontado (fn [valor-bruto] (> valor-bruto 100)) 100))
(println (valor-descontado  #(> % 100) 100))


(def precos [30 700 1000])

(print (precos 0))
(print (get precos 2))
(print (get precos 17))
(println (get precos 1 "Não encontrado"))

(println (conj precos 5))

(update precos 0 inc)

(defn aplica-desconto?
  [valor-bruto]
  (> valor-bruto 100))


(defn valor-descontado
  [valor-bruto]
  (if (aplica-desconto? valor-bruto)
    (let [taxa-de-desconto (/ 10 100)
          desconto (* valor-bruto taxa-de-desconto)]
      (- valor-bruto desconto))
    valor-bruto))

(println (map valor-descontado precos))

(println (range 10))
(println (filter even? (range 10)))

(filter aplica-desconto? precos)