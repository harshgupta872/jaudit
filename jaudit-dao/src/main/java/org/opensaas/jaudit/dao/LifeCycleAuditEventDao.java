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
package org.opensaas.jaudit.dao;

import org.opensaas.jaudit.LifeCycleAuditEvent;
import org.opensaas.jaudit.LifeCycleAuditEventMutable;

/**
 * Dao interface for working with {@link LifeCycleAuditEvent}s.
 * 
 * @param <T>
 *            the specific type of {@link LifeCycleAuditEventMutable} in use.
 */
public interface LifeCycleAuditEventDao<T extends LifeCycleAuditEventMutable>
        extends AuditEventDao<T> {
    // no added behavior
}
