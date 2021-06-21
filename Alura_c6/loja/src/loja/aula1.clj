(ns loja.aula1)

(defn meu-mapa
  [funcao sequencia]
  (let [primeiro (first sequencia)]
    (funcao primeiro)))

(defn meu-mapa
  [funcao sequencia]
  (let [primeiro (first sequencia)]
    (if (not (nil? primeiro))
      (do (funcao primeiro)
          (meu-mapa funcao (rest sequencia))))))

;(meu-mapa println ["daniela" "guilherme" "carlos" "paulo" "lucia" "ana"])
;(meu-mapa println (range 5000))

(defn meu-mapa
  [funcao sequencia]
  (let [primeiro (first sequencia)]
    (if (not (nil? primeiro))
      (do (funcao primeiro)
          (recur funcao (rest sequencia))))))

(meu-mapa println (range 5000))