(defproject net.b12n/swiza-interop "0.1.1"
  :description "swiza-interop utility library"
  :url "http://github.com/agilecreativity/swiza-interop"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :plugins [[lein-cljfmt "0.6.1"]
            [jonase/eastwood "0.3.5"]
            [lein-auto "0.1.3"]
            [lein-cloverage "1.0.13"]
            [lein-bump-version "0.1.6"]
            [alembic "0.3.2"]]
  :source-paths ["src/main/clojure"]
  :java-source-paths ["src/main/java"]
  :test-paths ["src/test/clojure" "src/test/java"]
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [net.b12n/swiza-commons "0.1.2"]
                 [akvo/fs "20180904-152732.6dad3934"]]
  :profiles {:1.10 {:dependencies [[org.clojure/clojure "1.10.1"]]}
             :1.9  {:dependencies [[org.clojure/clojure "1.9.0"]]}
             :uberjar {:aot :all}
             :dev {:dependencies [[org.clojure/tools.namespace "0.2.11"]
                                  [circleci/circleci.test "0.4.2"]
                                  [clj-jgit "0.8.10"]
                                  [alembic "0.3.2"]]
                   :global-vars {*warn-on-reflection* true
                                 *assert* true}}}
  :aliases {"lint" ["do" ["cljfmt" "check"] ["eastwood"]]
            "test-all" ["with-profile" "default:+1.9:+1.10" "test"]
            "lint-and-test-all" ["do" ["lint"] ["test-all"]]
            "test" ["run" "-m" "circleci.test/dir" :project/test-paths]
            "tests" ["run" "-m" "circleci.test"]
            "retest" ["run" "-m" "circleci.test.retest"]})
