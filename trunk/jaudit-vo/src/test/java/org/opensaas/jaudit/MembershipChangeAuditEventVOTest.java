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
 * Tests {@link MembershipChangeAuditEventVO}.
 */
public class MembershipChangeAuditEventVOTest {

    @Test
    public void testConstructor() {
        MembershipChangeAuditEventVO vo = new MembershipChangeAuditEventVO();
        assert vo != null;
        assert vo instanceof AuditEvent;
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullId() {
        MembershipChangeAuditEventVO vo = new MembershipChangeAuditEventVO();
        assert vo.getId() == null;
        vo.setId(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyId() {
        MembershipChangeAuditEventVO vo = new MembershipChangeAuditEventVO();
        vo.setId("");
    }

    @Test
    public void testId() {
        MembershipChangeAuditEventVO vo = new MembershipChangeAuditEventVO();
        vo.setId("a");
        assert vo.getId().equals("a");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullMembershipChangeEventType() {
        MembershipChangeAuditEventVO vo = new MembershipChangeAuditEventVO();
        assert vo.getMembershipChangeEventType() == null;
        vo.setMembershipChangeEventType(null);
    }

    @Test
    public void testMembershipChangeEventType() {
        MembershipChangeAuditEventVO vo = new MembershipChangeAuditEventVO();
        assert vo.getMembershipChangeEventType() == null;
        vo.setMembershipChangeEventType(MembershipChangeType.ADDED);
        assert vo.getMembershipChangeEventType().equals(MembershipChangeType.ADDED);
    }
}
