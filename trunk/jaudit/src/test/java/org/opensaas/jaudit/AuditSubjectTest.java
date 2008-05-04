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
import org.opensaas.jaudit.test.DefaultFactories;
import org.opensaas.jaudit.test.ObjectFactory;

/**
 * Test class for {@link org.opensaas.jaudit.AuditSubject}.
 */
public class AuditSubjectTest extends BeanTest<AuditSubject> {
    private static final DefaultFactories.StringFactory STRING_FACTORY = new DefaultFactories.StringFactory();

    private static final AuditSubjectFactory AUDIT_SUBJECT_FACTORY = new AuditSubjectFactory();

    /**
     * An {@link ObjectFactory} for {@link AuditSubject}s.
     */
    static class AuditSubjectFactory implements
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

    /**
     * {@inheritDoc}
     */
    @Override
    protected ObjectFactory<AuditSubject> getObjectFactory() {
        return AUDIT_SUBJECT_FACTORY;
    }

}
