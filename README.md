# vrcapi-clj

WIP.

```clojure
(ns sample.core
  (:require [vrcapi-clj.core :as client])
  (:require [environ.core :refer [env]]))


(defn -main []
  (let [username (env :username)
        password (env :password)
        auth (client/auth username password)]
    (def json (client/api-get "avatars" {:auth auth
                                         :query {:n 1
                                                 :offset 100}}))
    (println json)))
```
