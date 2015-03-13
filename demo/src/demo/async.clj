(ns demo.async
  (:require [clojure.core.async :refer :all])
  (:import [java.lang.Thread]))

(def dbl-in (chan))
(def dbl-out (chan))
(def sq-in (chan))
(def sq-out (chan))

(def m {dbl-out "Output channel for doubler"
        sq-out "Output chan for square"})

(def slow-doubler
  (go-loop []
   (let [input (<! dbl-in)]
     (Thread/sleep 2000)
     (>! dbl-out (* 2 input)))
   (recur)))

(def slow-square
  (go-loop []
           (let [delay (+ 1500 (rand-int 1000))
                 input (<! sq-in)]
             (Thread/sleep delay)
             (>! sq-out (* input input)))
           (recur)))

(defn blocking
  [n]
  (>!! dbl-in n)
  (println "Now we wait")
  (println "I got back" (<!! dbl-out)))

(defn non-blocking
  [n]
  (go
   (>! dbl-in n)
   (println "Waiting for result")
   (println "I got back" (<! dbl-out)))
  (println "I can keep doing things while I wait"))

(defn blocking-square
  [n]
  (time
   (do
     (>!! sq-in n)
     (<!! sq-out))))

(defn some-test
  [n]
  (go
    (>! dbl-in n)
    (>! sq-in n)
    (let [[v c] (alts! [dbl-out sq-out])]
      (println "I got value " v "from channel: " (m c)))))



(defn clear-chans
  []
  (alts!! [(timeout 3000) dbl-out sq-out dbl-in sq-in]))
