(ns demo.multi)

(defmulti get-area
   (fn [shape] (:name shape)))
