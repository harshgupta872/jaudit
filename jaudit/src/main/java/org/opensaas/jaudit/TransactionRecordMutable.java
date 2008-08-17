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
 * The mutable interface to compliment {@link TransactionRecord}.
 */
public interface TransactionRecordMutable extends TransactionRecord {

    /**
     * Sets the required id of this transaction record.
     * 
     * @see #getId()
     * 
     * @param id
     *            required id to set.
     * 
     */
    void setId(final String id);

    /**
     * Sets the required transaction id.
     * 
     * @see #getTransactionId()
     * 
     * @param transactionId
     *            transaction id to set.
     */
    void setTransactionId(final String transactionId);

    /**
     * Sets the optional SessionRecord to associate with this record.
     * 
     * @see #getSessionRecord()
     * 
     * @param sessionRecord
     *            the session record to set.
     */
    void setSessionRecord(final SessionRecord sessionRecord);

    /**
     * Sets the optional started time stamp for this record.
     * 
     * @see #getStartedTs();
     * 
     * @param startedTs
     *            the time to set.
     */
    void setStartedTs(final Date startedTs);

    /**
     * Sets the optional ended time stamp for this record.
     * 
     * @see #getEndedTs()
     * 
     * @param endedTs
     *            the time to set.
     */
    void setEndedTs(final Date endedTs);

    /**
     * Sets the optional transaction status for this record.
     * 
     * @see #getTransactionCompletionStatus()
     * @param transactionStatus
     */
    void setTransactionCompletionStatus(final TransactionCompletionStatus transactionStatus);

}
