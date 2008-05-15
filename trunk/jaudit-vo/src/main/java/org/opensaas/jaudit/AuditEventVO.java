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
public class AuditEventVO implements AuditEventMutable {

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
    public void setId(final String id) {
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
    public void setTarget(final AuditSubject target) {
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
    public void setTransactionRecord(final TransactionRecord transaction) {
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
    public void setTs(final Date ts) {
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
    public void setDescription(final String description) {
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
    public void setSessionRecord(final SessionRecord sessionRecord) {
        _sessionRecord = sessionRecord;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        if (_id == null) {
            return super.hashCode();
        }
        return _id.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (o == null || !(getClass().equals(o.getClass()))) {
            return false;
        }

        final AuditEvent ae = (AuditEvent) o;

        if (_id == null || ae.getId() == null) {
            return false;
        }

        return _id.equals(ae.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        final StringBuilder buff = new StringBuilder(255);
        buff.append(getClass().getSimpleName());
        buff.append("[id=");
        buff.append(_id);
        buff.append(", ts=");
        buff.append(_ts);
        buff.append(", target=");
        buff.append(_target);
        if (_sessionRecord != null) {
            buff.append(", sessionRecord=");
            buff.append(_sessionRecord.getSessionId());
        }
        if (_transactionRecord != null) {
            buff.append(", transactionRecord=");
            buff.append(_transactionRecord.getTransactionId());
        }

        return buff.toString();

    }

}
