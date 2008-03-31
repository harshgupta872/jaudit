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
 * Tests {@link ConsumptionEventVO}.
 */
public class ConsumptionEventVOTest {

    @Test
    public void testConstructor() {
        ConsumptionEventVO ceVO = new ConsumptionEventVO();
        assert ceVO != null;
        assert ceVO instanceof ConsumptionEvent;
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullId() {
        ConsumptionEventVO ceVO = new ConsumptionEventVO();
        assert ceVO.getId() == null;
        ceVO.setId(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyId() {
        ConsumptionEventVO ceVO = new ConsumptionEventVO();
        ceVO.setId("");
    }

    @Test
    public void testId() {
        ConsumptionEventVO ceVO = new ConsumptionEventVO();
        ceVO.setId("a");
        assert ceVO.getId().equals("a");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullAmountConsumed() {
        ConsumptionEventVO ceVO = new ConsumptionEventVO();
        assert ceVO.getAmountConsumed() == null;
        ceVO.setAmountConsumed(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeAmountConsumed() {
        ConsumptionEventVO ceVO = new ConsumptionEventVO();
        assert ceVO.getAmountConsumed() == null;
        ceVO.setAmountConsumed(-1.0);
    }

    @Test
    public void testAmountConsumed() {
        ConsumptionEventVO ceVO = new ConsumptionEventVO();
        ceVO.setAmountConsumed(1.0);
        assert ceVO.getAmountConsumed().equals(1.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeScale() {
        ConsumptionEventVO ceVO = new ConsumptionEventVO();
        assert ceVO.getScale() == null;
        ceVO.setScale(-1);
    }

    @Test
    public void testScale() {
        ConsumptionEventVO ceVO = new ConsumptionEventVO();
        ceVO.setScale(1);
        assert ceVO.getScale().equals(1);
    }
}
