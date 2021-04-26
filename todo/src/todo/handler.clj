(ns todo.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [ring.middleware.json :as json]
            [ring.util.response :refer [response]]
            [todo.query :as query]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defroutes app-routes
  (GET "/api/todos" []
    (response (query/get-todos)))
  (GET "/api/todos/:id" [id]
    (response (query/get-todo (Integer/parseInt id))))
  (POST "/api/todos" {:keys [params]}
    (let [{:keys [title description]} params]
      (response (query/add-todo title description))))
  (PUT "/api/todos/:id" [id title is_complete]
    (response (query/update-todo (Integer/parseInt id) title is_complete)))
  (DELETE "/api/todos/:id" [id]
    (response (query/delete-todo (Integer/parseInt id))))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (-> (handler/api app-routes)
      (json/wrap-json-params)
      (json/wrap-json-response)
      (wrap-defaults app-routes site-defaults)))
