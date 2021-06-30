(ns servico-clojure-pedestal.servidor
  (:use clojure.pprint)
  (:require [io.pedestal.http :as http]
            [servico-clojure-pedestal.database :as database]
            [io.pedestal.test :as test]
            [io.pedestal.interceptor :as i]
            [com.stuartsierra.component :as component]))

(defrecord Servidor [database routes]
  component/Lifecycle

  (start [this]
    (defn assoc-store [context]
      (update context :request assoc :store (:store database)))

    (def db-interceptor
      {:name :db-interceptor
       :enter assoc-store})

    (def service-map-base {::http/routes (:endpoints routes)
                           ::http/port 9999
                           ::http/type :jetty
                           ::http/join? false})

    (def service-map (-> service-map-base
                         (http/default-interceptors)
                         (update ::http/interceptor conj (i/interceptor db-interceptor))))

    (defonce server (atom nil))

    (defn start-server []
      (reset! server (http/start (http/create-server service-map)))
      (prn "Started server http"))

    (defn stop-server []
      (http/stop @server)
      (prn "Stoted server http"))

    (defn restart-server []
      (stop-server)
      (start-server)
      (prn "Restarted server http"))

    (defn test-request [verb url]
      (test/response-for (::http/service-fn @server) verb url))

    (defn start []
      (try (start-server) (catch Exception e (println "Erro ao executar start" (.getMessage e))))
      (try (restart-server) (catch Exception e (println "Erro ao executar restart" (.getMessage e)))))
    (start)
    (assoc this :test-request test-request))

  (stop [this]
        (assoc this :test-request nil)))

(defn new-servidor []
  (map->Servidor {}))
