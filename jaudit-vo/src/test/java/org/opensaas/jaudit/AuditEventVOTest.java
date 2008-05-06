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
package org.opensaas.jaudit;

import org.opensaas.jaudit.test.BeanTest;
import org.opensaas.jaudit.test.ObjectFactory;

/**
 * Tests {@link AuditEventVO} based VO objects.
 * 
 * @param <T>
 *            The specific subclass of {@link AuditEvent} under test.
 */
public abstract class AuditEventVOTest<T extends AuditEvent> extends
        BeanTest<T> {

    /**
     * {@inheritDoc}
     */
    @Override
    protected Object[] getTestValues(final Class<?> valueType) {
        if (TransactionRecord.class.isAssignableFrom(valueType)) {
            return new Object[] {
                    TransactionRecordVOTest.FACTORY.createUnique(),
                    TransactionRecordVOTest.FACTORY.createUnique(), null };
        }
        if (AuditSubject.class.isAssignableFrom(valueType)) {
            return new Object[] { new AuditSubject(), null };
        }

        if (SessionRecord.class.isAssignableFrom(valueType)) {
            return new Object[] { SessionRecordVOTest.FACTORY.createUnique(),
                    SessionRecordVOTest.FACTORY.createUnique(), null };
        }

        return super.getTestValues(valueType);
    }

    /**
     * A default implementation which just uses the {@link Class#newInstance()}
     * methods for new instances.
     * 
     * @param type
     *            The class for which to generate an {@link ObjectFactory}.
     * @param <X>
     *            The type of class for which to generate an
     *            {@link ObjectFactory}.
     * @return An {@link ObjectFactory} that uses {@link Class#newInstance()} to
     *         generate objects.
     */
    protected static <X extends AuditEventVO> ObjectFactory<X> newFactory(
            final Class<X> type) {

        return new ObjectFactory<X>() {

            /**
             * {@inheritDoc}
             */
            public X createEquivalent() {
                try {
                    final X newInstance = type.newInstance();
                    final AuditEventVO ae = newInstance;
                    ae.setId(STRING_FACTORY.createEquivalent());
                    return newInstance;
                } catch (final Exception e) {
                    throw new RuntimeException(
                            "Cannot instantiate a new class of type " + type);
                }
            }

            /**
             * {@inheritDoc}
             */
            public X createUnique() {
                try {
                    final X newInstance = type.newInstance();
                    final AuditEventVO ae = newInstance;
                    ae.setId(STRING_FACTORY.createUnique());
                    return newInstance;
                } catch (final Exception e) {
                    throw new RuntimeException(
                            "Cannot instantiate a new class of type " + type);
                }
            }

        };

    }

}
