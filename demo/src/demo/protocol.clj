(ns demo.protocol)

(defprotocol Area
  (get-area [shape]))

(defrecord Circle [radius]
  Area
  (get-area [this] (* 3.14 radius radius)))

(defrecord Rectangle [base height]
  Area
  (get-area [this] (* base height)))

(extend java.lang.String
  Area {:get-area (fn [this] (.length this))})
