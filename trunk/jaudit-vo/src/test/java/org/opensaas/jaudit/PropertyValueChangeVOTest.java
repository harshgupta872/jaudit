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
 * Tests {@link PropertyValueChangeVO}.
 */
public class PropertyValueChangeVOTest extends BeanTest<PropertyValueChangeVO> {

    static final ObjectFactory<PropertyValueChangeVO> FACTORY = new PropertyValueChangeVOFactory();

    /**
     * {@inheritDoc}
     */
    @Override
    protected ObjectFactory<PropertyValueChangeVO> getObjectFactory() {
        return FACTORY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Object[] getTestValues(final Class<?> valueType) {

        if (LifeCycleAuditEvent.class.isAssignableFrom(valueType)) {
            return new Object[] {
                    LifeCycleAuditEventVOTest.FACTORY.createUnique(),
                    LifeCycleAuditEventVOTest.FACTORY.createUnique(), null };
        }

        return super.getTestValues(valueType);

    }

    private static class PropertyValueChangeVOFactory implements
            ObjectFactory<PropertyValueChangeVO> {

        /**
         * {@inheritDoc}
         */
        public PropertyValueChangeVO createEquivalent() {
            final PropertyValueChangeVO pvc = new PropertyValueChangeVO();
            pvc.setId(STRING_FACTORY.createEquivalent());
            return pvc;
        }

        /**
         * {@inheritDoc}
         */
        public PropertyValueChangeVO createUnique() {
            final PropertyValueChangeVO pvc = new PropertyValueChangeVO();
            pvc.setId(STRING_FACTORY.createUnique());
            return pvc;
        }

    }

}
