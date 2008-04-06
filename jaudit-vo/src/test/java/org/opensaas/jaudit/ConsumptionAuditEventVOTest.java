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

import org.junit.Test;

/**
 * Tests {@link ConsumptionAuditEventVO}.
 */
public class ConsumptionAuditEventVOTest {

    @Test
    public void testConstructor() {
        ConsumptionAuditEventVO ceVO = new ConsumptionAuditEventVO();
        assert ceVO != null;
        assert ceVO instanceof ConsumptionAuditEvent;
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullId() {
        ConsumptionAuditEventVO ceVO = new ConsumptionAuditEventVO();
        assert ceVO.getId() == null;
        ceVO.setId(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyId() {
        ConsumptionAuditEventVO ceVO = new ConsumptionAuditEventVO();
        ceVO.setId("");
    }

    @Test
    public void testId() {
        ConsumptionAuditEventVO ceVO = new ConsumptionAuditEventVO();
        ceVO.setId("a");
        assert ceVO.getId().equals("a");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullAmountConsumed() {
        ConsumptionAuditEventVO ceVO = new ConsumptionAuditEventVO();
        assert ceVO.getAmountConsumed() == null;
        ceVO.setAmountConsumed(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeAmountConsumed() {
        ConsumptionAuditEventVO ceVO = new ConsumptionAuditEventVO();
        assert ceVO.getAmountConsumed() == null;
        ceVO.setAmountConsumed(-1.0);
    }

    @Test
    public void testAmountConsumed() {
        ConsumptionAuditEventVO ceVO = new ConsumptionAuditEventVO();
        ceVO.setAmountConsumed(1.0);
        assert ceVO.getAmountConsumed().equals(1.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeScale() {
        ConsumptionAuditEventVO ceVO = new ConsumptionAuditEventVO();
        assert ceVO.getScale() == 0;
        ceVO.setScale(-1);
    }

    @Test
    public void testScale() {
        ConsumptionAuditEventVO ceVO = new ConsumptionAuditEventVO();
        ceVO.setScale(1);
        assert ceVO.getScale().equals(1);
    }
}
