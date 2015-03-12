(ns reagent-dummy.core-test
  (:require [cemerick.cljs.test :refer-macros [is are deftest testing use-fixtures done]]
;            [reagent.core :as reagent :refer [atom]]
 ;           [reagent-dummy.core :as rc]
            ))


;; (def isClient (not (nil? (try (.-document js/window)
;;                               (catch js/Object e nil)))))

;; (def rflush reagent/flush)

;; (defn add-test-div [name]
;;   (let [doc     js/document
;;         body    (.-body js/document)
;;         div     (.createElement doc "div")]
;;     (.appendChild body div)
;;     div))

;; (defn with-mounted-component [comp f]
;;   (when isClient
;;     (let [div (add-test-div "_testreagent")]
;;       (let [comp (reagent/render-component comp div #(f comp div))]
;;         (reagent/unmount-component-at-node div)
;;         (reagent/flush)
;;         (.removeChild (.-body js/document) div)))))


;; (defn found-in [re div]
;;   (let [res (.-innerHTML div)]
;;     (if (re-find re res)
;;       true
;;       (do (println "Not found: " res)
;;           false))))


(deftest dummy-test
  (is (= 1 2)))
;; (deftest test-home
;;   (with-mounted-component (rc/home-page)
;;     (fn [c div]
;;       (is (found-in #"Welcome to" div)))))
