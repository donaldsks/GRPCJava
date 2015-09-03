/*
 * Copyright 2014, Google Inc. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 *    * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *    * Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following disclaimer
 * in the documentation and/or other materials provided with the
 * distribution.
 *
 *    * Neither the name of Google Inc. nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package io.grpc;

import com.google.common.base.Preconditions;

import java.io.InputStream;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

/**
 * Description of a remote method used by {@link Channel} to initiate a call.
 *
 * <p>Provides the name of the operation to execute as well as {@link Marshaller} instances
 * used to parse and serialize request and response messages.
 *
 * <p>Can be constructed manually but will often be generated by stub code generators.
 */
@Immutable
public class MethodDescriptor<RequestT, ResponseT> {

  private final MethodType type;
  private final String fullMethodName;
  private final Marshaller<RequestT> requestMarshaller;
  private final Marshaller<ResponseT> responseMarshaller;

  /**
   * The call type of a method.
   */
  public enum MethodType {
    /**
     * One request message followed by one response message.
     */
    UNARY,

    /**
     * Zero or more request messages followed by one response message.
     */
    CLIENT_STREAMING,

    /**
     * One request message followed by zero or more response messages.
     */
    SERVER_STREAMING,

    /**
     * Zero or more request and response messages arbitrarily interleaved in time.
     */
    BIDI_STREAMING,

    /**
     * Cardinality and temporal relationships are not known. Implementations should not make
     * buffering assumptions and should largely treat the same as {@link #BIDI_STREAMING}.
     */
    UNKNOWN;

    /**
     * Returns {@code true} if the client will immediately send one request message to the server
     * after calling {@link ClientCall#start(io.grpc.ClientCall.Listener, io.grpc.Metadata)}
     * and then immediately half-close the stream by calling {@link io.grpc.ClientCall#halfClose()}.
     */
    public final boolean clientSendsOneMessage() {
      return this == UNARY || this == SERVER_STREAMING;
    }

    /**
     * Returns {@code true} if the server will immediately send one response message to the client
     * upon receipt of {@link io.grpc.ServerCall.Listener#onHalfClose()} and then immediately
     * close the stream by calling {@link ServerCall#close(Status, io.grpc.Metadata)}.
     */
    public final boolean serverSendsOneMessage() {
      return this == UNARY || this == CLIENT_STREAMING;
    }
  }

  /**
   * A typed abstraction over message serialization and deserialization, a.k.a. marshalling and
   * unmarshalling.
   *
   * <p>Stub implementations will define implementations of this interface for each of the request
   * and response messages provided by a service.
   *
   * @param <T> type of serializable message
   */
  public interface Marshaller<T> {
    /**
     * Given a message, produce an {@link InputStream} for it so that it can be written to the wire.
     * Where possible implementations should produce streams that are {@link io.grpc.KnownLength}
     * to improve transport efficiency.
     *
     * @param value to serialize.
     * @return serialized value as stream of bytes.
     */
    public InputStream stream(T value);

    /**
     * Given an {@link InputStream} parse it into an instance of the declared type so that it can be
     * passed to application code.
     *
     * @param stream of bytes for serialized value
     * @return parsed value
     */
    public T parse(InputStream stream);
  }

  /**
   * Creates a new {@code MethodDescriptor}.
   *
   * @param type the call type of this method
   * @param fullMethodName the fully qualified name of this method
   * @param requestMarshaller the marshaller used to encode and decode requests
   * @param responseMarshaller the marshaller used to encode and decode responses
   */
  public static <RequestT, ResponseT> MethodDescriptor<RequestT, ResponseT> create(
      MethodType type, String fullMethodName,
      Marshaller<RequestT> requestMarshaller,
      Marshaller<ResponseT> responseMarshaller) {
    return new MethodDescriptor<RequestT, ResponseT>(
        type, fullMethodName, requestMarshaller, responseMarshaller);
  }

  private MethodDescriptor(MethodType type, String fullMethodName,
                           Marshaller<RequestT> requestMarshaller,
                           Marshaller<ResponseT> responseMarshaller) {
    this.type = Preconditions.checkNotNull(type, "type");
    this.fullMethodName = Preconditions.checkNotNull(fullMethodName, "fullMethodName");
    this.requestMarshaller = Preconditions.checkNotNull(requestMarshaller, "requestMarshaller");
    this.responseMarshaller = Preconditions.checkNotNull(responseMarshaller, "responseMarshaller");
  }

  /**
   * The call type of the method.
   */
  public MethodType getType() {
    return type;
  }

  /**
   * The fully qualified name of the method.
   */
  public String getFullMethodName() {
    return fullMethodName;
  }

  /**
   * Parse a response payload from the given {@link InputStream}.
   *
   * @param input stream containing response message to parse.
   * @return parsed response message object.
   */
  public ResponseT parseResponse(InputStream input) {
    return responseMarshaller.parse(input);
  }

  /**
   * Convert a request message to an {@link InputStream}.
   *
   * @param requestMessage to serialize using the request {@link Marshaller}.
   * @return serialized request message.
   */
  public InputStream streamRequest(RequestT requestMessage) {
    return requestMarshaller.stream(requestMessage);
  }

  /**
   * Parse an incoming request message.
   *
   * @param input the serialized message as a byte stream.
   * @return a parsed instance of the message.
   */
  public RequestT parseRequest(InputStream input) {
    return requestMarshaller.parse(input);
  }

  /**
   * Serialize an outgoing response message.
   *
   * @param response the response message to serialize.
   * @return the serialized message as a byte stream.
   */
  public InputStream streamResponse(ResponseT response) {
    return responseMarshaller.stream(response);
  }

  /**
   * Generate the fully qualified method name.
   *
   * @param fullServiceName the fully qualified service name that is prefixed with the package name
   * @param methodName the short method name
   */
  public static String generateFullMethodName(String fullServiceName, String methodName) {
    return fullServiceName + "/" + methodName;
  }

  /**
   * Extract the fully qualified service name out of a fully qualified method name. May return
   * {@code null} if the input is malformed, but you cannot rely on it for the validity of the
   * input.
   */
  @Nullable
  public static String extractFullServiceName(String fullMethodName) {
    int index = fullMethodName.lastIndexOf("/");
    if (index == -1) {
      return null;
    }
    return fullMethodName.substring(0, index);
  }
}
