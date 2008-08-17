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
package org.opensaas.jaudit.service.spring;

import java.util.Date;
import java.util.logging.Logger;

import org.opensaas.jaudit.TransactionRecord;
import org.opensaas.jaudit.service.AuditService;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class TransactionAuditor implements ApplicationListener {

    static private final Logger LOGGER = Logger
            .getLogger(TransactionAuditor.class.getName());

    private static final ThreadLocal<Object> AUDITING_LOCK = new ThreadLocal<Object>();

    private static final Object LOCK = new Object();

    private AuditService _auditService;

    /**
     * {@inheritDoc}
     */
    public void onApplicationEvent(final ApplicationEvent event) {
        if (NewTransactionEvent.class.isAssignableFrom(event.getClass())) {
            final NewTransactionEvent te = (NewTransactionEvent) event;

            final TransactionStatus ts = te.getTransactionStatus();

            if (ts != null && ts.isNewTransaction()) {
                final TransactionRecord tr = _auditService
                        .createTransactionRecord(Integer.toHexString(ts
                                .hashCode()), te.getSessionRecord());

                final TransactionStatusSynchronization tss = new TransactionStatusSynchronization(
                        tr);
                TransactionSynchronizationManager.registerSynchronization(tss);

            }

        }

    }

    public void setAuditService(AuditService auditService) {
        _auditService = auditService;
    }

    private class TransactionStatusSynchronization implements
            TransactionSynchronization {

        private final TransactionRecord _transactionRecord;

        /**
         * Default required constructor.
         * 
         * @param transactionRecord
         */
        public TransactionStatusSynchronization(
                final TransactionRecord transactionRecord) {

            if (transactionRecord == null) {
                throw new IllegalArgumentException(
                        "Transaction must not be null.");
            }
            _transactionRecord = transactionRecord;

        }

        /**
         * {@inheritDoc}
         */
        public void afterCommit() {
            // nothing

        }

        /**
         * {@inheritDoc}
         */
        public void afterCompletion(final int status) {

            // let's make sure we're not in an infinite loop.

            final Object auditing = AUDITING_LOCK.get();

            if (auditing != null) {
                LOGGER.fine("Alreadying logging for this transaction.");
                return;
            }

            AUDITING_LOCK.set(LOCK);
            try {

                org.opensaas.jaudit.TransactionCompletionStatus tsCode = org.opensaas.jaudit.TransactionCompletionStatus.STATUS_UNKNOWN;
                switch (status) {
                case STATUS_COMMITTED:
                    tsCode = org.opensaas.jaudit.TransactionCompletionStatus.STATUS_COMMITTED;
                break;
                case STATUS_ROLLED_BACK:
                    tsCode = org.opensaas.jaudit.TransactionCompletionStatus.STATUS_COMMITTED;
                break;
                }

                _auditService.updateTransactionEnded(_transactionRecord,
                        tsCode, new Date());
            } finally {
                AUDITING_LOCK.remove();
            }

        }

        /**
         * {@inheritDoc}
         */
        public void beforeCommit(boolean readOnly) {
            // nothing

        }

        /**
         * {@inheritDoc}
         */
        public void beforeCompletion() {
            // nothing

        }

        /**
         * {@inheritDoc}
         */
        public void resume() {
            // nothing

        }

        /**
         * {@inheritDoc}
         */
        public void suspend() {
            // nothing

        }
    }

}
