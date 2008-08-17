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

import java.util.Date;

import org.opensaas.jaudit.TransactionRecord;
import org.opensaas.jaudit.TransactionRecordMutable;
import org.opensaas.jaudit.TransactionCompletionStatus;
import org.opensaas.jaudit.dao.TransactionRecordDao;

/**
 * Default hibernate implementation of {@link TransactionRecordDao}.
 * 
 * @param <T>
 *            the specific type of {@link TransactionRecordMutable} in use.
 */
public class TransactionRecordDaoHibernate<T extends TransactionRecordMutable>
        extends GenericDaoHibernate<T, String> implements
        TransactionRecordDao<T> {

    /**
     * Required constructor.
     * 
     * @param type
     *            the type we are managing.
     */
    public TransactionRecordDaoHibernate(final Class<T> type) {
        super(type);
    }

    /**
     * {@inheritDoc}
     */
    public TransactionRecord transactionEnded(
            final TransactionRecord transactionRecord,
            final TransactionCompletionStatus transactionStatus, final Date endedTs) {
        if (transactionRecord == null) {
            throw new IllegalArgumentException(
                    "Transaction record must not be null.");
        }

        final TransactionRecordMutable tr = read(transactionRecord.getId());
        if (tr == null) {
            throw new IllegalArgumentException(
                    "Transaction record does not exist in persistence.");
        }

        tr.setEndedTs(endedTs);
        tr.setTransactionCompletionStatus(transactionStatus);

        getMySession().save(tr);
        return tr;
    }

}
