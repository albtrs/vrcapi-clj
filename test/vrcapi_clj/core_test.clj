(ns vrcapi-clj.core-test
  (:require [clojure.test :refer :all]
            [vrcapi-clj.core :refer :all]))

(deftest test-01
  (testing "Creating api url"
    (is (= (api-base "auth") "https://api.vrchat.cloud/api/1/auth"))))

(deftest test-02
  (testing "Getting api key (An actual api key may change so test that 'is not nil'.)"
    (is (not= (get-api-key) nil))))

(deftest test-03
  (testing "It's blank"
    (testing "username"
      (is (thrown?
           java.lang.IllegalArgumentException
           (auth nil, "dummy")))
    (testing "password"
      (is (thrown?
           java.lang.IllegalArgumentException
           (auth "password", nil)))))))
