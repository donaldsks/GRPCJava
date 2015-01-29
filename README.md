grpc-java
=========

How to Build
------------

grpc-java requires Netty 5, which is still in flux. The version we need can be
found in the lib/netty submodule:
```
$ git submodule update --init
$ cd lib/netty
$ mvn install -pl codec-http2 -am -DskipTests=true
```

The codegen plugin requires a recent protobuf build from master (what will
become proto3):
```
$ git clone https://github.com/google/protobuf.git
$ cd protobuf
$ ./autogen.sh
$ ./configure
$ make
$ make check
$ sudo make install
$ cd java
$ mvn install
```

If you are comfortable with C++ compilation and autotools, you can specify a
--prefix for protobuf and use -I in CXXFLAGS, -L in LDFLAGS, LD\_LIBRARY\_PATH,
and PATH to reference it. The environment variables will be used when building
grpc-java.

Now to build grpc-java itself:
```
$ ./gradlew install
```

Navigating Around the Source
----------------------------

Heres a quick readers guide to the code to help folks get started. At a high level there are three distinct layers
to the library: stub, channel & transport. 

### Stub

The 'stub'  layer is what is exposed to most developers and provides type-safe bindings to whatever 
datamodel/IDL/interface you are adapting. An example is provided of a binding to code generated by the protocol-buffers compiler but others should be trivial to add and are welcome.

#### Key Interfaces

[Stream Observer](https://github.com/google/grpc-java/blob/master/stub/src/main/java/io/grpc/stub/StreamObserver.java)


### Channel

The 'channel' layer is an abstraction over transport handling that is suitable for interception/decoration and exposes more behavior to the application than the stub layer. It is intended to be easy for application frameworks to use this layer to address cross-cutting concerns such as logging, monitoring, auth etc. Flow-control is also exposed at this layer to allow more sophisticated applications to interact with it directly.

#### Common

* [Metadata - headers & trailers](https://github.com/google/grpc-java/blob/master/core/src/main/java/io/grpc/Metadata.java)
* [Status - error code namespace & handling](https://github.com/google/grpc-java/blob/master/core/src/main/java/io/grpc/Status.java)

#### Client
* [Channel - client side binding](https://github.com/google/grpc-java/blob/master/core/src/main/java/io/grpc/Channel.java)
* [Call](https://github.com/google/grpc-java/blob/master/core/src/main/java/io/grpc/Call.java)
* [Client Interceptor](https://github.com/google/grpc-java/blob/master/core/src/main/java/io/grpc/ClientInterceptor.java)

#### Server
* [Server call handler - analog to Channel on server](https://github.com/google/grpc-java/blob/master/core/src/main/java/io/grpc/ServerCallHandler.java)
* [Server Call](https://github.com/google/grpc-java/blob/master/core/src/main/java/io/grpc/ServerCall.java)


### Transport

The 'transport' layer does the heavy lifting of putting & taking bytes off the wire. The interfaces to it are abstract just enough to allow plugging in of different implementations. Transports are modeled as 'Stream' factories. The variation in interface between a server stream and a client stream exists to codify their differing semantics for cancellation and error reporting.

#### Common

* [Stream](https://github.com/google/grpc-java/blob/master/core/src/main/java/io/grpc/transport/Stream.java)
* [Stream Listener](https://github.com/google/grpc-java/blob/master/core/src/main/java/io/grpc/transport/StreamListener.java)

#### Client

* [Client Stream](https://github.com/google/grpc-java/blob/master/core/src/main/java/io/grpc/transport/ClientStream.java)
* [Client Stream Listener](https://github.com/google/grpc-java/blob/master/core/src/main/java/io/grpc/transport/ClientStreamListener.java)

#### Server

* [Server Stream](https://github.com/google/grpc-java/blob/master/core/src/main/java/io/grpc/transport/ServerStream.java)
* [Server Stream Listener](https://github.com/google/grpc-java/blob/master/core/src/main/java/io/grpc/transport/ServerStreamListener.java)


### Examples

Tests showing how these layers are composed to execute calls using protobuf messages can be found here https://github.com/google/grpc-java/tree/master/integration-testing/src/main/java/io/grpc/testing/integration
