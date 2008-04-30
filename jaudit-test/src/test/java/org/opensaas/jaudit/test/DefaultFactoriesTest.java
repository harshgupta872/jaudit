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

import java.util.NoSuchElementException;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link org.opensaas.jaudit.test.DefaultFactories}.
 */
public class DefaultFactoriesTest {

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.DefaultFactories#StringFactory}.
     */
    @Test
    public void testStringFactory() {
        final DefaultFactories.StringFactory factory = new DefaultFactories.StringFactory();
        ObjectFactoryAssert.assertObjectFactoryContract(factory);
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.DefaultFactories#IntegerFactory}.
     */
    @Test
    public void testIntegerFactory() {
        final DefaultFactories.IntegerFactory factory = new DefaultFactories.IntegerFactory();
        ObjectFactoryAssert.assertObjectFactoryContract(factory);
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.DefaultFactories#IntegerFactory}.
     */
    @Test(expected = NoSuchElementException.class)
    public void testIntegerFactoryLimits() {
        final DefaultFactories.IntegerFactory factory = new DefaultFactories.IntegerFactory();
        factory.counter = -2; // short-circuit or this will take forever!
        for (int i = 0; i < 3; ++i) {
            factory.createUnique();
        }
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.DefaultFactories#IntegerFactory}.
     */
    @Test
    public void testIntegerFactoryWrap() {
        final DefaultFactories.IntegerFactory factory = new DefaultFactories.IntegerFactory();
        factory.counter = Integer.MAX_VALUE;
        Assert.assertEquals(Integer.MIN_VALUE, factory.createUnique()
                .intValue());
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.DefaultFactories#LongFactory}.
     */
    @Test
    public void testLongFactory() {
        final DefaultFactories.LongFactory factory = new DefaultFactories.LongFactory();
        ObjectFactoryAssert.assertObjectFactoryContract(factory);
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.DefaultFactories#LongFactory}.
     */
    @Test(expected = NoSuchElementException.class)
    public void testLongFactoryLimits() {
        final DefaultFactories.LongFactory factory = new DefaultFactories.LongFactory();
        factory.counter = -2; // short-circuit or this will take forever!
        for (int i = 0; i < 3; ++i) {
            factory.createUnique();
        }
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.DefaultFactories#LongFactory}.
     */
    @Test
    public void testLongFactoryWrap() {
        final DefaultFactories.LongFactory factory = new DefaultFactories.LongFactory();
        factory.counter = Long.MAX_VALUE;
        Assert.assertEquals(Long.MIN_VALUE, factory.createUnique().longValue());
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.DefaultFactories#DefaultFactories()}.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testDefaultFactories() {
        new DefaultFactories();
    }
}
