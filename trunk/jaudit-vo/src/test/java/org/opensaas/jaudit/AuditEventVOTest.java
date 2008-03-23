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
 * Tests {@link AuditEventVO}.
 */
public class AuditEventVOTest {

    @Test
    public void testConstructor() {
        AuditEventVO aeVO = new AuditEventVO();
        assert aeVO != null;
        assert aeVO instanceof AuditEvent;
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullId() {
        AuditEventVO aeVO = new AuditEventVO();
        assert aeVO.getId() == null;
        aeVO.setId(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyId() {
        AuditEventVO aeVO = new AuditEventVO();
        aeVO.setId("");
    }

    @Test
    public void testId() {
        AuditEventVO aeVO = new AuditEventVO();
        aeVO.setId("a");
        assert aeVO.getId().equals("a");
    }

}
