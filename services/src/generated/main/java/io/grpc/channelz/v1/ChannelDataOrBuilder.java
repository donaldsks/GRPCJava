// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: grpc/channelz/v1/channelz.proto

package io.grpc.channelz.v1;

public interface ChannelDataOrBuilder extends
    // @@protoc_insertion_point(interface_extends:grpc.channelz.v1.ChannelData)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * The connectivity state of the channel or subchannel.  Implementations
   * should always set this.
   * </pre>
   *
   * <code>.grpc.channelz.v1.ChannelConnectivityState state = 1;</code>
   */
  boolean hasState();
  /**
   * <pre>
   * The connectivity state of the channel or subchannel.  Implementations
   * should always set this.
   * </pre>
   *
   * <code>.grpc.channelz.v1.ChannelConnectivityState state = 1;</code>
   */
  io.grpc.channelz.v1.ChannelConnectivityState getState();
  /**
   * <pre>
   * The connectivity state of the channel or subchannel.  Implementations
   * should always set this.
   * </pre>
   *
   * <code>.grpc.channelz.v1.ChannelConnectivityState state = 1;</code>
   */
  io.grpc.channelz.v1.ChannelConnectivityStateOrBuilder getStateOrBuilder();

  /**
   * <pre>
   * The target this channel originally tried to connect to.  May be absent
   * </pre>
   *
   * <code>string target = 2;</code>
   */
  java.lang.String getTarget();
  /**
   * <pre>
   * The target this channel originally tried to connect to.  May be absent
   * </pre>
   *
   * <code>string target = 2;</code>
   */
  com.google.protobuf.ByteString
      getTargetBytes();

  /**
   * <pre>
   * A trace of recent events on the channel.  May be absent.
   * </pre>
   *
   * <code>.grpc.channelz.v1.ChannelTrace trace = 3;</code>
   */
  boolean hasTrace();
  /**
   * <pre>
   * A trace of recent events on the channel.  May be absent.
   * </pre>
   *
   * <code>.grpc.channelz.v1.ChannelTrace trace = 3;</code>
   */
  io.grpc.channelz.v1.ChannelTrace getTrace();
  /**
   * <pre>
   * A trace of recent events on the channel.  May be absent.
   * </pre>
   *
   * <code>.grpc.channelz.v1.ChannelTrace trace = 3;</code>
   */
  io.grpc.channelz.v1.ChannelTraceOrBuilder getTraceOrBuilder();

  /**
   * <pre>
   * The number of calls started on the channel
   * </pre>
   *
   * <code>int64 calls_started = 4;</code>
   */
  long getCallsStarted();

  /**
   * <pre>
   * The number of calls that have completed with an OK status
   * </pre>
   *
   * <code>int64 calls_succeeded = 5;</code>
   */
  long getCallsSucceeded();

  /**
   * <pre>
   * The number of calls that have completed with a non-OK status
   * </pre>
   *
   * <code>int64 calls_failed = 6;</code>
   */
  long getCallsFailed();

  /**
   * <pre>
   * The last time a call was started on the channel.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp last_call_started_timestamp = 7;</code>
   */
  boolean hasLastCallStartedTimestamp();
  /**
   * <pre>
   * The last time a call was started on the channel.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp last_call_started_timestamp = 7;</code>
   */
  com.google.protobuf.Timestamp getLastCallStartedTimestamp();
  /**
   * <pre>
   * The last time a call was started on the channel.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp last_call_started_timestamp = 7;</code>
   */
  com.google.protobuf.TimestampOrBuilder getLastCallStartedTimestampOrBuilder();
}
