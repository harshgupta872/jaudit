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

import org.opensaas.jaudit.test.ObjectFactory;

/**
 * Tests {@link ConsumptionAuditEventVO}.
 */
public class ConsumptionAuditEventVOTest extends
        AuditEventVOTest<ConsumptionAuditEventVO> {

    static final ObjectFactory<ConsumptionAuditEventVO> FACTORY = newFactory(ConsumptionAuditEventVO.class);

    /**
     * {@inheritDoc}
     */
    @Override
    protected ObjectFactory<ConsumptionAuditEventVO> getObjectFactory() {
        return FACTORY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Object[] getTestValues(final PropertyDescriptor pd) {

        if (pd.getName().equals("scale")) {
            return new Object[] { 0, Integer.MAX_VALUE, null };
        }

        return super.getTestValues(pd);

    }

}
