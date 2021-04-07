(ns financeiro.core)


(def teste "123")
teste

(defn teste [x]
  (+ x 1))

(defn imprimir [x]
  (let [a (teste x)]
    a))

(imprimir 10)