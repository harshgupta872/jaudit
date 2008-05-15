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

import org.opensaas.jaudit.AuditEvent;
import org.opensaas.jaudit.AuditEventMutable;
import org.opensaas.jaudit.dao.AuditEventDao;

/**
 * Default hibernate implementation of {@link AuditEventDao}.
 * 
 * @param <T>
 *            the specific type of {@link AuditEvent} in use.
 */
public class AuditEventDaoHibernate<T extends AuditEventMutable> extends
        GenericDaoHibernate<T, String> implements AuditEventDao<T> {

    /**
     * Required constructor.
     * 
     * @param type
     *            the type we are managing.
     */
    public AuditEventDaoHibernate(final Class<T> type) {
        super(type);
    }
}
