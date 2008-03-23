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

import org.opensaas.jaudit.SessionRecord;
import org.opensaas.jaudit.TransactionRecord;

/**
 * The default implementation and persistence mapping of a
 * {@link TransactionRecord}.
 * 
 */
@Entity
@Table(name="transaction_records")
public class TransactionRecordVO implements TransactionRecord {

    private Date _endedTs;

    private CharSequence _id;

    private SessionRecord _sessionRecord;

    private Date _startedTs;

    private CharSequence _transactionId;

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
    public CharSequence getId() {
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
    public void setId(CharSequence id) {
        if (id == null) {
            throw new IllegalArgumentException("Id must not be null.");
        }
        _id = id;
    }

    /**
     * {@inheritDoc}
     */
    @ManyToOne(targetEntity = SessionRecordVO.class, optional=false)
    @JoinColumn(name = "session_record_id", nullable = false)
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
        if (sessionRecord == null) {
            throw new IllegalArgumentException(
                    "Session record must not be null.");
        }
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
    @Column(name = "transaction_id", nullable = false, length=128)
    public CharSequence getTransactionId() {
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
    public void setTransactionId(CharSequence transactionId) {
        if (transactionId == null) {
            throw new IllegalArgumentException(
                    "Transaction id must not be null.");
        }
        _transactionId = transactionId;
    }

}
