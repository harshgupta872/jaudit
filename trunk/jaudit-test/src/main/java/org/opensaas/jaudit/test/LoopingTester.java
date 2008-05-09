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

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Assert;

/**
 * A base class useful for tests that need to do many iterations when executing.
 */
public abstract class LoopingTester {

    private static final int MIN_LOOPS = 128;

    /**
     * The number of iterations to use when looping. This can be altered by test
     * methods, but {@link #resetLoops()} will ensure that it is reset between
     * every test.
     */
    protected static int LOOPS = Integer.rotateLeft(MIN_LOOPS, 8);

    private static final int ORIGINAL_LOOPS = LOOPS;

    /**
     * Ensure the loop count stays the same between tests.
     */
    @After
    public void resetLoops() {
        LOOPS = ORIGINAL_LOOPS;
    }

    /**
     * Verify that the number of loops is at a valid setting. In other words,
     * negative looping is bad!
     * 
     * @param logger
     *            The logger to use for reporting status.
     */
    protected static void checkLoops(final Logger logger) {
        Assert.assertTrue(createInvalidLoopCountMessage(), LOOPS > 0);
        if (logger != null) {
            if (LOOPS < MIN_LOOPS) {
                logger
                        .log(
                                Level.WARNING,
                                "Very few iterations for testing: {0}; test has questionable validity.",
                                LOOPS);
            } else {
                logger.log(Level.FINEST, "Using {0} loops for testing.", LOOPS);
            }
        }
    }

    /**
     * Execute {@link Runnable} {@link #LOOPS} times concurrently using multiple
     * threads, returning when all threads are complete. The poolSize variable
     * controls how many threads will run concurrently.
     * 
     * @param run
     *            The code to execute concurrently.
     * @param timeoutInSeconds
     *            How long to wait before aborting.
     * @param poolSize
     *            The number of threads to use.
     * @throws InterruptedException
     *             if a thread is interrupted.
     */
    protected static void runInThreads(final Runnable run,
            final long timeoutInSeconds, final int poolSize)
            throws InterruptedException {
        Assert.assertTrue("timeout must be positive; was " + timeoutInSeconds,
                timeoutInSeconds > 0L);
        Assert.assertTrue("poolSize must be positive; was " + poolSize,
                poolSize > 0);

        final long start = System.currentTimeMillis();
        final ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors
                .newFixedThreadPool(poolSize);
        for (int i = 0; i < LOOPS; ++i) {
            executor.execute(run);
        }
        // wait for tasks to complete
        while (System.currentTimeMillis() - start < timeoutInSeconds * 1000L) {
            if (executor.getCompletedTaskCount() >= LOOPS) {
                break;
            }
            Thread.sleep(1000);
        }
        // cleanup executor
        executor.shutdown();
        Assert.assertTrue(createUnexpectedTimeoutMessage(), executor
                .awaitTermination(timeoutInSeconds, TimeUnit.SECONDS));
    }

    /* package */static String createUnexpectedTimeoutMessage() {
        return "Unexpected timeout";
    }

    /* package */static String createInvalidLoopCountMessage() {
        return "Test is meaningless without looping";
    }
}