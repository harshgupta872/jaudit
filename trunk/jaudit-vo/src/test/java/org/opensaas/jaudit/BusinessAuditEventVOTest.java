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

import org.opensaas.jaudit.test.ObjectFactory;

/**
 * Tests {@link BusinessBusinessAuditEventVO}.
 */
public class BusinessAuditEventVOTest extends
        AuditEventVOTest<BusinessAuditEventVO> {

    static final ObjectFactory<BusinessAuditEventVO> FACTORY = newFactory(BusinessAuditEventVO.class);

    /**
     * {@inheritDoc}
     */
    @Override
    protected ObjectFactory<BusinessAuditEventVO> getObjectFactory() {
        return FACTORY;
    }

}
