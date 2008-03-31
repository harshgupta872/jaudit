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
 * Tests {@link BusinessBusinessAuditEventVO}.
 */
public class BusinessAuditEventVOTest {

    @Test
    public void testConstructor() {
        BusinessAuditEventVO baeVO = new BusinessAuditEventVO();
        assert baeVO != null;
        assert baeVO instanceof AuditEvent;
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullId() {
        BusinessAuditEventVO baeVO = new BusinessAuditEventVO();
        assert baeVO.getId() == null;
        baeVO.setId(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyId() {
        BusinessAuditEventVO baeVO = new BusinessAuditEventVO();
        baeVO.setId("");
    }

    @Test
    public void testId() {
        BusinessAuditEventVO baeVO = new BusinessAuditEventVO();
        baeVO.setId("a");
        assert baeVO.getId().equals("a");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullBusinessClass() {
        BusinessAuditEventVO baeVO = new BusinessAuditEventVO();
        assert baeVO.getBusinessClass() == null;
        baeVO.setBusinessClass(null);
    }

    @Test
    public void testBusinessClass() {
        BusinessAuditEventVO baeVO = new BusinessAuditEventVO();
        baeVO.setBusinessClass("a");
        assert baeVO.getBusinessClass().equals("a");
    }
}
