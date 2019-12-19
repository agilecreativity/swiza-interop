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
  (print-methods java.util.UUID) ;; see your REPL
  (print-methods java.lang.String)"
  [clazz]
  (let [declared-methods (seq (:declaredMethods (bean clazz)))
        methods (map #(.toString %) declared-methods)]
    (doseq [m methods]
      (println m))
    methods))

(comment
  (print-methods java.lang.String)
  (def result (print-methods java.util.Arrays))
  (first result)
  )

(defn declared-methods
  "List out the declared methods of a given class

  Examples:
  (declared-methods java.util.UUID)
  (declared-methods java.lang.String)"
  [clazz]
  (-> clazz
      bean
      :declaredMethods
      seq))

(defn declared-names
  "Extract the unique function names from a given class.

  Example:
  (declared-names java.util.UUID)"
  [clazz]
  (if-let [methods (declared-methods clazz)]
    (->> methods
         (map (fn [m] (.getName m)))
         distinct)))

(comment
  (declared-names java.util.UUID)

  (declared-names java.util.List)
  )

(comment
  ;; from: borkdude/babashka
  (defn public-declared-method? [c m]
    (and (= c (.getDeclaringClass m))
         (not (.getAnnotation m Deprecated))))

  (defn public-declared-method-names [c]
    (->> (.getMethods c)
         (keep (fn [m]
                 (when (public-declared-method? c m)
                   {:name (.getName m)})) )
         (distinct)
         (sort-by :name)
         (vec)))

  (public-declared-method-names java.lang.UNIXProcess)
  )
