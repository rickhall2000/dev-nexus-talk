(ns demo.core
  (:require [clojure.core.async :refer [go chan <! >! put! timeout close!]]))

(def control-channel (chan))

(defn print-greeting
  [counter]
  (if (= 1 (mod counter 2
                ))
        (println "World")
        (println "Hello")))

(def main-loop
  (go
    (loop [counter 0]
      (let [msg (<! control-channel)]
        (print-greeting counter)
        (if (= msg :stop)
          (do
            (println "Enough of that!")
            (close! control-channel))
          (recur (inc counter)))))))

(defn timer
  [interval ctrl]
  (go
    (while (>! ctrl :continue)
      (<! (timeout interval)))
    (println "timer done")))

(defn launch
  []
  (timer 5000 control-channel))
