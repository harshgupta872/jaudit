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
package org.opensaas.jaudit.dao.hibernate;

import org.opensaas.jaudit.LifeCycleAuditEvent;
import org.opensaas.jaudit.LifeCycleAuditEventMutable;
import org.opensaas.jaudit.dao.LifeCycleAuditEventDao;

/**
 * Default hibernate implementation of {@link LifeCycleAuditEventDao}.
 * 
 * @param <T>
 *            the specific type of {@link LifeCycleAuditEvent} in use.
 */
public class LifeCycleAuditEventDaoHibernate<T extends LifeCycleAuditEventMutable>
        extends AuditEventDaoHibernate<T> implements LifeCycleAuditEventDao<T> {

    /**
     * Required constructor.
     * 
     * @param type
     *            the type we are managing.
     */
    public LifeCycleAuditEventDaoHibernate(final Class<T> type) {
        super(type);
    }
}
