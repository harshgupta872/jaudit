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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;

/**
 * A family of asserts that ease the burden of testing accessors.
 */
public final class AccessorAssert {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = Logger.getLogger(AccessorAssert.class
            .getName());

    private static final Pattern SET_REGEX = Pattern.compile("set[A-Z]\\w*");

    private static final Pattern GET_REGEX = Pattern
            .compile("(?:get|is)[A-Z]\\w*");

    /**
     * Lookup the getter method for the given field and make sure that when
     * called on the target object it returns the specified value. The getter
     * name is expected to be in the standard bean naming convention of
     * <code>get<i>Xxx</i></code>.
     * 
     * @param <T>
     *            The type of class to be tested.
     * @param <X>
     *            The type of object to be returned.
     * @param target
     *            The object under test.
     * @param getterName
     *            The name of the getter method to test.
     * @param expectedValue
     *            The expected value of the resulting getter call. Can be null.
     * @throws IllegalAccessException
     *             if a method is inaccessible by the current class loader.
     * @throws InvocationTargetException
     *             if a called method throws an exception.
     * @throws NoSuchMethodException
     *             if the method name cannot be found.
     */
    public static <T, X> void assertGetter(final T target,
            final String getterName, final X expectedValue)
            throws IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {
        assert target != null : "target cannot be null";
        assert getterName != null : "getterName cannot be null";

        final Method getterMethod = findGetterMethod(target, getterName);
        testGetter(target, getterMethod, expectedValue);
    }

    /**
     * Lookup the setter method for the given field and make sure that when
     * called on the target object, a call to the expected companion getter
     * method returns the same value. The setter name is expected to be in the
     * standard bean naming convention of <code>set<i>Xxx</i></code>.
     * 
     * @param <T>
     *            The type of class to be tested.
     * @param <X>
     *            The type of object to be returned.
     * @param target
     *            The object under test.
     * @param setterName
     *            The name of the setter method to test.
     * @param valueType
     *            The type of class accepted as the parameter to the setter.
     *            Specify it explicitly instead of by inference from
     *            <code>value</code> to enable testing for auto-boxing and
     *            null.
     * @param values
     *            The values to set and then expected by the getter. When more
     *            than one exists they will be each used in sequence.
     * @throws IllegalAccessException
     *             if a method is inaccessible by the current class loader.
     * @throws InvocationTargetException
     *             if a called method throws an exception.
     * @throws NoSuchMethodException
     *             if the method name cannot be found.
     */
    public static <T, X> void assertGetterAndSetter(final T target,
            final String setterName, final Class<X> valueType,
            final X... values) throws IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {
        assert target != null : "target cannot be null";
        assert setterName != null : "setterName cannot be null";
        assert valueType != null : "valueType cannot be null";
        assert values != null : "values cannot be null";

        if (values.length == 0) {
            LOGGER
                    .log(Level.WARNING,
                            "No values passed; test only checking for existence of methods");
        }

        // find setter method
        final Class<?> targetClass = target.getClass();
        final Method setterMethod = targetClass
                .getMethod(setterName, valueType);
        Assert.assertTrue(createIncorrectMethodMessage(setterName, "setter"),
                isSetter(setterMethod));

        // find associated getter method
        final Method getterMethod = findGetterMethod(target, buildGetterName(
                setterName, valueType));

        // invoke set/get pairs for each passed value
        for (final X value : values) {
            setterMethod.invoke(target, value);
            testGetter(target, getterMethod, value);
        }
    }

    /* package */static String createReturnValueIncorrectMessage(
            final String name) {
        return name + "() return value incorrect;";
    }

    /* package */static String createIncorrectMethodMessage(final String name,
            final String type) {
        return String.format("%s() does not appear to be a %s method", name,
                type);
    }

    private static boolean isSetter(final Method method) {
        if (!method.getReturnType().equals(Void.TYPE)) {
            return false;
        }
        final Matcher matcher = SET_REGEX.matcher(method.getName());
        return matcher.matches();
    }

    private static boolean isGetter(final Method method) {
        if (method.getReturnType().equals(Void.TYPE)) {
            return false;
        }
        final Matcher matcher = GET_REGEX.matcher(method.getName());
        return matcher.matches();
    }

    private static String buildGetterName(final String setMethod,
            final Class<?> valueType) {
        final String prefix = Boolean.TYPE.equals(valueType) ? "is" : "get";
        return prefix + setMethod.substring(3);
    }

    private static <T> Method findGetterMethod(final T target,
            final String getterName) throws NoSuchMethodException {
        final Class<?> targetClass = target.getClass();
        final Method getterMethod = targetClass.getMethod(getterName);
        Assert.assertTrue(createIncorrectMethodMessage(getterName, "getter"),
                isGetter(getterMethod));
        return getterMethod;
    }

    private static <T, X> void testGetter(final T target,
            final Method getterMethod, final X expectedValue)
            throws IllegalAccessException, InvocationTargetException {
        // invoke method
        final Object retrievedValue = getterMethod.invoke(target);

        // check result
        if (expectedValue == null) {
            Assert.assertNull(createReturnValueIncorrectMessage(getterMethod
                    .getName()), retrievedValue);
        } else {
            final Class<?> returnType = getterMethod.getReturnType();
            if (returnType.isPrimitive()) {
                Assert.assertEquals(
                        createReturnValueIncorrectMessage(getterMethod
                                .getName()), expectedValue, retrievedValue);
            } else {
                // use same instead of equals because not all objects implement
                // equals
                Assert.assertSame(
                        createReturnValueIncorrectMessage(getterMethod
                                .getName()), expectedValue, retrievedValue);
            }
        }
    }

    /**
     * Created with package protection for testability.
     */
    /* package */AccessorAssert() {
        throw new UnsupportedOperationException(
                "Cannot construct utility class.");
    }
}
