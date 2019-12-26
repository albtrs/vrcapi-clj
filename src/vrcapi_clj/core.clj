(ns vrcapi-clj.core
  (:require [clj-http.client :as http]
            [clojure.data.json :as json]
            [clojure.string :as s]))

(let [base-url "https://api.vrchat.cloud/api/1/"]

  (defn api-base [end-point]
    (str base-url end-point))

  (defn take-as-json
    [response param-key]
    (-> response
        (get param-key)
        (json/read-str)))

  (defn api-get
    [end-point & options]
    (let [url (api-base end-point)
          {:keys [auth query raw?]} options]
      (let [params (merge auth query)
            response (http/get url {:query-params params})]
        (if raw?
          response
          (take-as-json response :body)))))

  (defn get-api-key []
    ((api-get "config") "apiKey"))

  (defn get-token
    [api-key, username, password]
    (when (some s/blank? [username, password])
      (throw (IllegalArgumentException. "username or password is blank.")))
    (-> (api-base "auth/user")
        (http/get {:basic-auth [username password]
                   :query-params {:apiKey api-key}})
        (get :cookies)
        (get "auth")
        (get :value)))

  (defn auth
    [username, password]
    (let [api-key (get-api-key)
          token   (get-token api-key username password)]
      {"apiKey" api-key
       "token"  token})))

