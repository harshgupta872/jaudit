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

import java.util.Date;
import java.util.UUID;

/**
 * This is the basic component for the jaudit libraries. An audit event is the
 * record of an event which is recorded for business requirements such as:
 * <ul>
 * <li>compliance with established policies and operational procedure</li>
 * <li>information to determine usage of metered resources</li>
 * <li>correlation of business events for causal relationship discovery</li>
 * <li>record of historical information.</li>
 * </ul>
 * This interface defines the most basic of attributes that entail an
 * AuditEvent.
 */
public interface AuditEvent {

    /**
     * Returns the globally unique id of this audit record. Implementors and
     * creators of an AuditEvent should take care to try to ensure uniqueness of
     * this ID.
     * 
     * The default implementation of ids provided by the jaudit project are
     * based on Java's default {@link UUID#randomUUID()}.
     * 
     * Required.
     * 
     * @return a globally unique id for this audit log.
     */
    String getId();

    /**
     * Returns the time at which this event occurred and should be accounted.
     * 
     * Required.
     * 
     * @return Date at which this event took place.
     */
    Date getTs();

    /**
     * Returns the {@link TransactionRecord} associated with this audit event.
     * 
     * Optional.
     * 
     * @return the transaction record associated with this audit event.
     */
    TransactionRecord getTransactionRecord();

    /**
     * Returns the {@link SessionRecord} associated with this audit event.
     * 
     * Optional.
     * 
     * @return the session record associated with this audit event.
     */
    SessionRecord getSessionRecord();

    /**
     * Returns the target entity of this event.
     * 
     * Optional.
     * 
     * @return the entity target of this event.
     */
    AuditSubject getTarget();

    /**
     * Gets a non-localized description of what occurred.
     * 
     * Optional
     * 
     * @return a short description of what happened.
     */
    String getDescription();

}
