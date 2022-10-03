# Integration with Infinispan

## How do I run?

1 Run

```mvn compile quarkus:dev```

2 Run Infinispan  Server

```docker run -it -p 11222:11222 -p 80:80 -e USER="admin" -e PASS="password" quay.io/infinispan/server:13.0```

5 Open http://localhost:11222/console/ and create your key cache.

## How do I test?

```curl --location --request GET 'http://localhost:8080/hello/Robert'```
