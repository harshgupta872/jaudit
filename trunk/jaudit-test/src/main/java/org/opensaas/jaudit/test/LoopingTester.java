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

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A base class useful for tests that need to do many iterations when executing.
 */
public abstract class LoopingTester {

    private static final int MIN_LOOPS = 128;

    /**
     * The number of iterations to use when looping.
     */
    protected static int LOOPS = Integer.rotateLeft(MIN_LOOPS, 8);

    /**
     * Verify that the number of loops is at a valid setting. In other words,
     * negative looping is bad!
     * 
     * @param logger
     *            The logger to use for reporting status.
     */
    protected static void checkLoops(final Logger logger) {
        assert LOOPS > 0 : createInvalidLoopCountMessage();
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

    /* package */static String createInvalidLoopCountMessage() {
        return "Test is meaningless without looping";
    }

    /**
     * 
     */
    public LoopingTester() {
        super();
    }

}