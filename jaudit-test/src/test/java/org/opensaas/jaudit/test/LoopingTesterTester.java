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

import java.util.logging.Logger;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

/**
 * Base class for tests that inherit from
 * {@link org.opensaas.jaudit.test.LoopingTester}.
 */
abstract public class LoopingTesterTester {
    private static final int ORIGINAL_LOOPS = LoopingTester.LOOPS;

    /**
     * Ensure the loop count stays the same between tests.
     */
    @After
    public void resetLoops() {
        LoopingTester.LOOPS = ORIGINAL_LOOPS;
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.LoopingTester#checkLoops(Logger)}.
     */
    @Test
    public void testCheckLoopsSmall() {
        LoopingTester.LOOPS = 1;
        LoopingTester.checkLoops(null);
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.LoopingTester#checkLoops(Logger)}.
     */
    @Test
    public void testCheckLoopsFail() {
        LoopingTester.LOOPS = 0;
        try {
            LoopingTester.checkLoops(null);
        } catch (final AssertionError error) {
            // make sure this is the error we were expecting!
            Assert.assertEquals(LoopingTester.createInvalidLoopCountMessage(),
                    error.getMessage());
            return;
        }
        Assert.fail("AssertionError expected");
    }
}