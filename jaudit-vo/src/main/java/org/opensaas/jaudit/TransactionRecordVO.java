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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The default implementation and persistence mapping of a
 * {@link TransactionRecord}.
 * 
 */
@Entity
@Table(name = "transaction_records")
public class TransactionRecordVO implements TransactionRecordMutable {

    private Date _endedTs;

    private String _id;

    private SessionRecord _sessionRecord;

    private Date _startedTs;

    private String _transactionId;

    /**
     * {@inheritDoc}
     */
    @Column(name = "ended_ts")
    public Date getEndedTs() {
        return _endedTs;
    }

    /**
     * Sets the time the transaction ended.
     * 
     * @see #getEndedTs()
     * 
     * @param endedTs
     *            time transaction ended.
     */
    public void setEndedTs(Date endedTs) {
        _endedTs = endedTs;
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
     *            the id to set.
     */
    public void setId(String id) {
        _id = id;
    }

    /**
     * {@inheritDoc}
     */
    @ManyToOne(targetEntity = SessionRecordVO.class, optional = true)
    @JoinColumn(name = "session_record", nullable = true)
    public SessionRecord getSessionRecord() {
        return _sessionRecord;
    }

    /**
     * Sets the required session record to associate with this transaction
     * record.
     * 
     * @see #getSessionRecord()
     * 
     * @param sessionRecord
     *            required session record.
     */
    public void setSessionRecord(SessionRecord sessionRecord) {
        _sessionRecord = sessionRecord;
    }

    /**
     * {@inheritDoc}
     */
    @Column(name = "started_ts")
    public Date getStartedTs() {
        return _startedTs;
    }

    /**
     * Sets the optional time that this transaction was started.
     * 
     * @see #getStartedTs()
     * 
     * @param startedTs
     *            the optional started date.
     */
    public void setStartedTs(Date startedTs) {
        _startedTs = startedTs;
    }

    /**
     * {@inheritDoc}
     */
    @Column(name = "transaction_id", nullable = false, length = 256, unique = true)
    public String getTransactionId() {
        return _transactionId;
    }

    /**
     * Sets the required transaction id.
     * 
     * @see #getTransactionId()
     * 
     * @param transactionId
     *            the required id of the transaction.
     */
    public void setTransactionId(String transactionId) {
        _transactionId = transactionId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(255);
        sb.append("TransactionRecord[");
        sb.append("transactionId=");
        sb.append(_transactionId);
        sb.append(", startedTs=");
        sb.append(_startedTs);
        sb.append(", endedTs=");
        sb.append(_endedTs);
        sb.append(", id=");
        sb.append(_id);
        if (_sessionRecord != null) {
            sb.append(", sessionRecordId=");
            sb.append(_sessionRecord.getId());
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null || !(o instanceof TransactionRecord)) {
            return false;
        }

        final TransactionRecord otherTr = (TransactionRecord) o;
        final String otherId = otherTr.getTransactionId();
        if (otherId == null && _transactionId == null) {
            return true;
        }

        if (_transactionId == null) {
            return false;
        }

        return _transactionId.equals(otherId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        if (_transactionId == null) {
            return 0;
        }
        return _transactionId.hashCode();
    }

}
