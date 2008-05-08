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

import java.util.Random;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link org.opensaas.jaudit.test.ObjectAssert}.
 */
public class ObjectAssertTest {
    static Random RANDOMIZER = new Random();

    private static final ObjectFactory<String> STRING_FACTORY = new DefaultFactories.StringFactory();

    private static final ObjectFactory<Object> UNIQUE_FACTORY = new ObjectFactory<Object>() {
        public Object createEquivalent() {
            return STRING_FACTORY.createUnique();
        }

        public Object createUnique() {
            return STRING_FACTORY.createUnique();
        }
    };

    private static final ObjectFactory<Object> RANDOM_HASH_FACTORY = new ObjectFactory<Object>() {
        class RandomHash {
            private int x;

            /**
             * An object whose {@link Object#hashCode()} doesn't meet the
             * {@link Object#equals()} contract.
             * 
             * @param x
             *            the value of this object.
             */
            public RandomHash(int x) {
                this.x = x;
            }

            @Override
            public int hashCode() {
                return RANDOMIZER.nextInt();
            }

            @Override
            public boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (obj == null) {
                    return false;
                }
                if (getClass() != obj.getClass()) {
                    return false;
                }
                final RandomHash other = (RandomHash) obj;
                if (x != other.x) {
                    return false;
                }
                return true;
            }
        }

        private ObjectFactory<Integer> factory = new DefaultFactories.IntegerFactory();

        public Object createEquivalent() {
            return new RandomHash(factory.createEquivalent().intValue());
        }

        public Object createUnique() {
            return new RandomHash(factory.createUnique().intValue());
        }
    };

    private static final ObjectFactory<Object> CONSTANT_HASH_FACTORY = new ObjectFactory<Object>() {
        class ConstantHash {
            private int x;

            /**
             * An object whose {@link Object#hashCode()} always returns the same
             * number.
             * 
             * @param x
             *            the value of this object.
             */
            public ConstantHash(int x) {
                this.x = x;
            }

            @Override
            public int hashCode() {
                return 7;
            }

            @Override
            public boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (obj == null) {
                    return false;
                }
                if (getClass() != obj.getClass()) {
                    return false;
                }
                final ConstantHash other = (ConstantHash) obj;
                if (x != other.x) {
                    return false;
                }
                return true;
            }
        }

        private ObjectFactory<Integer> factory = new DefaultFactories.IntegerFactory();

        public Object createEquivalent() {
            return new ConstantHash(factory.createEquivalent().intValue());
        }

        public Object createUnique() {
            return new ConstantHash(factory.createUnique().intValue());
        }
    };

    private static final ObjectFactory<Object> NULL_EQUALS_FACTORY = new ObjectFactory<Object>() {
        class EqualsAllowsNull {
            private int x;

            /**
             * An object whose {@link Object#hashCode()} doesn't meet the
             * {@link Object#equals()} contract.
             * 
             * @param x
             *            the value of this object.
             */
            public EqualsAllowsNull(int x) {
                this.x = x;
            }

            @Override
            public int hashCode() {
                return x;
            }

            @Override
            public boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (obj == null) {
                    return true;
                }
                if (getClass() != obj.getClass()) {
                    return false;
                }
                final EqualsAllowsNull other = (EqualsAllowsNull) obj;
                if (x != other.x) {
                    return false;
                }
                return true;
            }
        }

        private ObjectFactory<Integer> factory = new DefaultFactories.IntegerFactory();

        public Object createEquivalent() {
            return new EqualsAllowsNull(factory.createEquivalent().intValue());
        }

        public Object createUnique() {
            return new EqualsAllowsNull(factory.createUnique().intValue());
        }
    };

    private static final ObjectFactory<Object> INCONSISTENT_EQUIV_FACTORY = new ObjectFactory<Object>() {
        class InconsistentEquals {
            @Override
            public int hashCode() {
                return RANDOMIZER.nextInt();
            }

            @Override
            public boolean equals(Object obj) {
                return hashCode() % 2 == 0;
            }
        }

        public Object createEquivalent() {
            return new InconsistentEquals();
        }

        public Object createUnique() {
            return STRING_FACTORY.createUnique();
        }
    };

    private static final ObjectFactory<Object> INCONSISTENT_UNIQUE_FACTORY = new ObjectFactory<Object>() {
        class InconsistentEquals {
            @Override
            public int hashCode() {
                return RANDOMIZER.nextInt();
            }

            @Override
            public boolean equals(Object obj) {
                return hashCode() % 2 == 0;
            }
        }

        public Object createEquivalent() {
            return STRING_FACTORY.createEquivalent();
        }

        public Object createUnique() {
            return new InconsistentEquals();
        }
    };

    private static final ObjectFactory<Object> BAD_TRANSITIVE_FACTORY = new ObjectFactory<Object>() {
        class BadTransitive {
            private int x;

            /**
             * An object whose {@link Object#equals()} contract fails
             * transitivity.
             * 
             * @param x
             *            the value of this object.
             */
            public BadTransitive(int x) {
                this.x = x;
            }

            @Override
            public int hashCode() {
                return x;
            }

            @Override
            public boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (obj == null) {
                    return true;
                }
                if (getClass() != obj.getClass()) {
                    return false;
                }
                final BadTransitive other = (BadTransitive) obj;
                return x < other.x;
            }
        }

        private int counter = 0;

        public Object createEquivalent() {
            return new BadTransitive(++counter);
        }

        public Object createUnique() {
            return new BadTransitive(++counter);
        }
    };

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.ObjectAssert#assertHashCodeContract(Object)}.
     */
    @Test
    public void testAssertHashCodeContract() {
        ObjectAssert
                .assertHashCodeContract(new DefaultFactories.StringFactory());
        ObjectAssert
                .assertHashCodeContract(new DefaultFactories.IntegerFactory());
        ObjectAssert.assertHashCodeContract(CONSTANT_HASH_FACTORY);
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.ObjectAssert#assertEqualsContract(Object)}.
     */
    @Test
    public void testAssertEquals() {
        ObjectAssert.assertEqualsContract(new DefaultFactories.StringFactory());
        ObjectAssert
                .assertEqualsContract(new DefaultFactories.IntegerFactory());
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.ObjectAssert#assertEqualsReflexive(Object)}.
     */
    @Test
    public void testAssertEqualsReflexiveFails() {
        try {
            ObjectAssert.assertEqualsReflexive(INCONSISTENT_EQUIV_FACTORY);
        } catch (final AssertionError error) {
            // make sure this is the error we were expecting!
            Assert.assertThat("Caught " + error, error.getMessage(), Matchers
                    .startsWith("Unexpected reflexivity failure"));
            return;
        }
        Assert.fail("AssertionError expected");
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.ObjectAssert#assertEqualsContract(Object)}.
     */
    @Test(expected = NullPointerException.class)
    public void testAssertEqualsNull() {
        ObjectAssert.assertEqualsContract(null);
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.ObjectAssert#assertEqualsSymmetric(Object)}.
     */
    @Test
    public void testAssertEqualsSymmetricFails1() {
        try {
            ObjectAssert.assertEqualsSymmetric(UNIQUE_FACTORY);
        } catch (final AssertionError error) {
            // make sure this is the error we were expecting!
            Assert.assertThat("Caught " + error, error.getMessage(), Matchers
                    .startsWith("Unexpected symmetric failure x!=y"));
            return;
        }
        Assert.fail("AssertionError expected");
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.ObjectAssert#assertEqualsSymmetric(Object)}.
     */
    @Test
    public void testAssertEqualsSymmetricFails2() {
        try {
            ObjectAssert.assertEqualsSymmetric(BAD_TRANSITIVE_FACTORY);
        } catch (final AssertionError error) {
            // make sure this is the error we were expecting!
            Assert.assertThat("Caught " + error, error.getMessage(), Matchers
                    .startsWith("Unexpected symmetric failure y!=x"));
            return;
        }
        Assert.fail("AssertionError expected");
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.ObjectAssert#assertEqualsSymmetricInequality(Object)}.
     */
    @Test
    public void testAssertEqualsSymmetricInequalityFails1() {
        try {
            // calling twice with this factory should guarantee a failure
            ObjectAssert
                    .assertEqualsSymmetricInequality(INCONSISTENT_UNIQUE_FACTORY);
            ObjectAssert
                    .assertEqualsSymmetricInequality(INCONSISTENT_UNIQUE_FACTORY);
        } catch (final AssertionError error) {
            // make sure this is the error we were expecting!
            Assert.assertThat("Caught " + error, error.getMessage(), Matchers
                    .startsWith("Unexpected symmetric inequality failure"));
            return;
        }
        Assert.fail("AssertionError expected");
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.ObjectAssert#assertEqualsSymmetricInequality(Object)}.
     */
    @Test
    public void testAssertEqualsSymmetricInequalityFails2() {
        // running this test twice should result in both branches failing
        // because
        // of the cyclical nature of the factory being used
        // TODO: write a real test for this!
        testAssertEqualsSymmetricInequalityFails1();
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.ObjectAssert#assertEqualsTransitive(Object)}.
     */
    @Test
    public void testAssertEqualsTransitiveFails1() {
        try {
            ObjectAssert.assertEqualsTransitive(UNIQUE_FACTORY);
        } catch (final AssertionError error) {
            // make sure this is the error we were expecting!
            Assert.assertThat("Caught " + error, error.getMessage(), Matchers
                    .startsWith("Unexpected transitive failure x!=y"));
            return;
        }
        Assert.fail("AssertionError expected");
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.ObjectAssert#assertEqualsTransitive(Object)}.
     */
    @Test
    public void testAssertEqualsTransitiveFails3() {
        try {
            ObjectAssert.assertEqualsTransitive(BAD_TRANSITIVE_FACTORY);
        } catch (final AssertionError error) {
            // make sure this is the error we were expecting!
            Assert.assertThat("Caught " + error, error.getMessage(), Matchers
                    .startsWith("Unexpected transitive failure z!=x"));
            return;
        }
        Assert.fail("AssertionError expected");
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.ObjectAssert#assertEqualsNulls(Object)}.
     */
    @Test
    public void testAssertEqualsNullsFails() {
        try {
            ObjectAssert.assertEqualsNulls(NULL_EQUALS_FACTORY);
        } catch (final AssertionError error) {
            // make sure this is the error we were expecting!
            Assert.assertThat("Caught " + error, error.getMessage(), Matchers
                    .startsWith("Unexpected equal null"));
            return;
        }
        Assert.fail("AssertionError expected");
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.ObjectAssert#assertEqualsConsistent(Object)}.
     */
    @Test
    public void testAssertEqualsConsistentEquivFails() {
        try {
            ObjectAssert.assertEqualsConsistent(INCONSISTENT_EQUIV_FACTORY);
        } catch (final AssertionError error) {
            // make sure this is the error we were expecting!
            Assert.assertThat("Caught " + error, error.getMessage(), Matchers
                    .startsWith("Unexpected equal inequality"));
            return;
        }
        Assert.fail("AssertionError expected");
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.ObjectAssert#assertEqualsConsistent(Object)}.
     */
    @Test
    public void testAssertEqualsConsistentUniqueFails() {
        try {
            ObjectAssert.assertEqualsConsistent(INCONSISTENT_UNIQUE_FACTORY);
        } catch (final AssertionError error) {
            // make sure this is the error we were expecting!
            Assert.assertThat("Caught " + error, error.getMessage(), Matchers
                    .startsWith("Unexpected equal equality"));
            return;
        }
        Assert.fail("AssertionError expected");
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.ObjectAssert#assertEqualsHashCode(Object)}.
     */
    @Test
    public void testAssertEqualsHashCodeFails() {
        try {
            ObjectAssert.assertEqualsHashCode(RANDOM_HASH_FACTORY);
        } catch (final AssertionError error) {
            // make sure this is the error we were expecting!
            Assert.assertThat("Caught " + error, error.getMessage(), Matchers
                    .startsWith("Unexpected hashCode difference"));
            return;
        }
        Assert.fail("AssertionError expected");
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.ObjectAssert#assertToStringContract(Object)}.
     */
    @Test
    public void testAssertToStringContract() {
        ObjectAssert.assertToStringContract(STRING_FACTORY);
        ObjectAssert
                .assertToStringContract(new DefaultFactories.IntegerFactory());
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.ObjectAssert#assertToString(Object)}.
     */
    @Test
    public void testAssertToString() {
        ObjectAssert.assertToString("");
        ObjectAssert.assertToString("Texas Fight!");
        ObjectAssert.assertToString(this);
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.ObjectAssert#assertToString(Object)}.
     */
    @Test(expected = NullPointerException.class)
    public void testAssertToStringNull() {
        ObjectAssert.assertToString(null);
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.ObjectAssert#assertToString(Object)}.
     */
    @Test
    public void testAssertToStringFailNull() {
        try {
            ObjectAssert.assertToString(new Object() {
                @Override
                public String toString() {
                    return null;
                }
            });
        } catch (final AssertionError error) {
            // make sure this is the error we were expecting!
            // we can use this as an arg to createToStringMessagePrefix()
            // because it begins with the same signature as the inner class
            // actually used
            Assert
                    .assertThat(error.getMessage(), Matchers
                            .startsWith(ObjectAssert
                                    .createToStringMessagePrefix(this)));
            return;
        }
        Assert.fail("AssertionError expected");
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.ObjectAssert#assertToString(Object)}.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testAssertToStringFailException() {
        ObjectAssert.assertToString(new Object() {
            @Override
            public String toString() {
                throw new UnsupportedOperationException();
            }
        });
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.ObjectAssert#ObjectAssert()}.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testObjectAssert() {
        new ObjectAssert();
    }
}
