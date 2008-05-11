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
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Assert;

/**
 * A family of asserts that ease the burden of testing basic object
 * functionality such as {@link Object#equals(Object)} and
 * {@link Object#hashCode()}.
 */
public final class ObjectAssert extends LoopingTester {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = Logger.getLogger(ObjectAssert.class
            .getName());

    /**
     * Used for testing only.
     */
    private static class Dummy {
        /**
         * Technically valid, but terrible.
         */
        @Override
        public int hashCode() {
            return 31;
        }

        /**
         * All Dummy instances are equal.
         */
        @Override
        public boolean equals(final Object obj) {
            return obj instanceof Dummy;
        }
    }

    /**
     * Test that the objects created by the given {@link ObjectFactory} properly
     * follow the {@link Object#equals(Object)} contract.
     * 
     * @param factory
     *            the factory used to generate objects for this test.
     */
    public static void assertEqualsContract(final ObjectFactory<?> factory) {
        assertEqualsReflexive(factory);
        assertEqualsSymmetric(factory);
        assertEqualsSymmetricInequality(factory);
        assertEqualsTransitive(factory);
        assertEqualsConsistent(factory);
        assertEqualsNulls(factory);
        assertEqualsObject(factory);
        assertEqualsHashCode(factory);
    }

    /**
     * Test that toString() works. This isn't amazingly useful other than
     * verifying it doesn't throw an exception or return null.
     * 
     * @param target
     *            the object on which to test toString();
     */
    public static void assertToString(final Object target) {
        final String s = target.toString();
        Assert.assertNotNull(createToStringMessagePrefix(target)
                + " returned null", s);
        if (s.isEmpty()) {
            LOGGER.log(Level.WARNING, "{0} generated an empty string",
                    createToStringMessagePrefix(target));
        }
    }

    /**
     * Test that toString() works. This isn't amazingly useful other than
     * verifying it doesn't throw an exception or return null.
     * 
     * @param factory
     *            the factory used to generate objects for this test.
     */
    public static void assertToStringContract(final ObjectFactory<?> factory) {
        assertToString(factory.createEquivalent());
        for (int i = 0; i < LOOPS; ++i) {
            assertToString(factory.createUnique());
        }
    }

    /**
     * Test that the objects created by the given {@link ObjectFactory} properly
     * follow the {@link Object#hashCode()} contract. This isn't amazingly
     * useful other than verifying it doesn't throw an exception. A log message
     * will be generated if too many instances return the same hash code; this
     * isn't an error because the contract doesn't specify uniqueness, just
     * encourages it.
     * 
     * @param factory
     *            the factory used to generate objects for this test.
     */
    public static void assertHashCodeContract(final ObjectFactory<?> factory) {
        final Set<Integer> hashes = new HashSet<Integer>(LOOPS);
        for (int i = 0; i < LOOPS; ++i) {
            hashes.add(factory.createUnique().hashCode());
        }

        // 5% duplicates is worth warning about
        final int dups = LOOPS - hashes.size();
        if (dups > Math.max(1, LOOPS / 20)) {
            LOGGER
                    .log(
                            Level.WARNING,
                            "Hashcode for class {0} generated {1} duplicates out of {2} calls.",
                            new Object[] {
                                    factory.createEquivalent().getClass(),
                                    dups, LOOPS });
        }
    }

    /* package */static void assertEqualsReflexive(
            final ObjectFactory<?> factory) {
        // doesn't prove reflexivity, but the looping helps reduce the
        // likelihood of failure
        for (int i = 0; i < LOOPS; ++i) {
            final Object x = factory.createEquivalent();
            Assert.assertEquals("Unexpected reflexivity failure", x, x);
        }
    }

    /* package */static void assertEqualsSymmetric(
            final ObjectFactory<?> factory) {
        final Object x = factory.createEquivalent();
        final Object y = factory.createEquivalent();
        Assert.assertEquals("Unexpected symmetric failure x!=y", x, y);
        Assert.assertEquals("Unexpected symmetric failure y!=x", y, x);
    }

    /* package */static void assertEqualsSymmetricInequality(
            final ObjectFactory<?> factory) {
        // doesn't prove symmetric inequality, but the looping helps reduce the
        // likelihood of failure
        for (int i = 0; i < LOOPS; ++i) {
            final Object x = factory.createUnique();
            final Object y = factory.createUnique();
            Assert.assertFalse("Unexpected symmetric inequality failure x==y",
                    x.equals(y));
            Assert.assertFalse("Unexpected symmetric inequality failure y==x",
                    y.equals(x));
        }
    }

    /* package */static void assertEqualsTransitive(
            final ObjectFactory<?> factory) {
        final Object x = factory.createEquivalent();
        final Object y = factory.createEquivalent();
        final Object z = factory.createEquivalent();
        Assert.assertEquals("Unexpected transitive failure x!=y", x, y);
        Assert.assertEquals("Unexpected transitive failure y!=z", y, z);
        Assert.assertEquals("Unexpected transitive failure z!=x", z, x);
    }

    /* package */static void assertEqualsConsistent(
            final ObjectFactory<?> factory) {
        // doesn't prove consistency, but the looping helps reduce the
        // likelihood of failure
        for (int i = 0; i < LOOPS; ++i) {
            Assert.assertFalse("Unexpected equal equality", factory
                    .createUnique().equals(factory.createUnique()));
            Assert.assertEquals("Unexpected equal inequality", factory
                    .createEquivalent(), factory.createEquivalent());
        }
    }

    /* package */static void assertEqualsNulls(final ObjectFactory<?> factory) {
        // doesn't prove all objects fail on null, but the looping helps reduce
        // the likelihood of failure
        for (int i = 0; i < LOOPS; ++i) {
            Assert.assertFalse("Unexpected equal null", factory.createUnique()
                    .equals(null));
        }
    }

    /* package */static void assertEqualsObject(final ObjectFactory<?> factory) {
        final Dummy dummy = new Dummy();
        final Object unique = factory.createUnique();
        Assert.assertFalse("Unexpected class-mismatch equal 1", dummy
                .equals(unique));
        Assert.assertFalse("Unexpected class-mismatch equal 2", unique
                .equals(dummy));
    }

    /* package */static void assertEqualsHashCode(
            final ObjectFactory<?> factory) {
        // doesn't prove hash code consistency, but the looping helps reduce the
        // likelihood of failure
        final int hash = factory.createEquivalent().hashCode();
        for (int i = 0; i < LOOPS; ++i) {
            Assert.assertEquals("Unexpected hashCode difference", hash, factory
                    .createEquivalent().hashCode());
        }
    }

    /* package */static String createToStringMessagePrefix(final Object target) {
        return String.format("toString() for %s", target.getClass());
    }

    /**
     * Created with package protection for testability.
     */
    /* package */ObjectAssert() {
        throw new UnsupportedOperationException(
                "Cannot construct utility class.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Logger getLogger() {
        return LOGGER;
    }
}
