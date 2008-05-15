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

/**
 * This interface allows basic attributes of an {@link AuditEvent} to be
 * changed.
 */
public interface AuditEventMutable extends AuditEvent {

    /**
     * Update the globally unique id of this audit record.
     * 
     * @param id
     *            a globally unique id for this audit log.
     * @see AuditEvent#getId()
     */
    void setId(String id);

    /**
     * update the time at which this event occurred.
     * 
     * @param ts
     *            Date at which this event took place.
     * @see AuditEvent#getTs()
     */
    void setTs(Date ts);

    /**
     * Update the {@link TransactionRecord} associated with this audit event.
     * 
     * @param record
     *            the transaction record associated with this audit event.
     * @see AuditEvent#getTransactionRecord()
     */
    void setTransactionRecord(TransactionRecord record);

    /**
     * Updates the {@link SessionRecord} associated with this audit event.
     * 
     * @param record
     *            the session record associated with this audit event.
     * @see AuditEvent#getSessionRecord()
     */
    void setSessionRecord(SessionRecord record);

    /**
     * Updates the target entity of this event.
     * 
     * @param subject
     *            the entity target of this event.
     * @see AuditEvent#getTarget()
     */
    void setTarget(AuditSubject subject);

    /**
     * Updates the non-localized description of what occurred.
     * 
     * @param description
     *            a short description of what happened.
     * @see AuditEvent#getDescription()
     */
    void setDescription(String description);

}
