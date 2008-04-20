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

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 * This is a simple implementation for the {@link AuditEvent} interface.
 * 
 */
@MappedSuperclass
public class AuditEventVO implements AuditEvent {

    private String _id;

    private AuditSubject _target;

    private TransactionRecord _transactionRecord;

    private Date _ts;

    private String _description;

    private SessionRecord _sessionRecord;

    /**
     * Defalt empty constructor.
     */
    public AuditEventVO() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Id
    public String getId() {
        return _id;
    }

    /**
     * Sets the required id.
     * 
     * @see #getId()
     * 
     * @param id
     *            required id to set.
     */
    public void setId(String id) {
        if (id == null || id.length() == 0) {
            throw new IllegalArgumentException("Id must not be null.");
        }
        _id = id;
    }

    /**
     * {@inheritDoc}
     */
    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name = "subjectId", column = @Column(name = "target_subject_id")),
            @AttributeOverride(name = "subjectType", column = @Column(name = "target_subject_type")),
            @AttributeOverride(name = "subjectDiscriminator", column = @Column(name = "target_subject_discriminator")) })
    public AuditSubject getTarget() {
        return _target;
    }

    /**
     * Sets the target.
     * 
     * @param target
     *            Subject target to set.
     */
    public void setTarget(AuditSubject target) {
        _target = target;
    }

    /**
     * {@inheritDoc}
     */
    @ManyToOne(targetEntity = TransactionRecordVO.class)
    @JoinColumn(name = "transaction_record")
    public TransactionRecord getTransactionRecord() {
        return _transactionRecord;
    }

    /**
     * Sets the associated transaction.
     * 
     * @see #getTransactionRecord()
     * 
     * @param transaction
     *            transaction record to set.
     */
    public void setTransactionRecord(TransactionRecord transaction) {
        _transactionRecord = transaction;
    }

    /**
     * {@inheritDoc}
     */
    @Column(name = "ts", nullable = false)
    public Date getTs() {
        return _ts;
    }

    /**
     * Sets the required date on this audit event.
     * 
     * @see #getTs()
     * 
     * @param ts
     *            the required date.
     */
    public void setTs(Date ts) {
        if (ts == null) {
            throw new IllegalArgumentException("Date must not be null.");
        }
        _ts = ts;
    }

    /**
     * {@inheritDoc}
     */
    @Column(name = "description")
    public String getDescription() {
        return _description;
    }

    /**
     * Sets the optional description.
     * 
     * @see #getDescription()
     * 
     * @param description
     */
    public void setDescription(String description) {
        _description = description;
    }

    /**
     * {@inheritDoc}
     */
    @ManyToOne(targetEntity = SessionRecordVO.class)
    @JoinColumn(name = "session_record")
    public SessionRecord getSessionRecord() {
        return _sessionRecord;
    }

    /**
     * Sets the optional session record.
     * 
     * @see #getSessionRecord()
     * 
     * @param sessionRecord
     *            the sessionRecord to set
     */
    public void setSessionRecord(SessionRecord sessionRecord) {
        _sessionRecord = sessionRecord;
    }

}
