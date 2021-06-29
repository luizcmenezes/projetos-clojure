(ns servico-clojure-pedestal.servidor
  (:use clojure.pprint)
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]
            [io.pedestal.test :as test]
            [servico-clojure-pedestal.database :as database]))

(defn assoc-store [context]
  (update context :request assoc :store database/store))

(def db-interceptor
  {:name :db-interceptor
   :enter assoc-store})

(defn listar-tarefa [request]
  {:headers {"Content-Type" "application/json"}
   :status 200
   :body @(:store request)})

(defn criar-tarefa-mapa [uuid nome status]
  {:uuid uuid :nome nome :status status})

(defn criar-tarefa [request]
  (let [uuid (java.util.UUID/randomUUID)
        nome (get-in request [:query-params :nome])
        status (get-in request [:query-params :status])
        tarefa (criar-tarefa-mapa uuid nome status)
        store (:store request)]
    (swap! store assoc uuid tarefa)
    {:headers {"Content-Type" "application/json"}
     :status 200
     :body {:mensagem "Tarefa registrada com sucesso!"
            :tarefa tarefa}}))

(defn funcao-hello [request]
  {:headers {"Content-Type" "application/json"}
   :status 200
   :body (str "Bem vindo " (get-in request [:query-params :name] "DEV"))})

(defn deletar-tarefa [request]
  (let [store (:store request)
        tarefa-id (get-in request [:path-params :id])
        tarefa-uuid (java.util.UUID/fromString tarefa-id)]
    (swap! store dissoc tarefa-uuid)
    {:status 200
     :body {:mensasagem "Removida com sucesso!"}}))

(defn atualizar-tarefa [request]
  (let [tarefa-id (get-in request [:path-params :id])
        tarefa-uuid (java.util.UUID/fromString tarefa-id)
        nome (get-in request [:query-params :nome])
        status (get-in request [:query-params :status])
        tarefa (criar-tarefa-mapa tarefa-uuid nome status)
        store (:store request)]
    (swap! store assoc tarefa-uuid tarefa)
    {:headers {"Content-Type" "application/json"}
     :status 200
     :body {:mensagem "Tarefa atualizada com sucesso!"
            :tarefa tarefa}}))

(def routes (route/-expand-routes
             #{["/ola" :get funcao-hello :route-name :hello-world]
               ["/tarefa" :post [db-interceptor criar-tarefa] :route-name :criar-tarefa]
               ["/tarefa" :get [db-interceptor listar-tarefa] :route-name :listar-tarefa]
               ["/tarefa/:id" :delete [db-interceptor deletar-tarefa] :route-name :deletar-tarefa]
               ["/tarefa/:id" :patch [db-interceptor atualizar-tarefa] :route-name :atualizar-tarefa]}))

(def service-map {::http/routes routes
                  ::http/port 9999
                  ::http/type :jetty
                  ::http/join? false})

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

(comment
  (test-request :get "/ola?name=Luiz")
  (pprint (test-request :get "/ola"))
  (pprint (test-request :post "/tarefa?nome=Correr&status=pendente"))
  (pprint (test-request :post "/tarefa?nome=Ler&status=Pendente"))


  (pprint (clojure.edn/read-string (:body (test-request :get "/tarefa"))))
  (pprint (test-request :get "/tarefa"))

  
  (try (start-server) (catch Exception e (println "Erro ao executar start" (.getMessage e))))
  (try (restart-server) (catch Exception e (println "Erro ao executar restart" (.getMessage e))))
  (pprint @database/store)
  #_.)

