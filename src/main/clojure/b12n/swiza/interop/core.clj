(ns b12n.swiza.interop.core
  (:require
   [clojure.pprint :refer [pprint print-table]]
   [clojure.reflect :refer [reflect]]))

;; Inspired by:  https://gist.github.com/Sh4pe/eea52891dbeca5d0614d
(defn java-methods
  [object]
  (let [result (->> (reflect object)
                    :members
                    (filter :return-type)
                    (sort-by :name)
                    (map #(select-keys % [:flags :return-type :name :parameter-types])))]
    result))

(defn jmethods
  [object & [{:keys [show-public?
                     short-format?
                     show-getter?
                     show-setter?
                     show-private?]
              :or {show-public? true
                   short-format? true
                   show-getter? false
                   show-setter? false
                   show-private? false}}]]
  (let [result (java-methods object)]
    (as-> result $
      ;; include public methods if one specified
      (if show-public? (filter #(contains? (:flags %) :public) $) $)

      ;; include private methods if one specified
      (if show-private? (filter #(contains? (:flags %) :private) $) $)

      ;; include getter methods if one specified
      (if show-getter? (filter #(clojure.string/starts-with? (:name %) "get") $) $)

      ;; include setter methods if one specified
      (if show-setter? (filter #(clojure.string/starts-with? (:name %) "set") $) $)

      ;; Finally print out the signature of the class
      (if short-format?
        ;; print the function name once when it takes more than one way
        (-> (map #(:name %) $) set seq)
        (map #(format "%s %s %s(%s)"
                      (clojure.string/join " " (map name (:flags %)))
                      (:return-type %)
                      (:name %)
                      (clojure.string/join " " (map str (:parameter-types %)))) $)))))

(defn print-methods
  "Print the method of a given Java class.

  Examples:
  (show-methods java.util.UUID) ;; see your REPL
  (show-methods java.lang.String)"
  [clazz]
  (let [declared-methods (seq (:declaredMethods (bean clazz)))
        methods (map #(.toString %) declared-methods)]
    (doseq [m methods]
      (println m))
    methods))
