(ns loja.aula2)

(defn conta
  [total-ate-agora elementos]
  (recur (inc total-ate-agora) (rest elementos)))

;(println (conta 0 ["daniela" "guilherme" "carlos" "paulo" "lucia" "ana"]))

(defn conta
  [total-ate-agora elementos]
  (if (next elementos)
    (recur (inc total-ate-agora) (next elementos))))

(println (conta 0 ["daniela" "guilherme" "carlos" "paulo" "lucia" "ana"]))