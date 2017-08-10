// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: load_balancer.proto

package io.grpc.grpclb;

public final class LoadBalancerProto {
  private LoadBalancerProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_grpc_lb_v1_LoadBalanceRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_grpc_lb_v1_LoadBalanceRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_grpc_lb_v1_InitialLoadBalanceRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_grpc_lb_v1_InitialLoadBalanceRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_grpc_lb_v1_ClientStatsPerToken_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_grpc_lb_v1_ClientStatsPerToken_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_grpc_lb_v1_ClientStats_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_grpc_lb_v1_ClientStats_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_grpc_lb_v1_LoadBalanceResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_grpc_lb_v1_LoadBalanceResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_grpc_lb_v1_InitialLoadBalanceResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_grpc_lb_v1_InitialLoadBalanceResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_grpc_lb_v1_ServerList_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_grpc_lb_v1_ServerList_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_grpc_lb_v1_Server_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_grpc_lb_v1_Server_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\023load_balancer.proto\022\ngrpc.lb.v1\032\036googl" +
      "e/protobuf/duration.proto\032\037google/protob" +
      "uf/timestamp.proto\"\244\001\n\022LoadBalanceReques" +
      "t\022@\n\017initial_request\030\001 \001(\0132%.grpc.lb.v1." +
      "InitialLoadBalanceRequestH\000\022/\n\014client_st" +
      "ats\030\002 \001(\0132\027.grpc.lb.v1.ClientStatsH\000B\033\n\031" +
      "load_balance_request_type\")\n\031InitialLoad" +
      "BalanceRequest\022\014\n\004name\030\001 \001(\t\"D\n\023ClientSt" +
      "atsPerToken\022\032\n\022load_balance_token\030\001 \001(\t\022" +
      "\021\n\tnum_calls\030\002 \001(\003\"\244\002\n\013ClientStats\022-\n\tti",
      "mestamp\030\001 \001(\0132\032.google.protobuf.Timestam" +
      "p\022\031\n\021num_calls_started\030\002 \001(\003\022\032\n\022num_call" +
      "s_finished\030\003 \001(\003\0225\n-num_calls_finished_w" +
      "ith_client_failed_to_send\030\006 \001(\003\022)\n!num_c" +
      "alls_finished_known_received\030\007 \001(\003\022A\n\030ca" +
      "lls_finished_with_drop\030\010 \003(\0132\037.grpc.lb.v" +
      "1.ClientStatsPerTokenJ\004\010\004\020\005J\004\010\005\020\006\"\246\001\n\023Lo" +
      "adBalanceResponse\022B\n\020initial_response\030\001 " +
      "\001(\0132&.grpc.lb.v1.InitialLoadBalanceRespo" +
      "nseH\000\022-\n\013server_list\030\002 \001(\0132\026.grpc.lb.v1.",
      "ServerListH\000B\034\n\032load_balance_response_ty" +
      "pe\"}\n\032InitialLoadBalanceResponse\022\036\n\026load" +
      "_balancer_delegate\030\001 \001(\t\022?\n\034client_stats" +
      "_report_interval\030\002 \001(\0132\031.google.protobuf" +
      ".Duration\"i\n\nServerList\022#\n\007servers\030\001 \003(\013" +
      "2\022.grpc.lb.v1.Server\0226\n\023expiration_inter" +
      "val\030\003 \001(\0132\031.google.protobuf.Duration\"Z\n\006" +
      "Server\022\022\n\nip_address\030\001 \001(\014\022\014\n\004port\030\002 \001(\005" +
      "\022\032\n\022load_balance_token\030\003 \001(\t\022\014\n\004drop\030\004 \001" +
      "(\010J\004\010\005\020\0062b\n\014LoadBalancer\022R\n\013BalanceLoad\022",
      "\036.grpc.lb.v1.LoadBalanceRequest\032\037.grpc.l" +
      "b.v1.LoadBalanceResponse(\0010\001B%\n\016io.grpc." +
      "grpclbB\021LoadBalancerProtoP\001b\006proto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          com.google.protobuf.DurationProto.getDescriptor(),
          com.google.protobuf.TimestampProto.getDescriptor(),
        }, assigner);
    internal_static_grpc_lb_v1_LoadBalanceRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_grpc_lb_v1_LoadBalanceRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_grpc_lb_v1_LoadBalanceRequest_descriptor,
        new java.lang.String[] { "InitialRequest", "ClientStats", "LoadBalanceRequestType", });
    internal_static_grpc_lb_v1_InitialLoadBalanceRequest_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_grpc_lb_v1_InitialLoadBalanceRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_grpc_lb_v1_InitialLoadBalanceRequest_descriptor,
        new java.lang.String[] { "Name", });
    internal_static_grpc_lb_v1_ClientStatsPerToken_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_grpc_lb_v1_ClientStatsPerToken_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_grpc_lb_v1_ClientStatsPerToken_descriptor,
        new java.lang.String[] { "LoadBalanceToken", "NumCalls", });
    internal_static_grpc_lb_v1_ClientStats_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_grpc_lb_v1_ClientStats_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_grpc_lb_v1_ClientStats_descriptor,
        new java.lang.String[] { "Timestamp", "NumCallsStarted", "NumCallsFinished", "NumCallsFinishedWithClientFailedToSend", "NumCallsFinishedKnownReceived", "CallsFinishedWithDrop", });
    internal_static_grpc_lb_v1_LoadBalanceResponse_descriptor =
      getDescriptor().getMessageTypes().get(4);
    internal_static_grpc_lb_v1_LoadBalanceResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_grpc_lb_v1_LoadBalanceResponse_descriptor,
        new java.lang.String[] { "InitialResponse", "ServerList", "LoadBalanceResponseType", });
    internal_static_grpc_lb_v1_InitialLoadBalanceResponse_descriptor =
      getDescriptor().getMessageTypes().get(5);
    internal_static_grpc_lb_v1_InitialLoadBalanceResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_grpc_lb_v1_InitialLoadBalanceResponse_descriptor,
        new java.lang.String[] { "LoadBalancerDelegate", "ClientStatsReportInterval", });
    internal_static_grpc_lb_v1_ServerList_descriptor =
      getDescriptor().getMessageTypes().get(6);
    internal_static_grpc_lb_v1_ServerList_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_grpc_lb_v1_ServerList_descriptor,
        new java.lang.String[] { "Servers", "ExpirationInterval", });
    internal_static_grpc_lb_v1_Server_descriptor =
      getDescriptor().getMessageTypes().get(7);
    internal_static_grpc_lb_v1_Server_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_grpc_lb_v1_Server_descriptor,
        new java.lang.String[] { "IpAddress", "Port", "LoadBalanceToken", "Drop", });
    com.google.protobuf.DurationProto.getDescriptor();
    com.google.protobuf.TimestampProto.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
