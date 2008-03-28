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

    private TransactionRecord _transaction;

    private Date _ts;

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
            @AttributeOverride(name = "id", column = @Column(name = "target_subject_id")),
            @AttributeOverride(name = "subjectType", column = @Column(name = "target_subject_type")) })
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
    @ManyToOne(targetEntity = TransactionRecordVO.class, optional = false)
    @JoinColumn(name = "transaction_id", nullable = false)
    public TransactionRecord getTransactionRecord() {
        return _transaction;
    }

    /**
     * Sets the associated transaction.
     * 
     * @see #getTransactionRecord()
     * 
     * @param transaction
     *            non-null transaction record to set.
     */
    public void setTransactionRecord(TransactionRecord transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException(
                    "Transaction record must not be null.");
        }
        _transaction = transaction;
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
     * @param ts
     *            the required date.
     */
    public void setTs(Date ts) {
        if (ts == null) {
            throw new IllegalArgumentException("Date must not be null.");
        }
        _ts = ts;
    }

}
