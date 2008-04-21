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
package org.opensaas.exampleapp.manager;

import org.appfuse.service.GenericManager;
import org.opensaas.exampleapp.model.FooBar;
import org.opensaas.jaudit.AuditTargetId;
import org.opensaas.jaudit.LifeCycleAudit;
import org.opensaas.jaudit.LifeCycleType;

public interface FooBarManager extends GenericManager<FooBar, Long> {

    /**
     * {@inheritDoc}
     */
    @LifeCycleAudit(type = LifeCycleType.DELETE)
    void remove(@AuditTargetId(targetTypeClass = FooBar.class)
    Long id);

}
