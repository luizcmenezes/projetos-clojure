(ns servico-clojure-pedestal.routes
  (:require [io.pedestal.http.route :as route]
            [com.stuartsierra.component :as component]))

(defrecord Routes []
  component/Lifecycle

  (start [this]
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
                        ["/tarefa" :post criar-tarefa :route-name :criar-tarefa]
                        ["/tarefa" :get listar-tarefa :route-name :listar-tarefa]
                        ["/tarefa/:id" :delete deletar-tarefa :route-name :deletar-tarefa]
                        ["/tarefa/:id" :patch atualizar-tarefa :route-name :atualizar-tarefa]}))

         (assoc this :endpoints routes))

  (stop [this] 
        (assoc this :endpoints nil)))

(defn new-routes []
  (->Routes))

