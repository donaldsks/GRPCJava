// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: envoy/api/v2/auth/cert.proto

package io.grpc.xds.shaded.envoy.api.v2.auth;

public interface TlsCertificateOrBuilder extends
    // @@protoc_insertion_point(interface_extends:envoy.api.v2.auth.TlsCertificate)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * The TLS certificate chain.
   * </pre>
   *
   * <code>.envoy.api.v2.core.DataSource certificate_chain = 1;</code>
   */
  boolean hasCertificateChain();
  /**
   * <pre>
   * The TLS certificate chain.
   * </pre>
   *
   * <code>.envoy.api.v2.core.DataSource certificate_chain = 1;</code>
   */
  io.grpc.xds.shaded.envoy.api.v2.core.DataSource getCertificateChain();
  /**
   * <pre>
   * The TLS certificate chain.
   * </pre>
   *
   * <code>.envoy.api.v2.core.DataSource certificate_chain = 1;</code>
   */
  io.grpc.xds.shaded.envoy.api.v2.core.DataSourceOrBuilder getCertificateChainOrBuilder();

  /**
   * <pre>
   * The TLS private key.
   * </pre>
   *
   * <code>.envoy.api.v2.core.DataSource private_key = 2;</code>
   */
  boolean hasPrivateKey();
  /**
   * <pre>
   * The TLS private key.
   * </pre>
   *
   * <code>.envoy.api.v2.core.DataSource private_key = 2;</code>
   */
  io.grpc.xds.shaded.envoy.api.v2.core.DataSource getPrivateKey();
  /**
   * <pre>
   * The TLS private key.
   * </pre>
   *
   * <code>.envoy.api.v2.core.DataSource private_key = 2;</code>
   */
  io.grpc.xds.shaded.envoy.api.v2.core.DataSourceOrBuilder getPrivateKeyOrBuilder();

  /**
   * <pre>
   * The password to decrypt the TLS private key. If this field is not set, it is assumed that the
   * TLS private key is not password encrypted.
   * </pre>
   *
   * <code>.envoy.api.v2.core.DataSource password = 3;</code>
   */
  boolean hasPassword();
  /**
   * <pre>
   * The password to decrypt the TLS private key. If this field is not set, it is assumed that the
   * TLS private key is not password encrypted.
   * </pre>
   *
   * <code>.envoy.api.v2.core.DataSource password = 3;</code>
   */
  io.grpc.xds.shaded.envoy.api.v2.core.DataSource getPassword();
  /**
   * <pre>
   * The password to decrypt the TLS private key. If this field is not set, it is assumed that the
   * TLS private key is not password encrypted.
   * </pre>
   *
   * <code>.envoy.api.v2.core.DataSource password = 3;</code>
   */
  io.grpc.xds.shaded.envoy.api.v2.core.DataSourceOrBuilder getPasswordOrBuilder();

  /**
   * <pre>
   * [#not-implemented-hide:]
   * </pre>
   *
   * <code>.envoy.api.v2.core.DataSource ocsp_staple = 4;</code>
   */
  boolean hasOcspStaple();
  /**
   * <pre>
   * [#not-implemented-hide:]
   * </pre>
   *
   * <code>.envoy.api.v2.core.DataSource ocsp_staple = 4;</code>
   */
  io.grpc.xds.shaded.envoy.api.v2.core.DataSource getOcspStaple();
  /**
   * <pre>
   * [#not-implemented-hide:]
   * </pre>
   *
   * <code>.envoy.api.v2.core.DataSource ocsp_staple = 4;</code>
   */
  io.grpc.xds.shaded.envoy.api.v2.core.DataSourceOrBuilder getOcspStapleOrBuilder();

  /**
   * <pre>
   * [#not-implemented-hide:]
   * </pre>
   *
   * <code>repeated .envoy.api.v2.core.DataSource signed_certificate_timestamp = 5;</code>
   */
  java.util.List<io.grpc.xds.shaded.envoy.api.v2.core.DataSource> 
      getSignedCertificateTimestampList();
  /**
   * <pre>
   * [#not-implemented-hide:]
   * </pre>
   *
   * <code>repeated .envoy.api.v2.core.DataSource signed_certificate_timestamp = 5;</code>
   */
  io.grpc.xds.shaded.envoy.api.v2.core.DataSource getSignedCertificateTimestamp(int index);
  /**
   * <pre>
   * [#not-implemented-hide:]
   * </pre>
   *
   * <code>repeated .envoy.api.v2.core.DataSource signed_certificate_timestamp = 5;</code>
   */
  int getSignedCertificateTimestampCount();
  /**
   * <pre>
   * [#not-implemented-hide:]
   * </pre>
   *
   * <code>repeated .envoy.api.v2.core.DataSource signed_certificate_timestamp = 5;</code>
   */
  java.util.List<? extends io.grpc.xds.shaded.envoy.api.v2.core.DataSourceOrBuilder> 
      getSignedCertificateTimestampOrBuilderList();
  /**
   * <pre>
   * [#not-implemented-hide:]
   * </pre>
   *
   * <code>repeated .envoy.api.v2.core.DataSource signed_certificate_timestamp = 5;</code>
   */
  io.grpc.xds.shaded.envoy.api.v2.core.DataSourceOrBuilder getSignedCertificateTimestampOrBuilder(
      int index);
}
