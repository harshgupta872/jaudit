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
 * A transaction record is a record of a transaction created within the context
 * of a {@link SessionRecord}. Most often a transaction will have a one to one
 * mapping with an actual database backed transaction.
 */
public interface TransactionRecord {

    /**
     * Returns the globally unique id of this transaction record. Implementors
     * and creators of a TransactionRecord should take care to try to ensure
     * uniqueness of this ID.
     * 
     * Required.
     * 
     * TODO: Document some strategies for GUID/Unique id generation. Should
     * include the max length and content of the id. Maybe UUID?
     * 
     * @return a globally unique id for this transaction record.
     */
    String getId();

    /**
     * Returns the id of the transaction. This transaction id should be globally
     * unique if possible.
     * 
     * Required. Length is less than or equal to 256.
     * 
     * @return the id of the transaction.
     */
    String getTransactionId();

    /**
     * Returns the associated {@link SessionRecord} for this transaction.
     * 
     * Optional.
     * 
     * @return the associated SessionRecord.
     */
    SessionRecord getSessionRecord();

    /**
     * Returns the time at which this transaction was started.
     * 
     * Optional.
     * 
     * @return Date at which this session started.
     */
    Date getStartedTs();

    /**
     * Returns the time at which this transaction was ended.
     * 
     * Optional.
     * 
     * @return Date at which this transaction ended.
     */
    Date getEndedTs();

}
