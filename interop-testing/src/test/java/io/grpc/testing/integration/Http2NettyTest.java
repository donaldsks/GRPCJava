/*
 * Copyright 2014 The gRPC Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.grpc.testing.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import io.grpc.ServerBuilder;
import io.grpc.ServerCredentials;
import io.grpc.internal.testing.TestUtils;
import io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.InternalNettyChannelBuilder;
import io.grpc.netty.InternalNettyServerBuilder;
import io.grpc.netty.NettyChannelBuilder;
import io.grpc.netty.NettyServerBuilder;
import io.grpc.netty.NettySslContextServerCredentials;
import io.netty.handler.ssl.ClientAuth;
import io.netty.handler.ssl.SupportedCipherSuiteFilter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Integration tests for GRPC over HTTP2 using the Netty framework.
 */
@RunWith(JUnit4.class)
public class Http2NettyTest extends AbstractInteropTest {

  @Override
  protected ServerBuilder<?> getServerBuilder() {
    // Starts the server with HTTPS.
    try {
      ServerCredentials serverCreds = NettySslContextServerCredentials.create(
          GrpcSslContexts
              .forServer(TestUtils.loadCert("server1.pem"), TestUtils.loadCert("server1.key"))
              .clientAuth(ClientAuth.REQUIRE)
              .trustManager(TestUtils.loadCert("ca.pem"))
              .ciphers(TestUtils.preferredTestCiphers(), SupportedCipherSuiteFilter.INSTANCE)
              .build());
      NettyServerBuilder builder = NettyServerBuilder.forPort(0, serverCreds)
          .flowControlWindow(AbstractInteropTest.TEST_FLOW_CONTROL_WINDOW)
          .maxInboundMessageSize(AbstractInteropTest.MAX_MESSAGE_SIZE);
      // Disable the default census stats tracer, use testing tracer instead.
      InternalNettyServerBuilder.setStatsEnabled(builder, false);
      return builder.addStreamTracerFactory(createCustomCensusTracerFactory());
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
  }

  @Override
  protected NettyChannelBuilder createChannelBuilder() {
    try {
      NettyChannelBuilder builder = NettyChannelBuilder
          .forAddress(TestUtils.testServerAddress((InetSocketAddress) getListenAddress()))
          .flowControlWindow(AbstractInteropTest.TEST_FLOW_CONTROL_WINDOW)
          .maxInboundMessageSize(AbstractInteropTest.MAX_MESSAGE_SIZE)
          .sslContext(GrpcSslContexts
              .forClient()
              .keyManager(TestUtils.loadCert("client.pem"), TestUtils.loadCert("client.key"))
              .trustManager(TestUtils.loadX509Cert("ca.pem"))
              .ciphers(TestUtils.preferredTestCiphers(), SupportedCipherSuiteFilter.INSTANCE)
              .build());
      // Disable the default census stats interceptor, use testing interceptor instead.
      InternalNettyChannelBuilder.setStatsEnabled(builder, false);
      return builder.intercept(createCensusStatsClientInterceptor());
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }

  @Test
  public void remoteAddr() {
    InetSocketAddress isa = (InetSocketAddress) obtainRemoteClientAddr();
    assertEquals(InetAddress.getLoopbackAddress(), isa.getAddress());
    // It should not be the same as the server
    assertNotEquals(((InetSocketAddress) getListenAddress()).getPort(), isa.getPort());
  }

  @Test
  public void localAddr() throws Exception {
    InetSocketAddress isa = (InetSocketAddress) obtainLocalServerAddr();
    assertEquals(InetAddress.getLoopbackAddress(), isa.getAddress());
    assertEquals(((InetSocketAddress) getListenAddress()).getPort(), isa.getPort());
  }

  @Test
  public void tlsInfo() {
    assertX500SubjectDn("CN=testclient, O=Internet Widgits Pty Ltd, ST=Some-State, C=AU");
  }
}
