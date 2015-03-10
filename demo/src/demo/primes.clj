(ns demo.primes)

(defn prime?
  [n]
  (cond (< n 2) false

        :default (reduce (fn [a b] (and a (not (= 0 (mod n b))))) true (range 2 n))))
