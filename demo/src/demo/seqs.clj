(ns demo.seqs
  (:require [demo.primes :refer [prime?]]))


;; collection types

(defn square-primes [col]
   (map (fn [x] (* x x)) (filter prime? col)))

(defn summing-squares [col]
  (->> col
       (filter prime?)
       (map #(* % %))
       (reduce +)))

;; collections are immutable


;; many collections are sequences


;; collections are functions
