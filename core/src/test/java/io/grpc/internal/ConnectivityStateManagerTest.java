/*
 * Copyright 2016, gRPC Authors All rights reserved.
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

package io.grpc.internal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.google.common.util.concurrent.MoreExecutors;
import io.grpc.ConnectivityState;
import java.util.LinkedList;
import java.util.concurrent.Executor;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Unit tests for {@link ConnectivityStateManager}.
 */
@RunWith(JUnit4.class)
public class ConnectivityStateManagerTest {
  @Rule
  public final ExpectedException thrown = ExpectedException.none();

  private final FakeClock executor = new FakeClock();
  private final ConnectivityStateManager state = new ConnectivityStateManager();
  private final LinkedList<ConnectivityState> sink = new LinkedList<ConnectivityState>();

  @Test
  public void noCallback() {
    state.gotoState(ConnectivityState.CONNECTING);
    assertEquals(ConnectivityState.CONNECTING, state.getState());
    state.gotoState(ConnectivityState.TRANSIENT_FAILURE);
    assertEquals(ConnectivityState.TRANSIENT_FAILURE, state.getState());
  }

  @Test
  public void registerCallbackBeforeStateChanged() {
    state.gotoState(ConnectivityState.CONNECTING);
    assertEquals(ConnectivityState.CONNECTING, state.getState());

    state.notifyWhenStateChanged(new Runnable() {
        @Override
        public void run() {
          sink.add(state.getState());
        }
      }, executor.getScheduledExecutorService(), ConnectivityState.CONNECTING);

    assertEquals(0, executor.numPendingTasks());
    state.gotoState(ConnectivityState.TRANSIENT_FAILURE);
    // Make sure the callback is run in the executor
    assertEquals(0, sink.size());
    assertEquals(1, executor.runDueTasks());
    assertEquals(0, executor.numPendingTasks());
    assertEquals(1, sink.size());
    assertEquals(ConnectivityState.TRANSIENT_FAILURE, sink.poll());
  }
  
  @Test
  public void registerCallbackAfterStateChanged() {
    state.gotoState(ConnectivityState.CONNECTING);
    assertEquals(ConnectivityState.CONNECTING, state.getState());

    state.notifyWhenStateChanged(new Runnable() {
        @Override
        public void run() {
          sink.add(state.getState());
        }
      }, executor.getScheduledExecutorService(), ConnectivityState.IDLE);

    // Make sure the callback is run in the executor
    assertEquals(0, sink.size());
    assertEquals(1, executor.runDueTasks());
    assertEquals(0, executor.numPendingTasks());
    assertEquals(1, sink.size());
    assertEquals(ConnectivityState.CONNECTING, sink.poll());
  }

  @Test
  public void callbackOnlyCalledOnTransition() {
    state.notifyWhenStateChanged(new Runnable() {
        @Override
        public void run() {
          sink.add(state.getState());
        }
      }, executor.getScheduledExecutorService(), ConnectivityState.IDLE);

    state.gotoState(ConnectivityState.IDLE);
    assertEquals(0, executor.numPendingTasks());
    assertEquals(0, sink.size());
  }

  @Test
  public void callbacksAreOneShot() {
    Runnable callback = new Runnable() {
        @Override
        public void run() {
          sink.add(state.getState());
        }
      };

    state.notifyWhenStateChanged(callback, executor.getScheduledExecutorService(),
        ConnectivityState.IDLE);
    // First transition triggers the callback
    state.gotoState(ConnectivityState.CONNECTING);
    assertEquals(1, executor.runDueTasks());
    assertEquals(1, sink.size());
    assertEquals(ConnectivityState.CONNECTING, sink.poll());
    assertEquals(0, executor.numPendingTasks());

    // Second transition doesn't trigger the callback
    state.gotoState(ConnectivityState.TRANSIENT_FAILURE);
    assertEquals(0, sink.size());
    assertEquals(0, executor.numPendingTasks());

    // Register another callback
    state.notifyWhenStateChanged(callback, executor.getScheduledExecutorService(),
        ConnectivityState.TRANSIENT_FAILURE);

    state.gotoState(ConnectivityState.READY);
    state.gotoState(ConnectivityState.IDLE);
    // Callback loses the race with the second stage change
    assertEquals(1, executor.runDueTasks());
    assertEquals(1, sink.size());
    // It will see the second state
    assertEquals(ConnectivityState.IDLE, sink.poll());
    assertEquals(0, executor.numPendingTasks());
  }

  @Test
  public void multipleCallbacks() {
    final LinkedList<String> callbackRuns = new LinkedList<String>();
    state.notifyWhenStateChanged(new Runnable() {
        @Override
        public void run() {
          sink.add(state.getState());
          callbackRuns.add("callback1");
        }
      }, executor.getScheduledExecutorService(), ConnectivityState.IDLE);
    state.notifyWhenStateChanged(new Runnable() {
        @Override
        public void run() {
          sink.add(state.getState());
          callbackRuns.add("callback2");
        }
      }, executor.getScheduledExecutorService(), ConnectivityState.IDLE);
    state.notifyWhenStateChanged(new Runnable() {
        @Override
        public void run() {
          sink.add(state.getState());
          callbackRuns.add("callback3");
        }
      }, executor.getScheduledExecutorService(), ConnectivityState.READY);

    // callback3 is run immediately because the source state is already different from the current
    // state.
    assertEquals(1, executor.runDueTasks());
    assertEquals(1, callbackRuns.size());
    assertEquals("callback3", callbackRuns.poll());
    assertEquals(1, sink.size());
    assertEquals(ConnectivityState.IDLE, sink.poll());

    // Now change the state.
    state.gotoState(ConnectivityState.CONNECTING);
    assertEquals(2, executor.runDueTasks());
    assertEquals(2, callbackRuns.size());
    assertEquals("callback1", callbackRuns.poll());
    assertEquals("callback2", callbackRuns.poll());
    assertEquals(2, sink.size());
    assertEquals(ConnectivityState.CONNECTING, sink.poll());
    assertEquals(ConnectivityState.CONNECTING, sink.poll());

    assertEquals(0, executor.numPendingTasks());
  }

  private Runnable newRecursiveCallback(final Executor executor) {
    return new Runnable() {
      @Override
      public void run() {
        sink.add(state.getState());
        state.notifyWhenStateChanged(newRecursiveCallback(executor), executor, state.getState());
      }
    };
  }

  @Test
  public void registerCallbackFromCallback() {
    state.notifyWhenStateChanged(newRecursiveCallback(executor.getScheduledExecutorService()),
        executor.getScheduledExecutorService(), state.getState());

    state.gotoState(ConnectivityState.CONNECTING);
    assertEquals(1, executor.runDueTasks());
    assertEquals(0, executor.numPendingTasks());
    assertEquals(1, sink.size());
    assertEquals(ConnectivityState.CONNECTING, sink.poll());

    state.gotoState(ConnectivityState.READY);
    assertEquals(1, executor.runDueTasks());
    assertEquals(0, executor.numPendingTasks());
    assertEquals(1, sink.size());
    assertEquals(ConnectivityState.READY, sink.poll());
  }

  @Test
  public void registerCallbackFromCallbackDirectExecutor() {
    state.notifyWhenStateChanged(newRecursiveCallback(MoreExecutors.directExecutor()),
        MoreExecutors.directExecutor(), state.getState());

    state.gotoState(ConnectivityState.CONNECTING);
    assertEquals(1, sink.size());
    assertEquals(ConnectivityState.CONNECTING, sink.poll());

    state.gotoState(ConnectivityState.READY);
    assertEquals(1, sink.size());
    assertEquals(ConnectivityState.READY, sink.poll());
  }

  @Test
  public void disable() {
    state.disable();
    assertTrue(state.isDisabled());

    thrown.expect(UnsupportedOperationException.class);
    thrown.expectMessage("Channel state API is not implemented");
    state.getState();
  }

  @Test
  public void disableThenDisable() {
    state.disable();
    state.disable();
    assertTrue(state.isDisabled());

    thrown.expect(UnsupportedOperationException.class);
    thrown.expectMessage("Channel state API is not implemented");
    state.getState();
  }

  @Test
  public void disableThenGotoReady() {
    state.disable();

    thrown.expect(IllegalStateException.class);
    thrown.expectMessage("ConnectivityStateManager is already disabled");
    state.gotoState(ConnectivityState.READY);
  }

  @Test
  public void shutdownThenReady() {
    state.gotoState(ConnectivityState.SHUTDOWN);
    assertEquals(ConnectivityState.SHUTDOWN, state.getState());

    state.gotoState(ConnectivityState.READY);
    assertEquals(ConnectivityState.SHUTDOWN, state.getState());
  }
}
