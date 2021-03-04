(ns hospital.logic)

(defn cabe-na-fila?
  [hospital depatarmento]
  (some-> hospital
          depatarmento
          count
          (< 5)))