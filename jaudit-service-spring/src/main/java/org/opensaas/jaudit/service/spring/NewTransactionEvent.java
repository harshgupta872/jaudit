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

import org.opensaas.jaudit.SessionRecord;
import org.springframework.context.ApplicationEvent;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;

public class NewTransactionEvent extends ApplicationEvent {

    /**
     * Generated Serial Id.
     */
    private static final long serialVersionUID = -2135765089839814231L;

    private transient PlatformTransactionManager _platformTransactionManager;

    private TransactionStatus _transactionStatus;

    private SessionRecord _sessionRecord;

    public NewTransactionEvent(final PlatformTransactionManager source) {
        super(source);
    }

    public NewTransactionEvent(final PlatformTransactionManager ptm,
            final TransactionStatus transactionStatus,
            final SessionRecord sessionRecord) {
        this(ptm);
        _platformTransactionManager = ptm;
        _transactionStatus = transactionStatus;
        _sessionRecord = sessionRecord;

    }

    public PlatformTransactionManager getPlatformTransactionManager() {
        return _platformTransactionManager;
    }

    public TransactionStatus getTransactionStatus() {
        return _transactionStatus;
    }

    public SessionRecord getSessionRecord() {
        return _sessionRecord;
    }

}
