(ns conversor.core
  (:require [clojure.tools.cli :refer [parse-opts]]
            [cheshire.core :refer :all]
            [clj-http.client :as http-client])
  (:gen-class))

(def chave (System/getenv "CHAVE_API"))

(def api-url
  "https://free.currencyconverterapi.com/api/v6/convert")

(def usd-brl
  (:body (http-client/get api-url
          {:query-params {"q" "USD_BRL"
                          "apiKey" chave}})))

(parse-string usd-brl)

(defn parametrizar-moedas [de para]
  (str de "_" para))

(defn -main
  [& args]
  (let [{:keys [de para]} (:options
                           (parse-opts args opcoes-do-programa))]
        (prn "Cotação:" (http-client/get api-url
            {:query-params {"q" (parametrizar-moedas de para)
                            "apiKey" chave}}))))