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

import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.Collections;

import org.opensaas.jaudit.test.ObjectFactory;

/**
 * Tests {@link LifeCycleAuditEventVO}.
 */
public class LifeCycleAuditEventVOTest extends
        AuditEventVOTest<LifeCycleAuditEventVO> {

    static final ObjectFactory<LifeCycleAuditEventVO> FACTORY = newFactory(LifeCycleAuditEventVO.class);

    /**
     * {@inheritDoc}
     */
    @Override
    protected ObjectFactory<LifeCycleAuditEventVO> getObjectFactory() {
        return FACTORY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Object[] getTestValues(final PropertyDescriptor pd) {

        if (pd.getName().equals("propertyValueChanges")) {
            return new Object[] {
                    Collections.EMPTY_LIST,
                    Collections.singletonList(PropertyValueChangeVOTest.FACTORY
                            .createEquivalent()),
                    Arrays.asList(new Object[] {
                            PropertyValueChangeVOTest.FACTORY.createUnique(),
                            PropertyValueChangeVOTest.FACTORY.createUnique() }) };
        }

        return super.getTestValues(pd);

    }

}
