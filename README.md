# About
A sample Spring Boot 3 and Infinispan integration. A multiple instances of Infinispan is installed, and connecting to eachother to form a cluster.

## Running Infinispan
Pull from Docker Hub
```
$ docker pull infinispan/server:14.0.2.Final
```

Run the first instance
```
$ docker run -p 11222:11222 -e USER=admin -e PASS=password \
        --add-host=HOST:192.168.56.1 \ 
        infinispan/server:14.0.2.Final
```

Run the second instance
```
$ docker run -p 11223:11222 -e USER=admin -e PASS=password \
        --add-host=HOST:192.168.56.1 \ 
        infinispan/server:14.0.2.Final
```

Run the third instance
```
$ docker run -p 11224:11222 -e USER=admin -e PASS=password \
        --add-host=HOST:192.168.56.1 \ 
        infinispan/server:14.0.2.Final
```

We can validate the result after we login into Infinispan server,
![infinispan-cluster-membership](images/infinispan-server.png)

## Frameworks
- Spring Boot 3.0.4
- Infinispan 14.0.7.Final

## Infinispan Configuration
We are using below XML configuration for setting up a distributed cache.
```xml
<?xml version="1.0"?>
<distributed-cache name="user-cache" owners="2" mode="SYNC" statistics="true">
    <encoding>
        <key media-type="application/x-protostream"/>
        <value media-type="application/x-protostream"/>
    </encoding>
    <locking isolation="REPEATABLE_READ"/>
    <memory storage="OFF_HEAP"/>
</distributed-cache>
```

## Spring Boot Configuration
We can put all servers that are members of the cluster on the `server-list` key.
```properties
infinispan.remote.server-list=172.17.0.2:11222;172.17.0.3:11222;172.17.0.4:11222
infinispan.remote.auth-username=admin
infinispan.remote.auth-password=password
infinispan.remote.marshaller=org.infinispan.commons.marshall.ProtoStreamMarshaller
```

## How to Test
Create a new data in a cache store
```
$ curl -kv http://localhost:8080/add-user?name=lele&age=14&address=Jogja
{"name":"lele","age":14,"address":"Jogja"} 
```

Get the data from a cache store
```
$ curl -kv http://localhost:8080/get-user?name=lele
{"name":"lele","age":14,"address":"Jogja"} 
```

Find Address using Fuzzy search. For this sample, we are using the word `Jogjon`, but application is able to give `Jogja` as result.
```
$ curl -kv http://localhost:8080/find-address?address=Jogjon
{"name":"lele","age":14,"address":"Jogja"} 
```