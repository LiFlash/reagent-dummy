(ns reagent-dummy.core
    (:require [reagent.core :as reagent :refer [atom]]
              [cljsjs.react :as react]
              [kioo.reagent :as kioo :refer [content do-> listen]])
    (:require-macros [kioo.reagent :refer [defsnippet]]))

;; -------------------------
;; Views

(def state (atom {:stories {:story1 {:key :story1, :val "story1"}
                            :story2 {:key :story2, :val "story2"}}
                  :releases {:release1 {:key :release1, :val "release1"}
                             :release2 {:key :release2, :val "release2"}}
                  :selected []}))

(defn elem-view [elem path]
  (let [local-state (atom {:initialized true})]
    (fn [elem path]
      [:li {:onClick (fn [_]
                       (swap! state update-in [:selected] conj :story1)
                       (reset! local-state {:initialized false}))}
       (get-in @state (conj path :val)) " was initialized: " (str (get @local-state :initialized))])))

(defn filter-stories [stories filtered]
  (if (seq filtered)
    (filterv #(some #{%} filtered) stories)
    stories))

(defn app []
  [:div "reagent"
   [:ul
    (for [elem (filter-stories (keys (:stories @state)) (:selected @state))]
      ^{:key elem} [elem-view elem [:stories elem]])]
   [:ul
    (for [elem (keys (:releases @state))]
      ^{:key elem} [elem-view elem [:releases elem]])]])

(def kioo-state (atom {:stories {:story1 {:key :story1, :val "story1"}
                            :story2 {:key :story2, :val "story2"}}
                  :releases {:release1 {:key :release1, :val "release1"}
                             :release2 {:key :release2, :val "release2"}}
                  :selected []}))

(defsnippet kioo-elem-view* "templates/list.html" [(attr= :template "elem")]
  [local-state elem path]
  {[(attr= :field "val")] (do->
                           (listen :on-click (fn [e]
                                               (swap! kioo-state update-in [:selected] conj :story1)
                                               (reset! local-state {:initialized false})))
                           (content (get-in @kioo-state (conj path :val)) " was initialized: " (str (:initialized @local-state))))})

(defn kioo-elem-view [elem path]
  [kioo-elem-view* (atom {:initialized true}) elem path])

(defsnippet kioo-app "templates/list.html" [(attr= :template "lists")]
  []
  {[(attr= :field "story-list")] (content
                                  (for [elem (filter-stories (keys (:stories @kioo-state)) (:selected @kioo-state))]
                                    [kioo-elem-view elem [:stories elem]])
                                  ;; (map #(kioo-elem-view % [:stories %])
                                  ;;      (filter-stories (keys (:stories @kioo-state)) (:selected @kioo-state)))
                                  )
   [(attr= :field "release-list")] (content
                                    (for [elem (keys (:releases @kioo-state))]
                                      [kioo-elem-view elem [:releases elem]])
                                    ;; (map #(kioo-elem-view % [:releases %])
                                    ;;       (keys (:releases @kioo-state)))
                                    )})

(defn kioo-init []
  [:div "kioo"
   [kioo-app]])
;; -------------------------
;; Initialize app
(defn init! []
  (reagent/render-component [app] (.getElementById js/document "app"))
  (reagent/render-component [kioo-init] (.getElementById js/document "kioo-app")))
