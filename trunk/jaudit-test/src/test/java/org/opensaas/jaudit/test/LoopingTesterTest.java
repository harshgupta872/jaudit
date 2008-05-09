/**
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE
 * You may obtain a copy of the License at
 *
 *   http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.opensaas.jaudit.test;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link org.opensaas.jaudit.test.LoopingTester}.
 */
public class LoopingTesterTest extends LoopingTester {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = Logger
            .getLogger(LoopingTesterTest.class.getName());

    /**
     * Used for counting things in a synchronous fashion.
     */
    private static class Counter {
        private long value;

        private final Object lock = new Object();

        /**
         * Constructor.
         */
        public Counter() {
            this(0L);
        }

        /**
         * Constructor.
         * 
         * @param initialValue
         *            The value from which to start counting.
         */
        public Counter(final long initialValue) {
            value = initialValue;
        }

        /**
         * Return the current value.
         * 
         * @return the current value.
         */
        public final long get() {
            synchronized (lock) {
                return value;
            }
        }

        /**
         * Increment the counter.
         * 
         * @return the new value.
         */
        public long increment() {
            synchronized (lock) {
                return ++value;
            }
        }
    }

    private static final Runnable NOOP = new Runnable() {
        public void run() {
            Thread.yield();
        }
    };

    private static final Runnable FOREVER = new Runnable() {
        public void run() {
            while (true) {
                // run forever
            }
        }
    };

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.LoopingTester#checkLoops(Logger)}.
     */
    @Test
    public void testCheckLoopsSmall() {
        LOOPS = 1;
        checkLoops(null);
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.LoopingTester#checkLoops(Logger)}.
     */
    @Test
    public void testCheckLoopsFail() {
        LOOPS = 0;
        try {
            checkLoops(null);
        } catch (final AssertionError error) {
            // make sure this is the error we were expecting!
            Assert.assertEquals(createInvalidLoopCountMessage(), error
                    .getMessage());
            return;
        }
        Assert.fail("AssertionError expected");
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.LoopingTester#runInThreads(java.lang.Runnable, long, int)}.
     * 
     * @throws Exception
     *             when an error occurs.
     */
    @Test(expected = NullPointerException.class)
    public void testRunInThreadsNullRunnable() throws Exception {
        runInThreads(null, 1L, 1);
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.LoopingTester#runInThreads(java.lang.Runnable, long, int)}.
     * 
     * @throws Exception
     *             when an error occurs.
     */
    @Test(expected = AssertionError.class)
    public void testRunInThreadsBadTimeout() throws Exception {
        runInThreads(NOOP, 0L, 1);
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.LoopingTester#runInThreads(java.lang.Runnable, long, int)}.
     * 
     * @throws Exception
     *             when an error occurs.
     */
    @Test(expected = AssertionError.class)
    public void testRunInThreadsBadPoolSize() throws Exception {
        runInThreads(NOOP, 1L, 0);
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.LoopingTester#runInThreads(java.lang.Runnable, long, int)}.
     * 
     * @throws Exception
     *             when an error occurs.
     */
    @Test
    public void testRunInThreadsTimeout() throws Exception {
        final long timeoutInSeconds = 20L;
        final long start = System.currentTimeMillis() / 1000L;
        try {
            runInThreads(FOREVER, timeoutInSeconds, 5);
        } catch (final AssertionError error) {
            final long finish = System.currentTimeMillis() / 1000L;

            // make sure this is the error we were expecting!
            Assert.assertEquals(createUnexpectedTimeoutMessage(), error
                    .getMessage());

            // guess that elapsed time is roughly correct
            final long lowerBound = timeoutInSeconds
                    - Math.max(1L, timeoutInSeconds / 5L); // 20%
            final long upperBound = timeoutInSeconds * 3; // 300%
            final long duration = finish - start;
            Assert.assertTrue(String.format("Expected %d <= %d <= %d",
                    lowerBound, duration, upperBound), lowerBound <= duration
                    && duration <= upperBound);
            return;
        }
        Assert.fail("AssertionError expected");
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.LoopingTester#runInThreads(java.lang.Runnable, long, int)}.
     * 
     * @throws Exception
     *             when an error occurs.
     */
    @Test
    public void testRunInThreads() throws Exception {
        final Counter counter = new Counter();
        Assert.assertEquals(0L, counter.get());

        LOOPS = 256; // don't run so long
        final int poolSize = 5;
        final Set<Long> threadIds = new HashSet<Long>(poolSize);
        runInThreads(new Runnable() {
            public void run() {
                counter.increment();
                threadIds.add(Thread.currentThread().getId());
                try {
                    // delay a bit to better emulate a thread doing real work
                    Thread.sleep(100L);
                } catch (final InterruptedException ex) {
                    // ignore
                }
            }
        }, 30L, poolSize);

        // we executed the Runnable the right number of times in the right
        // number of threads
        Assert.assertEquals(poolSize, threadIds.size());
        Assert.assertEquals(LOOPS, counter.get());
    }
}
