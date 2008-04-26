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
import org.opensaas.jaudit.AuditSubjectTest.AuditSubjectFactory;
import org.opensaas.jaudit.test.AccessorAssert;
import org.opensaas.jaudit.test.DefaultFactories;
import org.opensaas.jaudit.test.ObjectAssert;
import org.opensaas.jaudit.test.ObjectFactory;
import org.opensaas.jaudit.test.ObjectFactoryAssert;
import org.opensaas.jaudit.test.SerializableAssert;

/**
 * Test method for {@link org.opensaas.jaudit.ResponsibleInformation}.
 */
public class ResponsibleInformationTest {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = Logger
            .getLogger(ResponsibleInformationTest.class.getName());

    private static class ResponsibleInformationFactory implements
            ObjectFactory<ResponsibleInformation> {
        private int counter = 0;

        /**
         * {@inheritDoc}
         */
        public ResponsibleInformation createEquivalent() {
            return new ResponsibleInformation();
        }

        /**
         * {@inheritDoc}
         */
        public ResponsibleInformation createUnique() {
            final ResponsibleInformation info = new ResponsibleInformation();

            // use bitwise math to grab all combinations, guaranteeing at least
            // one is picked
            counter = ++counter % 15;
            if ((counter & 1) != 1) {
                info.setCredentialsType(STRING_FACTORY.createUnique());
            }
            if ((counter & 2) != 2) {
                info.setResponsible(AUDIT_SUBJECT_FACTORY.createUnique());
            }
            if ((counter & 4) != 4) {
                info.setResponsibleAddress(STRING_FACTORY.createUnique());
            }
            if ((counter & 8) != 8) {
                info.setResponsibleAgent(STRING_FACTORY.createUnique());
            }
            return info;
        }
    }

    private static final DefaultFactories.StringFactory STRING_FACTORY = new DefaultFactories.StringFactory();

    private static final AuditSubjectFactory AUDIT_SUBJECT_FACTORY = new AuditSubjectFactory();

    private static final ResponsibleInformationFactory RESPONSIBLE_FACTORY = new ResponsibleInformationFactory();

    /**
     * Verify that the factory we'll use for the other tests satisfies the
     * {@ObjectFactory} contract.
     */
    @Test
    public void verifyFactory() {
        ObjectFactoryAssert
                .assertObjectFactoryContract(new ResponsibleInformationFactory());
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.ResponsibleInformation#getResponsible()} and
     * {@link org.opensaas.jaudit.ResponsibleInformation#setResponsible(org.opensaas.jaudit.AuditSubject)}.
     * 
     * @throws Exception
     *             when an error occurs.
     */
    @Test
    public void testResponsible() throws Exception {
        AccessorAssert.assertGetterAndSetter(new ResponsibleInformation(),
                "setResponsible", AuditSubject.class, AUDIT_SUBJECT_FACTORY
                        .createEquivalent(), null, AUDIT_SUBJECT_FACTORY
                        .createUnique(), AUDIT_SUBJECT_FACTORY.createUnique(),
                AUDIT_SUBJECT_FACTORY.createUnique());
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.ResponsibleInformation#getResponsibleAddress()}
     * and
     * {@link org.opensaas.jaudit.ResponsibleInformation#setResponsibleAddress(java.lang.String)}.
     * 
     * @throws Exception
     *             when an error occurs.
     */
    @Test
    public void testResponsibleAddress() throws Exception {
        AccessorAssert.assertGetterAndSetter(new ResponsibleInformation(),
                "setResponsibleAddress", String.class, "", " ", null,
                "Texas Fight!", UUID.randomUUID().toString());
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.ResponsibleInformation#getResponsibleAgent()}
     * and
     * {@link org.opensaas.jaudit.ResponsibleInformation#setResponsibleAgent(java.lang.String)}.
     * 
     * @throws Exception
     *             when an error occurs.
     */
    @Test
    public void testResponsibleAgent() throws Exception {
        AccessorAssert.assertGetterAndSetter(new ResponsibleInformation(),
                "setResponsibleAgent", String.class, "", " ", null,
                "Texas Fight!", UUID.randomUUID().toString());
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.ResponsibleInformation#getCredentialsType()}
     * and
     * {@link org.opensaas.jaudit.ResponsibleInformation#setCredentialsType(java.lang.String)}.
     * 
     * @throws Exception
     *             when an error occurs.
     */
    @Test
    public void testCredentialsType() throws Exception {
        AccessorAssert.assertGetterAndSetter(new ResponsibleInformation(),
                "setCredentialsType", String.class, "", " ", null,
                "Texas Fight!", UUID.randomUUID().toString());
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.ResponsibleInformation#equals(java.lang.Object)}.
     */
    @Test
    public void testEqualsObject() {
        ObjectAssert.assertEqualsContract(RESPONSIBLE_FACTORY);
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.ResponsibleInformation#toString()}.
     */
    @Test
    public void testToString() {
        ObjectAssert.assertToStringContract(RESPONSIBLE_FACTORY);
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.ResponsibleInformation#hashCode()}.
     */
    @Test
    public void testHashCode() {
        ObjectAssert.assertHashCodeContract(RESPONSIBLE_FACTORY);
    }

    /**
     * Test that {@link org.opensaas.jaudit.ResponsibleInformation} is properly
     * {@link Serializable}.
     * 
     * @throws Exception
     *             when an error occurs.
     */
    @Test
    public void testSerializable() throws Exception {
        SerializableAssert.testSerialization(RESPONSIBLE_FACTORY);
    }

}
