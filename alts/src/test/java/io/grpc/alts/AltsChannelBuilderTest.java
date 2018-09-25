/*
 * Copyright 2018 The gRPC Authors
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

package io.grpc.alts;

import static com.google.common.truth.Truth.assertThat;

import io.grpc.alts.internal.AltsClientOptions;
import io.grpc.alts.internal.AltsProtocolNegotiator;
import io.grpc.alts.internal.TransportSecurityCommon.RpcProtocolVersions;
import io.grpc.netty.ProtocolNegotiator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public final class AltsChannelBuilderTest {

  @Test
  public void buildsNettyChannel() throws Exception {
    AltsChannelBuilder builder =
        AltsChannelBuilder.forTarget("localhost:8080").enableUntrustedAltsForTesting();

    ProtocolNegotiator protocolNegotiator = builder.getProtocolNegotiatorForTest();
    AltsClientOptions altsClientOptions = builder.getAltsClientOptionsForTest();

    assertThat(protocolNegotiator).isNull();
    assertThat(altsClientOptions).isNull();

    builder.build();

    protocolNegotiator = builder.getProtocolNegotiatorForTest();
    altsClientOptions = builder.getAltsClientOptionsForTest();

    assertThat(protocolNegotiator).isNotNull();
    assertThat(protocolNegotiator).isInstanceOf(AltsProtocolNegotiator.class);

    assertThat(altsClientOptions).isNotNull();
    RpcProtocolVersions expectedVersions =
        RpcProtocolVersions.newBuilder()
            .setMaxRpcVersion(
                RpcProtocolVersions.Version.newBuilder().setMajor(2).setMinor(1).build())
            .setMinRpcVersion(
                RpcProtocolVersions.Version.newBuilder().setMajor(2).setMinor(1).build())
            .build();
    assertThat(altsClientOptions.getRpcProtocolVersions()).isEqualTo(expectedVersions);
  }
}
