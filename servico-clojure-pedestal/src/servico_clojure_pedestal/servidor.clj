(ns servico-clojure-pedestal.servidor
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]
            [io.pedestal.test :as test]))

(def store (atom {}))

(defn lista-tarefa [request]
  {:headers {"Content-Type" "application/json"}
   :status 200
   :body @store})

(defn criar-tarefa-mapa [uuid nome status]
  {:uuid uuid :nome nome :status status})

(defn criar-tarefa [request]
  (let [uuid (java.util.UUID/randomUUID)
        nome (get-in request [:query-params :nome])
        status (get-in request [:query-params :status])
        tarefa (criar-tarefa-mapa uuid nome status)]
    (swap! store assoc uuid tarefa)
    {:headers {"Content-Type" "application/json"}
     :status 200
     :body {:mensagem "Tarefa registrada com sucesso!"
            :tarefa tarefa}}))

(defn funcao-hello [request]
  {:headers {"Content-Type" "application/json"}
   :status 200
   :body (str "Bem vindo " (get-in request [:query-params :name] "ao mundo clojure API"))})

(def routes (route/-expand-routes
             #{["/ola" :get funcao-hello :route-name :hello-world]
               ["/tarefa" :post criar-tarefa :route-name :criar-tarefa]
               ["/tarefa" :get lista-tarefa :route-name :lista-tarefa]}))

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
  (test-request :get "/ola")
  (test-request :post "/tarefa?nome=Correr&status=pendente")
  (test-request :post "/tarefa?nome=Correr&status=feito")

  (test-request :get "/tarefa")

  (restart-server)
  #_.)

