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
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Assert;

/**
 * A family of asserts that validate proper behavior of classes that implement
 * {@link ObjectFactory}.
 */
public final class ObjectFactoryAssert extends LoopingTester {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = Logger
            .getLogger(ObjectFactoryAssert.class.getName());

    /**
     * Ensure the given factory follows the contract specified by
     * {@link ObjectFactory#createEquivalent()} and
     * {@link ObjectFactory#createUnique()}.
     * 
     * @param factory
     *            the {@link ObjectFactory} under test.
     * @see ObjectFactory#createEquivalent()
     */
    public static void assertObjectFactoryContract(
            final ObjectFactory<?> factory) {
        final boolean supportsEquiv = discoverEquivalenceSupport(factory);
        assertUniqueContract(factory, supportsEquiv);
        if (supportsEquiv) {
            assertEquivalentContract(factory);
        }
    }

    /* package */static void assertEquivalentContract(
            final ObjectFactory<?> factory) {
        // create a bunch of unique objects for comparison
        final Set<Object> unique = new HashSet<Object>(LOOPS);
        for (int i = 0; i < LOOPS; ++i) {
            unique.add(factory.createUnique());
        }

        // IdentityHashMap uses == instead of .equals(); too bad there isn't an
        // IdentityHashSet!
        final Map<Object, Integer> allObjs = new IdentityHashMap<Object, Integer>(
                LOOPS);
        Object first = null;
        for (int i = 0; i < LOOPS; ++i) {
            final Object x = factory.createEquivalent();
            if (first == null) {
                first = x;
            }

            // createEquivalent() != null
            Assert.assertNotNull("Unexpected null in loop " + i, x);

            // !createEquivalent().equals(createUnique())
            Assert.assertFalse("Unexpected unique match in loop " + i, unique
                    .contains(x));

            // createEquivalent() != createEquivalent()
            Assert.assertNull("Unexpected duplicate pointer in loop " + i,
                    allObjs.put(x, i));

            // createEquivalent().equals(createEquivalent())
            Assert.assertEquals("Unexpected match in loop " + i, first, x);

            // createEquivalent().getClass().equals(createEquivalent().getClass())
            // assume createEquivalent().equals(createEquivalent()) takes care
            // of this

            // createEquivalent().getClass().equals(createUnique().getClass())
            Assert.assertEquals("Unexpected mismatched class in loop " + i, x
                    .getClass(), factory.createUnique().getClass());
        }
    }

    /* package */static void assertUniqueContract(
            final ObjectFactory<?> factory, final boolean supportsEquiv) {
        // for comparison
        final Object equiv = supportsEquiv ? factory.createEquivalent() : null;

        final Set<Object> unique = new HashSet<Object>(LOOPS);
        // IdentityHashMap uses == instead of .equals(); too bad there isn't an
        // IdentityHashSet!
        final Map<Object, Integer> allObjs = new IdentityHashMap<Object, Integer>(
                LOOPS);
        Object first = null;
        for (int i = 0; i < LOOPS; ++i) {
            final Object x = factory.createUnique();
            if (first == null) {
                first = x;
            }

            // createUnique() != null
            Assert.assertNotNull("Unexpected null in loop " + i, x);

            // createUnique() != createUnique()
            Assert.assertNull("Unexpected duplicate pointer in loop " + i,
                    allObjs.put(x, i));

            // !createUnique().equals(createUnique())
            Assert.assertTrue("Unexpected match in loop " + i, unique.add(x));

            // createUnique().getClass().equals(createUnique().getClass())
            Assert.assertEquals(String.format(
                    "Unexpected class [%s] in loop %d", x.getClass().getName(),
                    i), first.getClass(), x.getClass());

            if (supportsEquiv) {
                // !createEquivalent().equals(createUnique())
                Assert.assertFalse("Unexpected equivalent match in loop " + i,
                        equiv.equals(x));

                // createEquivalent().getClass().equals(createUnique().getClass())
                Assert.assertEquals("Unexpected mismatched class in loop " + i,
                        x.getClass(), equiv.getClass());
            }
        }
    }

    private static boolean discoverEquivalenceSupport(
            final ObjectFactory<?> factory) {
        try {
            factory.createEquivalent();
            return true;
        } catch (final UnsupportedOperationException ex) {
            LOGGER.log(Level.FINER,
                    "{0} does not generate objects that support equivalence.",
                    factory);
        }
        return false;
    }

    /**
     * Created with package protection for testability.
     */
    /* package */ObjectFactoryAssert() {
        throw new UnsupportedOperationException(
                "Cannot construct utility class.");
    }

    /**
     * ${@inheritDoc}
     */
    @Override
    protected Logger getLogger() {
        return LOGGER;
    }
}
