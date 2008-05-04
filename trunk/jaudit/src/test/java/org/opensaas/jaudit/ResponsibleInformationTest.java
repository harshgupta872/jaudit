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

import org.opensaas.jaudit.AuditSubjectTest.AuditSubjectFactory;
import org.opensaas.jaudit.test.BeanTest;
import org.opensaas.jaudit.test.DefaultFactories;
import org.opensaas.jaudit.test.ObjectFactory;

/**
 * Test method for {@link org.opensaas.jaudit.ResponsibleInformation}.
 */
public class ResponsibleInformationTest extends
        BeanTest<ResponsibleInformation> {

    private static final DefaultFactories.StringFactory STRING_FACTORY = new DefaultFactories.StringFactory();

    private static final AuditSubjectFactory AUDIT_SUBJECT_FACTORY = new AuditSubjectFactory();

    private static final ResponsibleInformationFactory RESPONSIBLE_FACTORY = new ResponsibleInformationFactory();

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
            } else {
                // not null by default, so force it for a more complete test
                info.setResponsible(null);
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

    /**
     * {@inheritDoc}
     */
    @Override
    protected Object[] getTestValues(final Class<?> valueType) {

        if (AuditSubject.class.isAssignableFrom(valueType)) {
            return new Object[] { AUDIT_SUBJECT_FACTORY.createUnique(), null };
        }
        return super.getTestValues(valueType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ObjectFactory<ResponsibleInformation> getObjectFactory() {
        return RESPONSIBLE_FACTORY;
    }

}
