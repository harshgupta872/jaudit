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
 * Default test for the bean methods of {@link SessionRecordVO}.
 */
public class SessionRecordVOTest extends BeanTest<SessionRecordVO> {

    static final ObjectFactory<SessionRecordVO> FACTORY = new SessionRecordVOFactory();

    /**
     * {@inheritDoc}
     */
    @Override
    protected ObjectFactory<SessionRecordVO> getObjectFactory() {
        return FACTORY;
    }

    /**
     * An {@link ObjectFactory} for {@link TransactionRecordVO}s.
     */
    private static class SessionRecordVOFactory implements
            ObjectFactory<SessionRecordVO> {

        /**
         * {@inheritDoc}
         */
        public SessionRecordVO createEquivalent() {
            final SessionRecordVO sr = new SessionRecordVO();

            sr.setSessionId(STRING_FACTORY.createEquivalent());

            return sr;
        }

        /**
         * {@inheritDoc}
         */
        public SessionRecordVO createUnique() {
            final SessionRecordVO sr = new SessionRecordVO();

            sr.setSessionId(STRING_FACTORY.createUnique());

            return sr;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Object[] getTestValues(final Class<?> valueType) {

        if (AuditSubject.class.isAssignableFrom(valueType)) {
            return new Object[] { new AuditSubject(), null };
        }

        if (ResponsibleInformation.class.isAssignableFrom(valueType)) {
            return new Object[] { new ResponsibleInformation(), null };
        }

        return super.getTestValues(valueType);

    }

}
