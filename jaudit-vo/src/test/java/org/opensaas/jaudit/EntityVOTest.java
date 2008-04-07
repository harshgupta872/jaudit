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
 * Tests {@link EntityVO}.
 */
public class EntityVOTest {

    @Test
    public void testConstructor() {
        EntityVO eVO = new EntityVO();
        assert eVO != null;
        assert eVO instanceof AuditSubject;
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullId() {
        EntityVO eVO = new EntityVO();
        assert eVO.getSubjectId() == null;
        eVO.setSubjectId(null);
    }

    @Test
    public void testEmptyId() {
        EntityVO eVO = new EntityVO();
        eVO.setSubjectId("");
        assert eVO.getSubjectId().equals("");
    }

    @Test
    public void testId() {
        EntityVO eVO = new EntityVO();
        eVO.setSubjectId("a");
        assert eVO.getSubjectId().equals("a");
    }

    @Test
    public void testSubjectType() {
        EntityVO eVO = new EntityVO();
        eVO.setSubjectType("a");
        assert eVO.getSubjectType().equals("a");
    }
}
