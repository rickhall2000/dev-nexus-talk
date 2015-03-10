(ns demo.xduce
  (:require [demo.primes :refer [prime?]]
            [clojure.core.async :as a]))


(defn fill-chan
  [output]
  (a/go
   (doseq [i (range 10)]
     (a/>! output i))
   (a/close! output)))

(def square (map (fn [x] (* x x))))
(def primes (filter prime?))

(def square-filter (comp primes square))

(def squares
  (sequence square-filter (range 10)))

(def sum-squares
  (transduce square-filter + (range 10)))

(def squares-chan
  (let [result (a/chan 1 square-filter)]
    (fill-chan result)
    (a/into [] result)))

(def sum-squares-chan
  (let [result (a/chan 1 square-filter)]
    (fill-chan result)
    (a/reduce + 0 result)))
