(ns hospital.logic)

(defn cabe-na-fila?
  [hospital depatarmento]
  (some-> hospital
          depatarmento
          count
          (< 5)))

;; (defn chega-em
;;   [hospital departamento pessoa]
;;   (if (cabe-na-fila? hospital departamento)
;;     (update hospital departamento conj pessoa)
;;     (throw (ex-info "Não cabe ninguém neste departamento" {:paciente pessoa}))))

;; (defn chega-em
;;   [hospital departamento pessoa]
;;   (if (cabe-na-fila? hospital departamento)
;;     (update hospital departamento conj pessoa)))

;; (defn chega-em
;;   [hospital departamento pessoa]
;;   (if (cabe-na-fila? hospital departamento)
;;     (update hospital departamento conj pessoa)
;;     (throw (ex-info 
;;             "Não cabe ninguém neste departamento" {:paciente pessoa 
;;                                                            :tipo :impossivel-colocar-pessoa-na-fila}))))

;; (defn tenta-colocar-na-fila
;;   [hospital departamento pessoa]
;;   (if (cabe-na-fila? hospital departamento)
;;     (update hospital departamento conj pessoa)))

;; (defn chega-em
;;   [hospital departamento pessoa]
;;   (if-let [novo-hospital (tenta-colocar-na-fila hospital departamento pessoa)]
;;     {:hospital novo-hospital :resultado :sucesso}
;;     {:hospital hospital :resultado :impossivel-colocar-pessoa-na-fila}))

(defn chega-em
  [hospital departamento pessoa]
  (if (cabe-na-fila? hospital departamento)
    (update hospital departamento conj pessoa)
    (throw (ex-info "Não cabe ninguém neste departamento" {:paciente pessoa}))))


(defn atende
  [hospital departamento]
  (update hospital departamento pop))

(defn proxima
  "Retorna o próximo paciente da fila"
  [hospital departamento]
  (-> hospital
      departamento
      peek))

(defn transfere
  "Transfere o próximo paciente da fila de para a fila para"
  [hospital de para]
  (let [pessoa (proxima hospital de)]
    (-> hospital
        (atende de )
        (chega-em para pessoa))))