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

import java.util.UUID;
import java.util.logging.Logger;

import org.junit.Test;
import org.opensaas.jaudit.test.AccessorAssert;
import org.opensaas.jaudit.test.DefaultFactories;
import org.opensaas.jaudit.test.ObjectAssert;
import org.opensaas.jaudit.test.ObjectFactory;
import org.opensaas.jaudit.test.ObjectFactoryAssert;
import org.opensaas.jaudit.test.SerializableAssert;

/**
 * Test class for {@link org.opensaas.jaudit.AuditSubject}.
 */
public class AuditSubjectTest {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = Logger
            .getLogger(AuditSubjectTest.class.getName());

    /**
     * An {@link ObjectFactory} for {@link AuditSubject}s.
     */
    public static class AuditSubjectFactory implements
            ObjectFactory<AuditSubject> {
        private int counter = 0;

        /**
         * {@inheritDoc}
         */
        public AuditSubject createEquivalent() {
            return new AuditSubject();
        }

        /**
         * {@inheritDoc}
         */
        public AuditSubject createUnique() {
            final AuditSubject subject = new AuditSubject();

            // use bitwise math to grab all combinations, guaranteeing at least
            // one is picked
            counter = ++counter % 7;
            if ((counter & 1) != 1) {
                subject.setSubjectId(STRING_FACTORY.createUnique());
            }
            if ((counter & 2) != 2) {
                subject.setSubjectType(STRING_FACTORY.createUnique());
            }
            if ((counter & 4) != 4) {
                subject.setSubjectDiscriminator(STRING_FACTORY.createUnique());
            }
            return subject;
        }
    }

    private static final DefaultFactories.StringFactory STRING_FACTORY = new DefaultFactories.StringFactory();

    private static final AuditSubjectFactory AUDIT_SUBJECT_FACTORY = new AuditSubjectFactory();

    /**
     * Verify that the factory we'll use for the other tests satisfies the
     * {@ObjectFactory} contract.
     */
    @Test
    public void verifyFactory() {
        ObjectFactoryAssert
                .assertObjectFactoryContract(new AuditSubjectFactory());
    }

    /**
     * Test method for {@link org.opensaas.jaudit.AuditSubject#getSubjectType()}
     * and
     * {@link org.opensaas.jaudit.AuditSubject#setSubjectType(java.lang.String)}.
     * 
     * @throws Exception
     *             when an error occurs.
     */
    @Test
    public void testSubjectType() throws Exception {
        AccessorAssert.assertGetterAndSetter(new AuditSubject(),
                "setSubjectType", String.class, "", " ", null, "Texas Fight!",
                UUID.randomUUID().toString());
    }

    /**
     * Test method for {@link org.opensaas.jaudit.AuditSubject#getSubjectId()}
     * and
     * {@link org.opensaas.jaudit.AuditSubject#setSubjectId(java.lang.String)}.
     * 
     * @throws Exception
     *             when an error occurs.
     */
    @Test
    public void testSubjectId() throws Exception {
        AccessorAssert.assertGetterAndSetter(new AuditSubject(),
                "setSubjectId", String.class, "", " ", null, "Texas Fight!",
                UUID.randomUUID().toString());
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.AuditSubject#getSubjectDiscriminator()} and .
     * {@link org.opensaas.jaudit.AuditSubject#setSubjectDiscriminator(java.lang.String)}.
     * 
     * @throws Exception
     *             when an error occurs.
     */
    @Test
    public void testSubjectDiscriminator() throws Exception {
        AccessorAssert.assertGetterAndSetter(new AuditSubject(),
                "setSubjectDiscriminator", String.class, "", " ", null,
                "Texas Fight!", UUID.randomUUID().toString());
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.AuditSubject#equals(java.lang.Object)}.
     */
    @Test
    public void testEqualsObject() {
        ObjectAssert.assertEqualsContract(AUDIT_SUBJECT_FACTORY);
    }

    /**
     * Test method for {@link org.opensaas.jaudit.AuditSubject#toString()}.
     */
    @Test
    public void testToString() {
        ObjectAssert.assertToStringContract(AUDIT_SUBJECT_FACTORY);
    }

    /**
     * Test method for {@link org.opensaas.jaudit.AuditSubject#hashCode()}.
     */
    @Test
    public void testHashCode() {
        ObjectAssert.assertHashCodeContract(AUDIT_SUBJECT_FACTORY);
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
        SerializableAssert.testSerialization(AUDIT_SUBJECT_FACTORY);
    }

}
