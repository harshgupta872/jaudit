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

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link org.opensaas.jaudit.test.AccessorAssert}.
 */
public class AccessorAssertTest {

    /**
     * Used for testing.
     */
    static class DummyObject {
        private String s;

        private int x;

        private boolean b;

        private int setsCalled = 0;

        /**
         * Construct so fields are null.
         */
        public DummyObject() {
            s = null;
            x = 0;
            b = false;
        }

        /**
         * Constructor.
         * 
         * @param s
         *            the s to set
         * @param x
         *            the x to set
         * @param b
         *            the b to set
         */
        public DummyObject(final String s, final int x, final boolean b) {
            this.s = s;
            this.x = x;
            this.b = b;
        }

        /**
         * Looks like a getter but returns void.
         */
        public void getFried() {
            // do nothing
        }

        /**
         * Looks like a getter but takes an argument.
         * 
         * @param crazy
         * @return crazy
         */
        public Object getCrazy(final Object crazy) {
            return crazy;
        }

        /**
         * Looks like a setter but doesn't return void.
         * 
         * @param pass
         * @return pass
         */
        public String setNothing(final String pass) {
            return pass;
        }

        /**
         * Looks like a setter but takes no arguments.
         */
        public void setUp1() {
            // do nothing
        }

        /**
         * Looks like a setter but takes more than one argument.
         * 
         * @param x
         * @param y
         */
        public void setUp2(final Object x, final Object y) {
            // do nothing
        }

        /**
         * Looks like a getter except for the name.
         * 
         * @return this
         */
        public Object self() {
            return this;
        }

        /**
         * Looks like a setter except for the name.
         * 
         * @param mark
         */
        public void check(final String mark) {
            // do nothing
        }

        /**
         * A getter/setter pair that doesn't work.
         * 
         * @param s
         *            the same to set
         */
        public void setSame(final String s) {
            // do nothing
        }

        /**
         * A getter/setter pair that doesn't work.
         * 
         * @return the same
         */
        public String getSame() {
            return "Hook 'em!";
        }

        /**
         * @return the s
         */
        public String getS() {
            return s;
        }

        /**
         * @param s
         *            the s to set
         */
        public void setS(final String s) {
            ++setsCalled;
            this.s = s;
        }

        /**
         * @return the x
         */
        public int getX() {
            return x;
        }

        /**
         * @param x
         *            the x to set
         */
        public void setX(final int x) {
            ++setsCalled;
            this.x = x;
        }

        /**
         * @return the b
         */
        public boolean isB() {
            return b;
        }

        /**
         * @param b
         *            the b to set
         */
        public void setB(final boolean b) {
            ++setsCalled;
            this.b = b;
        }

        /**
         * @return the setsCalled
         */
        public int getSetsCalled() {
            return setsCalled;
        }
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.AccessorAssert#assertGetter(Object, String, Object)}.
     * 
     * @throws Exception
     *             when bad things happen.
     */
    @Test(expected = AssertionError.class)
    public void testAssertGetterNullTarget() throws Exception {
        AccessorAssert.assertGetter(null, "getS", "");
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.AccessorAssert#assertGetter(Object, String, Object)}.
     * 
     * @throws Exception
     *             when bad things happen.
     */
    @Test(expected = AssertionError.class)
    public void testAssertGetterNullName() throws Exception {
        AccessorAssert.assertGetter(new DummyObject(), null, "");
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.AccessorAssert#assertGetter(Object, String, Object)}.
     * 
     * @throws Exception
     *             when bad things happen.
     */
    @Test(expected = NoSuchMethodException.class)
    public void testAssertGetterNoSuchMethod1() throws Exception {
        AccessorAssert.assertGetter(new DummyObject(), "", "");
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.AccessorAssert#assertGetter(Object, String, Object)}.
     * 
     * @throws Exception
     *             when bad things happen.
     */
    @Test(expected = NoSuchMethodException.class)
    public void testAssertGetterNoSuchMethod2() throws Exception {
        AccessorAssert.assertGetter(new DummyObject(), "getCrazy", "");
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.AccessorAssert#assertGetter(Object, String, Object)}.
     * 
     * @throws Exception
     *             when bad things happen.
     */
    @Test
    public void testAssertGetterBadMethodFail1() throws Exception {
        try {
            AccessorAssert.assertGetter(new DummyObject(), "getFried", "");
        } catch (final AssertionError error) {
            // make sure this is the error we were expecting!
            Assert.assertThat(error.getMessage(), Matchers
                    .startsWith(AccessorAssert.createIncorrectMethodMessage(
                            "getFried", "getter")));
            return;
        }
        Assert.fail("AssertionError expected");
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.AccessorAssert#assertGetter(Object, String, Object)}.
     * 
     * @throws Exception
     *             when bad things happen.
     */
    @Test
    public void testAssertGetterBadMethodFail2() throws Exception {
        try {
            AccessorAssert.assertGetter(new DummyObject(), "self", "");
        } catch (final AssertionError error) {
            // make sure this is the error we were expecting!
            Assert.assertThat(error.getMessage(), Matchers
                    .startsWith(AccessorAssert.createIncorrectMethodMessage(
                            "self", "getter")));
            return;
        }
        Assert.fail("AssertionError expected");
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.AccessorAssert#assertGetter(Object, String, Object)}.
     * 
     * @throws Exception
     *             when bad things happen.
     */
    @Test
    public void testAssertGetterStringPass() throws Exception {
        final String expected = "Texas Fight!";
        AccessorAssert.assertGetter(new DummyObject(expected, 0, false),
                "getS", expected);
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.AccessorAssert#assertGetter(Object, String, Object)}.
     * 
     * @throws Exception
     *             when bad things happen.
     */
    @Test
    public void testAssertGetterStringNullPass() throws Exception {
        AccessorAssert.assertGetter(new DummyObject(), "getS", null);
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.AccessorAssert#assertGetter(Object, String, Object)}.
     * 
     * @throws Exception
     *             when bad things happen.
     */
    @Test
    public void testAssertGetterStringFail() throws Exception {
        try {
            AccessorAssert.assertGetter(new DummyObject(), "getS", "oops");
        } catch (final AssertionError error) {
            // make sure this is the error we were expecting!
            Assert.assertThat(error.getMessage(), Matchers
                    .startsWith(AccessorAssert
                            .createReturnValueIncorrectMessage("getS")));
            return;
        }
        Assert.fail("AssertionError expected");
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.AccessorAssert#assertGetter(Object, String, Object)}.
     * 
     * @throws Exception
     *             when bad things happen.
     */
    @Test
    public void testAssertGetterIntPass() throws Exception {
        AccessorAssert.assertGetter(new DummyObject("", 42, true), "getX", 42);
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.AccessorAssert#assertGetter(Object, String, Object)}.
     * 
     * @throws Exception
     *             when bad things happen.
     */
    @Test
    public void testAssertGetterIntFail() throws Exception {
        try {
            AccessorAssert.assertGetter(new DummyObject("", 42, true), "getX",
                    86);
        } catch (final AssertionError error) {
            // make sure this is the error we were expecting!
            Assert.assertThat(error.getMessage(), Matchers
                    .startsWith(AccessorAssert
                            .createReturnValueIncorrectMessage("getX")));
            return;
        }
        Assert.fail("AssertionError expected");
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.AccessorAssert#assertGetter(Object, String, Object)}.
     * 
     * @throws Exception
     *             when bad things happen.
     */
    @Test
    public void testAssertGetterBoolPass() throws Exception {
        AccessorAssert.assertGetter(new DummyObject("", 42, true), "isB", true);
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.AccessorAssert#assertGetter(Object, String, Object)}.
     * 
     * @throws Exception
     *             when bad things happen.
     */
    @Test
    public void testAssertGetterBoolFail() throws Exception {
        try {
            AccessorAssert.assertGetter(new DummyObject("", 42, true), "isB",
                    false);
        } catch (final AssertionError error) {
            // make sure this is the error we were expecting!
            Assert.assertThat(error.getMessage(), Matchers
                    .startsWith(AccessorAssert
                            .createReturnValueIncorrectMessage("isB")));
            return;
        }
        Assert.fail("AssertionError expected");
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.AccessorAssert#assertGetter(Object, String, Object)}.
     * 
     * @throws Exception
     *             when bad things happen.
     */
    @Test
    public void testAssertGetterFailTypeMismatch() throws Exception {
        try {
            AccessorAssert.assertGetter(new DummyObject("", 42, true), "getX",
                    "oops");
        } catch (final AssertionError error) {
            // make sure this is the error we were expecting!
            Assert.assertThat(error.getMessage(), Matchers
                    .startsWith(AccessorAssert
                            .createReturnValueIncorrectMessage("getX")));
            return;
        }
        Assert.fail("AssertionError expected");
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.AccessorAssert#assertGetterAndSetter(Object, String, Class, Object)}.
     * 
     * @throws Exception
     *             when bad things happen.
     */
    @Test(expected = AssertionError.class)
    public void testAssertSetterNullTarget() throws Exception {
        AccessorAssert.assertGetterAndSetter(null, "setS", String.class, "");
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.AccessorAssert#assertGetterAndSetter(Object, String, Class, Object)}.
     * 
     * @throws Exception
     *             when bad things happen.
     */
    @Test(expected = AssertionError.class)
    public void testAssertSetterNullName() throws Exception {
        AccessorAssert.assertGetterAndSetter(new DummyObject(), null,
                Object.class, "");
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.AccessorAssert#assertGetterAndSetter(Object, String, Class, Object)}.
     * 
     * @throws Exception
     *             when bad things happen.
     */
    @Test(expected = AssertionError.class)
    public void testAssertSetterNullClass() throws Exception {
        AccessorAssert.assertGetterAndSetter(new DummyObject(), "setS", null,
                "");
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.AccessorAssert#assertGetterAndSetter(Object, String, Class, Object)}.
     * 
     * @throws Exception
     *             when bad things happen.
     */
    @Test(expected = AssertionError.class)
    public void testAssertSetterNullValues() throws Exception {
        AccessorAssert.assertGetterAndSetter(new DummyObject(), "setS",
                Object.class, (Object[]) null);
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.AccessorAssert#assertGetterAndSetter(Object, String, Class, Object)}.
     * 
     * @throws Exception
     *             when bad things happen.
     */
    @Test(expected = NoSuchMethodException.class)
    public void testAssertSetterNoSuchMethod1() throws Exception {
        AccessorAssert.assertGetterAndSetter(new DummyObject(), "",
                Object.class, "");
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.AccessorAssert#assertGetterAndSetter(Object, String, Class, Object)}.
     * 
     * @throws Exception
     *             when bad things happen.
     */
    @Test(expected = NoSuchMethodException.class)
    public void testAssertSetterNoSuchMethod2() throws Exception {
        AccessorAssert.assertGetterAndSetter(new DummyObject(), "setUp1",
                Object.class, "");
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.AccessorAssert#assertGetterAndSetter(Object, String, Class, Object)}.
     * 
     * @throws Exception
     *             when bad things happen.
     */
    @Test(expected = NoSuchMethodException.class)
    public void testAssertSetterNoSuchMethod3() throws Exception {
        AccessorAssert.assertGetterAndSetter(new DummyObject(), "setUp2",
                Object.class, "");
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.AccessorAssert#assertGetterAndSetter(Object, String, Class, Object)}.
     * 
     * @throws Exception
     *             when bad things happen.
     */
    @Test(expected = NoSuchMethodException.class)
    public void testAssertSetterNoSuchMethod4() throws Exception {
        AccessorAssert.assertGetterAndSetter(new DummyObject(), "setS",
                Object.class, "");
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.AccessorAssert#assertGetterAndSetter(Object, String, Class, Object)}.
     * 
     * @throws Exception
     *             when bad things happen.
     */
    @Test
    public void testAssertSetterBadMethodFail1() throws Exception {
        try {
            AccessorAssert.assertGetterAndSetter(new DummyObject(),
                    "setNothing", String.class, "");
        } catch (final AssertionError error) {
            // make sure this is the error we were expecting!
            Assert.assertThat(error.getMessage(), Matchers
                    .startsWith(AccessorAssert.createIncorrectMethodMessage(
                            "setNothing", "setter")));
            return;
        }
        Assert.fail("AssertionError expected");
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.AccessorAssert#assertGetterAndSetter(Object, String, Class, Object)}.
     * 
     * @throws Exception
     *             when bad things happen.
     */
    @Test
    public void testAssertSetterBadMethodFail2() throws Exception {
        try {
            AccessorAssert.assertGetterAndSetter(new DummyObject(), "check",
                    String.class, "");
        } catch (final AssertionError error) {
            // make sure this is the error we were expecting!
            Assert.assertThat(error.getMessage(), Matchers
                    .startsWith(AccessorAssert.createIncorrectMethodMessage(
                            "check", "setter")));
            return;
        }
        Assert.fail("AssertionError expected");
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.AccessorAssert#assertGetterAndSetter(Object, String, Class, Object)}.
     * 
     * @throws Exception
     *             when bad things happen.
     */
    @Test
    public void testAssertSetterDoNothing() throws Exception {
        final String expected = "Texas Fight!";
        final DummyObject d = new DummyObject(expected, 42, true);
        AccessorAssert.assertGetterAndSetter(d, "setS", String.class,
                new String[] {});
        Assert.assertEquals(expected, d.getS());
        Assert.assertEquals(0, d.getSetsCalled());
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.AccessorAssert#assertGetterAndSetter(Object, String, Class, Object)}.
     * 
     * @throws Exception
     *             when bad things happen.
     */
    @Test
    public void testAssertSetterStringPass() throws Exception {
        final String expected = "Texas Fight!";
        final DummyObject d = new DummyObject("", 0, false);
        AccessorAssert.assertGetterAndSetter(d, "setS", String.class, expected);
        Assert.assertEquals(expected, d.getS());
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.AccessorAssert#assertGetterAndSetter(Object, String, Class, Object)}.
     * 
     * @throws Exception
     *             when bad things happen.
     */
    @Test
    public void testAssertSetterStringNullPass() throws Exception {
        final DummyObject d = new DummyObject("", 0, false);
        AccessorAssert.assertGetterAndSetter(d, "setS", String.class,
                new String[] { null });
        Assert.assertNull(d.getS());
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.AccessorAssert#assertGetterAndSetter(Object, String, Class, Object)}.
     * 
     * @throws Exception
     *             when bad things happen.
     */
    @Test
    public void testAssertSetterStringMultiplePass() throws Exception {
        final DummyObject d = new DummyObject();
        AccessorAssert.assertGetterAndSetter(d, "setS", String.class, "one",
                null, "", "four");
        Assert.assertEquals(4, d.getSetsCalled());
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.AccessorAssert#assertGetterAndSetter(Object, String, Class, Object)}.
     * 
     * @throws Exception
     *             when bad things happen.
     */
    @Test
    public void testAssertSetterStringFail() throws Exception {
        try {
            AccessorAssert.assertGetterAndSetter(new DummyObject(), "setSame",
                    String.class, "oops");
        } catch (final AssertionError error) {
            // make sure this is the error we were expecting!
            Assert.assertThat(error.getMessage(), Matchers
                    .startsWith(AccessorAssert
                            .createReturnValueIncorrectMessage("getSame")));
            return;
        }
        Assert.fail("AssertionError expected");
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.AccessorAssert#assertGetterAndSetter(Object, String, Class, Object)}.
     * 
     * @throws Exception
     *             when bad things happen.
     */
    @Test
    public void testAssertSetterIntPass1() throws Exception {
        final DummyObject d = new DummyObject("", 0, false);
        AccessorAssert.assertGetterAndSetter(d, "setX", Integer.TYPE, 42);
        Assert.assertEquals(42, d.getX());
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.AccessorAssert#assertGetterAndSetter(Object, String, Class, Object)}.
     * 
     * @throws Exception
     *             when bad things happen.
     */
    @Test
    public void testAssertSetterIntPass2() throws Exception {
        final DummyObject d = new DummyObject("", 0, false);
        AccessorAssert.assertGetterAndSetter(d, "setX", Integer.TYPE,
                new Integer(42));
        Assert.assertEquals(42, d.getX());
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.AccessorAssert#assertGetterAndSetter(Object, String, Class, Object)}.
     * 
     * @throws Exception
     *             when bad things happen.
     */
    @Test
    public void testAssertSetterIntMultiplePass() throws Exception {
        final DummyObject d = new DummyObject();
        AccessorAssert.assertGetterAndSetter(d, "setX", Integer.TYPE,
                Integer.MIN_VALUE, -1, 0, 1, Integer.MAX_VALUE);
        Assert.assertEquals(5, d.getSetsCalled());
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.AccessorAssert#assertGetterAndSetter(Object, String, Class, Object)}.
     * 
     * @throws Exception
     *             when bad things happen.
     */
    @Test
    public void testAssertSetterBoolPass() throws Exception {
        final DummyObject d = new DummyObject("", 0, false);
        AccessorAssert.assertGetterAndSetter(d, "setB", Boolean.TYPE, true);
        Assert.assertTrue(d.isB());
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.AccessorAssert#assertGetterAndSetter(Object, String, Class, Object)}.
     * 
     * @throws Exception
     *             when bad things happen.
     */
    @Test
    public void testAssertSetterBoolMultiplePass() throws Exception {
        final DummyObject d = new DummyObject();
        AccessorAssert.assertGetterAndSetter(d, "setB", Boolean.TYPE, true,
                false, Boolean.FALSE, Boolean.TRUE);
        Assert.assertEquals(4, d.getSetsCalled());
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.AccessorAssert#AccessorAssert()}.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testConstructor() {
        new AccessorAssert();
    }
}
