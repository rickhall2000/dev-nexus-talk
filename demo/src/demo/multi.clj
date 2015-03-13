(ns demo.multi
  (:require [demo.protocol :refer [Area]]))

(defmulti get-area
   (fn [shape] (:name shape)))

(defmethod get-area :circle
  [{:keys [radius]}]
  (* radius radius 3.14))

(defmethod get-area :rectangle
  [{:keys [base height ]}]
  (* base height))

(def c1 {:name :circle :radius 3})

(defmethod get-area :default
  [shape]
  "not implemented")

(defrecord Triangle [base height]
  Area
  (get-area [this] (/ (* base height) 2)))
