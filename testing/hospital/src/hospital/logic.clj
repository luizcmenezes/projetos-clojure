(ns hospital.logic)

(defn cabe-na-fila? 
  [hospital depatarmento]
  (-> hospital
      depatarmento
      count
      (< 5)))