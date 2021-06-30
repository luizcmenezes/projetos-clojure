(ns servico-clojure-pedestal.main
  (:use clojure.pprint)
  (:require [servico-clojure-pedestal.servidor :as servidor]
            [com.stuartsierra.component :as component]
            [servico-clojure-pedestal.database :as database]
            [servico-clojure-pedestal.routes :as routes]))

(defn my-component-system []
  (component/system-map
   :database (database/new-database)
   :routes (routes/new-routes)
   :servidor (component/using (servidor/new-servidor) [:database :routes])))

(def component-result (component/start (my-component-system)))
(pprint component-result)


;; (test-request :get "/ola?name=Luiz")
;; (pprint (servidor/test-request :get "/ola"))
;; (pprint (servidor/test-request :post "/tarefa?nome=Correr&status=pendente"))
;; (pprint (servidor/test-request :post "/tarefa?nome=Ler&status=Pendente"))


;; (pprint (clojure.edn/read-string (:body (test-request :get "/tarefa"))))
;; (pprint (test-request :get "/tarefa"))