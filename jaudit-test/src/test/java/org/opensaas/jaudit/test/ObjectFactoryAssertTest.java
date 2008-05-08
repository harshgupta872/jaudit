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

import java.math.BigInteger;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link org.opensaas.jaudit.test.ObjectFactoryAssert}.
 */
public class ObjectFactoryAssertTest {

    static class CountingStringFactory implements ObjectFactory<String> {
        private long equivCalls = 0L;

        private BigInteger counter = BigInteger.ZERO;

        /**
         * Return the number of times {@link #createEquivalent()} has been
         * called..
         * 
         * @return the call counter.
         * @see #resetCalls()
         */
        public final long getCalls() {
            return equivCalls;
        }

        /**
         * Reset the call counter to zero;
         */
        public void resetCalls() {
            equivCalls = 0L;
        }

        /**
         * ${@inheritDoc}
         */
        public String createEquivalent() {
            ++equivCalls;
            return new String(toString());
        }

        /**
         * ${@inheritDoc}
         */
        public String createUnique() {
            counter = counter.add(BigInteger.ONE);
            return String.format("%s:%s", this, counter);
        }
    }

    static final CountingStringFactory VALID_FACTORY = new CountingStringFactory();

    private static final ObjectFactory<Exception> EXCEPTION_FACTORY = new ObjectFactory<Exception>() {
        public Exception createEquivalent() {
            throw new UnsupportedOperationException(
                    "Exception does not support equals()");
        }

        public Exception createUnique() {
            return new Exception();
        }
    };

    private static final ObjectFactory<Object> NULL_FACTORY = new ObjectFactory<Object>() {
        public Object createEquivalent() {
            return null;
        }

        public Object createUnique() {
            return null;
        }
    };

    private static final ObjectFactory<Object> SAME_FACTORY = new ObjectFactory<Object>() {
        private Integer x = 0;

        public Object createEquivalent() {
            return x;
        }

        public Object createUnique() {
            return x;
        }
    };

    private static final ObjectFactory<Object> UNIQUE_FACTORY = new ObjectFactory<Object>() {
        public Object createEquivalent() {
            return VALID_FACTORY.createUnique();
        }

        public Object createUnique() {
            return VALID_FACTORY.createUnique();
        }
    };

    private static final ObjectFactory<Object> EQUIV_POINTER_FACTORY = new ObjectFactory<Object>() {
        public Object createEquivalent() {
            return "oops";
        }

        public Object createUnique() {
            return VALID_FACTORY.createUnique();
        }
    };

    private static final ObjectFactory<Object> UNIQUE_POINTER_FACTORY = new ObjectFactory<Object>() {
        int counter = 0;

        public Object createEquivalent() {
            return VALID_FACTORY.createEquivalent();
        }

        public Object createUnique() {
            return ++counter % 10 == 0 ? "oops" : VALID_FACTORY.createUnique();
        }
    };

    private static final ObjectFactory<Object> UNIQUE_DUP_FACTORY = new ObjectFactory<Object>() {
        int counter = 0;

        public Object createEquivalent() {
            return new Integer(0);
        }

        public Object createUnique() {
            counter = (counter % 10) + 1;
            return new Integer(10);
        }
    };

    private static final ObjectFactory<Object> ROTATING_CLASS_FACTORY = new ObjectFactory<Object>() {
        int counter = 0;

        public Object createEquivalent() {
            if (counter % 2 == 1) {
                return new Long(0L);
            }
            return new Integer(0);
        }

        public Object createUnique() {
            if (++counter % 2 == 0) {
                return new Long(counter);
            }
            return new Integer(counter);
        }
    };

    private static final ObjectFactory<Object> MISMATCHED_CLASS_FACTORY = new ObjectFactory<Object>() {
        int counter = 0;

        public Object createEquivalent() {
            return new Long(0L);
        }

        public Object createUnique() {
            return new Integer(++counter);
        }
    };

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.ObjectFactoryAssert#assertEquivalentContract(org.opensaas.jaudit.test.ObjectFactory)}.
     */
    @Test
    public void testAssertEquivalentContract() {
        ObjectFactoryAssert.assertEquivalentContract(VALID_FACTORY);
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.ObjectFactoryAssert#assertEquivalentContract(org.opensaas.jaudit.test.ObjectFactory)}.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testAssertEquivalentContractUnsupported() {
        ObjectFactoryAssert.assertEquivalentContract(EXCEPTION_FACTORY);
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.ObjectFactoryAssert#assertEquivalentContract(org.opensaas.jaudit.test.ObjectFactory)}.
     * Fails <code>createEquivalent() != null</code>.
     */
    @Test
    public void testAssertEquivalentContractNull() {
        try {
            ObjectFactoryAssert.assertEquivalentContract(NULL_FACTORY);
        } catch (final AssertionError error) {
            // make sure this is the error we were expecting!
            Assert.assertThat("Caught " + error, error.getMessage(), Matchers
                    .startsWith("Unexpected null "));
            return;
        }
        Assert.fail("AssertionError expected");
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.ObjectFactoryAssert#assertEquivalentContract(org.opensaas.jaudit.test.ObjectFactory)}.
     * Fails <code>!createEquivalent().equals(createUnique())</code>.
     */
    @Test
    public void testAssertEquivalentContractSame() {
        try {
            ObjectFactoryAssert.assertEquivalentContract(SAME_FACTORY);
        } catch (final AssertionError error) {
            // make sure this is the error we were expecting!
            Assert.assertThat("Caught " + error, error.getMessage(), Matchers
                    .startsWith("Unexpected unique match "));
            return;
        }
        Assert.fail("AssertionError expected");
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.ObjectFactoryAssert#assertEquivalentContract(org.opensaas.jaudit.test.ObjectFactory)}.
     * Fails <code>createEquivalent().equals(createEquivalent())</code>.
     */
    @Test
    public void testAssertEquivalentContractEquals() {
        try {
            ObjectFactoryAssert.assertEquivalentContract(UNIQUE_FACTORY);
        } catch (final AssertionError error) {
            // make sure this is the error we were expecting!
            Assert.assertThat("Caught " + error, error.getMessage(), Matchers
                    .startsWith("Unexpected match "));
            return;
        }
        Assert.fail("AssertionError expected");
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.ObjectFactoryAssert#assertEquivalentContract(org.opensaas.jaudit.test.ObjectFactory)}.
     * Fails <code>createEquivalent() != createEquivalent()</code>.
     */
    @Test
    public void testAssertEquivalentContractDupPointer() {
        try {
            ObjectFactoryAssert.assertEquivalentContract(EQUIV_POINTER_FACTORY);
        } catch (final AssertionError error) {
            // make sure this is the error we were expecting!
            Assert.assertThat("Caught " + error, error.getMessage(), Matchers
                    .startsWith("Unexpected duplicate pointer "));
            return;
        }
        Assert.fail("AssertionError expected");
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.ObjectFactoryAssert#assertEquivalentContract(org.opensaas.jaudit.test.ObjectFactory)}.
     * Fails
     * <code>createEquivalent().getClass().equals(createUnique().getClass())</code>.
     */
    @Test
    public void testAssertEquivalentContractMismatchedClass() {
        try {
            ObjectFactoryAssert
                    .assertEquivalentContract(MISMATCHED_CLASS_FACTORY);
        } catch (final AssertionError error) {
            // make sure this is the error we were expecting!
            Assert.assertThat("Caught " + error, error.getMessage(), Matchers
                    .startsWith("Unexpected mismatched class "));
            return;
        }
        Assert.fail("AssertionError expected");
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.ObjectFactoryAssert#assertUniqueContract(org.opensaas.jaudit.test.ObjectFactory,boolean)}.
     */
    @Test
    public void testAssertUniqueContract() {
        ObjectFactoryAssert.assertUniqueContract(VALID_FACTORY, true);
        ObjectFactoryAssert.assertUniqueContract(EXCEPTION_FACTORY, false);

        VALID_FACTORY.resetCalls();
        ObjectFactoryAssert.assertUniqueContract(VALID_FACTORY, false);
        Assert.assertEquals(
                "createEquivalent() should not have been called at all", 0L,
                VALID_FACTORY.getCalls());
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.ObjectFactoryAssert#assertUniqueContract(org.opensaas.jaudit.test.ObjectFactory,boolean)}.
     * Fails <code>createUnique() != null</code>.
     */
    @Test
    public void testAssertUniqueContractNull() {
        try {
            ObjectFactoryAssert.assertUniqueContract(NULL_FACTORY, true);
        } catch (final AssertionError error) {
            // make sure this is the error we were expecting!
            Assert.assertThat("Caught " + error, error.getMessage(), Matchers
                    .startsWith("Unexpected null "));
            return;
        }
        Assert.fail("AssertionError expected");
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.ObjectFactoryAssert#assertUniqueContract(org.opensaas.jaudit.test.ObjectFactory,boolean)}.
     * Fails <code>!createEquivalent().equals(createUnique())</code>.
     */
    @Test
    public void testAssertUniqueContractSame() {
        try {
            ObjectFactoryAssert.assertUniqueContract(SAME_FACTORY, true);
        } catch (final AssertionError error) {
            // make sure this is the error we were expecting!
            Assert.assertThat("Caught " + error, error.getMessage(), Matchers
                    .startsWith("Unexpected equivalent match "));
            return;
        }
        Assert.fail("AssertionError expected");
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.ObjectFactoryAssert#assertUniqueContract(org.opensaas.jaudit.test.ObjectFactory,boolean)}.
     * Fails <code>createUnique().equals(createUnique())</code>.
     */
    @Test
    public void testAssertUniqueContractEquals() {
        try {
            ObjectFactoryAssert.assertUniqueContract(UNIQUE_DUP_FACTORY, true);
        } catch (final AssertionError error) {
            // make sure this is the error we were expecting!
            Assert.assertThat("Caught " + error, error.getMessage(), Matchers
                    .startsWith("Unexpected match "));
            return;
        }
        Assert.fail("AssertionError expected");
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.ObjectFactoryAssert#assertUniqueContract(org.opensaas.jaudit.test.ObjectFactory,boolean)}.
     * Fails
     * <code>createUnique().getClass().equals(createUnique().getClass())</code>.
     */
    @Test
    public void testAssertUniqueContractConsistentClass() {
        try {
            ObjectFactoryAssert.assertUniqueContract(ROTATING_CLASS_FACTORY,
                    true);
        } catch (final AssertionError error) {
            // make sure this is the error we were expecting!
            Assert.assertThat("Caught " + error, error.getMessage(), Matchers
                    .startsWith("Unexpected class "));
            return;
        }
        Assert.fail("AssertionError expected");
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.ObjectFactoryAssert#assertUniqueContract(org.opensaas.jaudit.test.ObjectFactory,boolean)}.
     * Fails
     * <code>createEquivalent().getClass().equals(createUnique().getClass())</code>.
     */
    @Test
    public void testAssertUniqueContractMismatchedClass() {
        try {
            ObjectFactoryAssert.assertUniqueContract(MISMATCHED_CLASS_FACTORY,
                    true);
        } catch (final AssertionError error) {
            // make sure this is the error we were expecting!
            Assert.assertThat("Caught " + error, error.getMessage(), Matchers
                    .startsWith("Unexpected mismatched class "));
            return;
        }
        Assert.fail("AssertionError expected");
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.ObjectFactoryAssert#assertUniqueContract(org.opensaas.jaudit.test.ObjectFactory,boolean)}.
     * Fails <code>createUnique() != createUnique()</code>.
     */
    @Test
    public void testAssertUniqueContractDupPointer() {
        try {
            ObjectFactoryAssert.assertUniqueContract(UNIQUE_POINTER_FACTORY,
                    true);
        } catch (final AssertionError error) {
            // make sure this is the error we were expecting!
            Assert.assertThat("Caught " + error, error.getMessage(), Matchers
                    .startsWith("Unexpected duplicate pointer "));
            return;
        }
        Assert.fail("AssertionError expected");
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.ObjectFactoryAssert#assertObjectFactoryContract(org.opensaas.jaudit.test.ObjectFactory)}.
     */
    @Test
    public void testAssertObjectFactoryContract() {
        ObjectFactoryAssert.assertObjectFactoryContract(VALID_FACTORY);
        ObjectFactoryAssert.assertObjectFactoryContract(EXCEPTION_FACTORY);
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.ObjectFactoryAssert#assertObjectFactoryContract(org.opensaas.jaudit.test.ObjectFactory)}.
     */
    @Test
    public void testLoopsSmall() {
        // still runs with just 1 loop, but isn't interesting
        LoopingTester.LOOPS = 1;
        ObjectFactoryAssert.assertObjectFactoryContract(VALID_FACTORY);
        ObjectFactoryAssert.assertObjectFactoryContract(EXCEPTION_FACTORY);
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.ObjectFactoryAssert#ObjectFactoryAssert()}.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testObjectFactoryAssert() {
        new ObjectFactoryAssert();
    }
}
