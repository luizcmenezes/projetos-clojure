(ns hospital.core-test)


(def nome "hello world")

(defn count-element [n]
  (reduce (fn [x y]
            (inc x)) 0 n))

(count-element "Hello world")

(defn reverse-seq [n]
  (reduce (fn [x y]
            (conj x y)) '() n))

(reverse-seq [1 2 3 4])

(defn sum-all [n]
  (reduce + n))

(= (sum-all '(1 10 3)) 14)

(defn impares [n]
  (filter odd? n))

(impares #{1 2 3 4 5})