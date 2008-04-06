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

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * Tests {@link LifeCycleAuditEventVO}.
 */
public class LifeCycleAuditEventVOTest {

    @Test
    public void testConstructor() {
        LifeCycleAuditEventVO lcaeVO = new LifeCycleAuditEventVO();
        assert lcaeVO != null;
        assert lcaeVO instanceof LifeCycleAuditEvent;
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullId() {
        LifeCycleAuditEventVO lcaeVO = new LifeCycleAuditEventVO();
        assert lcaeVO.getId() == null;
        lcaeVO.setId(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyId() {
        LifeCycleAuditEventVO lcaeVO = new LifeCycleAuditEventVO();
        lcaeVO.setId("");
    }

    @Test
    public void testId() {
        LifeCycleAuditEventVO lcaeVO = new LifeCycleAuditEventVO();
        lcaeVO.setId("a");
        assert lcaeVO.getId().equals("a");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullLifeCycleEventType() {
        LifeCycleAuditEventVO lcaeVO = new LifeCycleAuditEventVO();
        assert lcaeVO.getLifeCycleEventType() == null;
        lcaeVO.setLifeCycleEventType(null);
    }

    @Test
    public void testLifeCycleEventType() {
        LifeCycleAuditEventVO lcaeVO = new LifeCycleAuditEventVO();
        lcaeVO.setLifeCycleEventType(LifeCycleType.CREATE);
        assert lcaeVO.getLifeCycleEventType().equals(LifeCycleType.CREATE);
    }

    @Test
    public void testDefaultEmptyPropertyValueChanges() {
        LifeCycleAuditEventVO lcaeVO = new LifeCycleAuditEventVO();
        assert lcaeVO.getPropertyValueChanges().isEmpty();
    }

    @Test
    public void testPropertyValueChanges() {
        LifeCycleAuditEventVO lcaeVO = new LifeCycleAuditEventVO();
        List<PropertyValueChange> propertyValueChanges = new ArrayList<PropertyValueChange>();
        PropertyValueChange pvc = new PropertyValueChangeVO();
        propertyValueChanges.add(pvc);
        lcaeVO.setPropertyValueChanges(propertyValueChanges);
        assert lcaeVO.getPropertyValueChanges().equals(propertyValueChanges);
        LifeCycleAuditEvent lcae = lcaeVO.getPropertyValueChanges().iterator().next().getLifeCycleAuditEvent();
        assert lcaeVO.equals(lcae);
    }
    
    @Test
    public void testNullPropertyValueChanges() {
        LifeCycleAuditEventVO lcaeVO = new LifeCycleAuditEventVO();
        List<PropertyValueChange> propertyValueChanges = new ArrayList<PropertyValueChange>();
        PropertyValueChange pvc = new PropertyValueChangeVO();
        propertyValueChanges.add(pvc);
        lcaeVO.setPropertyValueChanges(propertyValueChanges);
        assert !lcaeVO.getPropertyValueChanges().isEmpty();
        
        lcaeVO.setPropertyValueChanges(null);
        assert lcaeVO.getPropertyValueChanges().isEmpty();
    }
}

