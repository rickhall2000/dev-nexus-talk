(ns demo.async
  (:require [clojure.core.async :refer :all])
  (:import [java.lang.Thread]))

(def input-chan (chan))
(def output-chan (chan))
(def input-chan' (chan))
(def output-chan' (chan))

(defonce slow-doubler
  (go-loop []
   (let [input (<! input-chan)]
     (Thread/sleep 2000)
     (>! output-chan (* 2 input)))
   (recur)))

(defonce slow-square
  (go-loop []
           (let [delay (+ 1500 (rand-int 1000))
                 input (<! input-chan')]
             (Thread/sleep delay)
             (>! output-chan' (* input input)))
           (recur)))

(defn blocking
  [n]
  (>!! input-chan n)
  (println "Now we wait")
  (println "I got back" (<!! output-chan)))

(defn non-blocking
  [n]
  (go
   (>! input-chan n)
   (println "Waiting for result")
   (println "I got back" (<! output-chan)))
  (println "I can keep doing things while I wait"))

(defn blocking-square
  [n]
  (time
   (do
     (>!! input-chan' n)
     (<!! output-chan'))))
