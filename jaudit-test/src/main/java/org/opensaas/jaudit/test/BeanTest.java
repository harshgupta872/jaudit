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

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.beanutils.PropertyUtils;
import org.junit.Test;
import org.opensaas.jaudit.test.DefaultFactories.StringFactory;

/**
 * A basic abstract class for testing simple bean functionality of an object.
 * 
 * @param <T>
 */
public abstract class BeanTest<T> {

    protected static final Map<Class<?>, Object[]> CLASS_TO_VALUES = newClassToValues();

    protected static final StringFactory STRING_FACTORY = new DefaultFactories.StringFactory();

    protected abstract ObjectFactory<T> getObjectFactory();

    private static final Object[] EMPTY_ARRAY = new Object[] {};

    /**
     * Verify that the factory we'll use for the other tests satisfies the
     * {@ObjectFactory} contract.
     */
    @Test
    public final void verifyFactory() {
        ObjectFactoryAssert.assertObjectFactoryContract(getObjectFactory());
    }

    /**
     * Verifies that all getter/setter combinations (java bean spec) work in
     * combination correctly together. Uses {@link #getTestValues(Class)} to
     * determine test values to feed into setters to test the return value of
     * getters.
     * 
     * @throws Exception
     */
    @Test
    public final void verifyGetterSetters() throws Exception {

        final T bean = getObjectFactory().createEquivalent();

        final PropertyDescriptor[] properties = PropertyUtils
                .getPropertyDescriptors(bean);
        for (PropertyDescriptor pd : properties) {
            if (pd.getReadMethod() == null || pd.getWriteMethod() == null) {
                continue;
            }
            final Object[] testValues = getTestValues(pd);

            AccessorAssert.assertGetterAndSetter(bean, pd.getWriteMethod(), pd
                    .getReadMethod(), testValues);
        }

    }

    /**
     * Test method for the toString() method.
     */
    @Test
    public void testToString() {
        ObjectAssert.assertToStringContract(getObjectFactory());
    }

    /**
     * Test method for hashCode() method.
     */
    @Test
    public void testHashCode() {
        ObjectAssert.assertHashCodeContract(getObjectFactory());
    }

    /**
     * Test method for equals method.
     */
    @Test
    public void testEquals() {
        ObjectAssert.assertEqualsContract(getObjectFactory());
    }

    /**
     * Test that {@link org.opensaas.jaudit.AuditSubject} is properly
     * {@link Serializable}.
     * 
     * @throws Exception
     *             if something bad happens.
     */
    @Test
    public void testSerializable() throws Exception {
        final T bean = getObjectFactory().createEquivalent();
        if (Serializable.class.isAssignableFrom(bean.getClass())) {
            @SuppressWarnings("unchecked")
            ObjectFactory<? extends Serializable> objectFactory = (ObjectFactory<? extends Serializable>) getObjectFactory();
            SerializableAssert.testSerialization(objectFactory);
        }
    }

    /**
     * Will look up test values based on the PropertyDescriptor's read method's
     * return type. This implementation may be overriden but it currently
     * delegates to {@link #getTestValues(Class)} to determine what to return.
     * 
     * @param pd
     *            PropertyDescriptor to check the return type of the read
     *            method.
     * @return Object[] or emtpy if none.
     */
    protected Object[] getTestValues(final PropertyDescriptor pd) {
        final Class<?> returnType = pd.getReadMethod().getReturnType();

        Object[] values = getTestValues(returnType);
        if (values == null) {
            return EMPTY_ARRAY;
        }
        return values;
    }

    /**
     * Returns test values based on the passed in value type. This method may be
     * overriden for types the base BeanTest class does not support. Currently
     * delegates to the Map returned by {@link #newClassToValues()}.
     * 
     * @param valueType
     *            Class to resolve values for.
     * 
     * @return Object[] matching values to test.
     */
    protected Object[] getTestValues(final Class<?> valueType) {

        final Object[] values = CLASS_TO_VALUES.get(valueType);
        if (values == null) {

            // check if enum
            if (valueType.isEnum()) {
                final Object[] enumConsts = valueType.getEnumConstants();
                final List<Object> consts = new ArrayList<Object>(
                        enumConsts.length + 1);
                consts.addAll(Arrays.asList(enumConsts));
                consts.add(null);
                return consts.toArray();
            }

            return EMPTY_ARRAY;
        }
        return values;
    }

    /**
     * Returns a new Map of default test class to test values types useful in
     * testing getters/setters.
     * 
     * @return Map of supported types and associated values.
     */
    protected static Map<Class<?>, Object[]> newClassToValues() {
        final Map<Class<?>, Object[]> ctov = new HashMap<Class<?>, Object[]>();

        ctov.put(Boolean.class, new Object[] { Boolean.TRUE, Boolean.FALSE,
                null });
        ctov.put(Boolean.TYPE, new Object[] { Boolean.TRUE, Boolean.FALSE });
        ctov.put(Date.class, new Object[] { new Date(), new Date(0L),
                new Date(1000L), null });
        ctov.put(Double.class, new Object[] { 0d, Double.MAX_VALUE,
                Double.MIN_VALUE, null });
        ctov.put(Double.TYPE, new Object[] { 0d, Double.MAX_VALUE,
                Double.MIN_VALUE });
        ctov.put(Integer.class, new Object[] { 0, Integer.MAX_VALUE,
                Integer.MIN_VALUE, null });
        ctov.put(Integer.TYPE, new Object[] { 0, Integer.MAX_VALUE,
                Integer.MIN_VALUE });
        ctov.put(Long.class, new Object[] { 0L, Long.MAX_VALUE,
            Long.MIN_VALUE, null });
        ctov.put(Long.TYPE, new Object[] { 0L, Long.MAX_VALUE,
                Long.MIN_VALUE });
        ctov.put(String.class, new Object[] { "", " ", "Texas Fight!",
                UUID.randomUUID().toString(), null });

        return Collections.unmodifiableMap(ctov);
    }

    /**
     * A default implementation which just uses the {@link Class#newInstance()}
     * methods for new instances.
     */
    protected static <X> ObjectFactory<X> newFactory(final Class<X> type) {

        return new ObjectFactory<X>() {

            /**
             * {@inheritDoc}
             */
            public X createEquivalent() {
                try {
                    return type.newInstance();
                } catch (Exception e) {
                    throw new RuntimeException(
                            "Cannot instantiate a new class of type " + type);
                }
            }

            /**
             * {@inheritDoc}
             */
            public X createUnique() {
                try {
                    return type.newInstance();
                } catch (Exception e) {
                    throw new RuntimeException(
                            "Cannot instantiate a new class of type " + type);
                }
            }

        };

    }

}
