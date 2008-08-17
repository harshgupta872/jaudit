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

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.opensaas.jaudit.SessionRecord;
import org.opensaas.jaudit.session.AuditSession;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;

/**
 * A bridge to conver transaction life cycle events into application events.
 */
public class TransactionEventBridge implements ApplicationContextAware {

    private static final Logger LOGGER = Logger
            .getLogger(TransactionEventBridge.class.getName());

    private ApplicationContext _applicationContext;

    /**
     * Create an audit record detailing that a particular operation has taken
     * place.
     * 
     * @param jp
     *            The join point currently being fired.
     * @param annotation
     *            The required annotation that triggers this join point.
     * @return The result of executing the method wrapped by this join point.
     * @throws Throwable
     *             When there is an error.
     */
    public Object recordAction(final ProceedingJoinPoint jp) throws Throwable {

        if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, "joinpoint={0}, target={2}, args={1}",
                    new Object[] { jp.getSignature().toLongString(),
                            Arrays.toString(jp.getArgs()), jp.getTarget() });
        }

        // make the call
        final Object retval = jp.proceed();

        if (PlatformTransactionManager.class.isAssignableFrom(jp.getTarget()
                .getClass())) {

            final SessionRecord sr = AuditSession.getAuditSession() != null ? AuditSession
                    .getAuditSession().getSessionRecord()
                    : null;

            final String methodName = jp.getSignature().getName();

            if ("getTransaction".equals(methodName)) {
                final TransactionStatus ts = (TransactionStatus) retval;

                if (ts.isNewTransaction()) {
                    _applicationContext
                            .publishEvent(new NewTransactionEvent(
                                    (PlatformTransactionManager) jp.getTarget(),
                                    ts, sr));
                }
            }
        }

        return retval;
    }

    /**
     * {@inheritDoc}
     */
    @Required
    public void setApplicationContext(
            final ApplicationContext applicationContext) throws BeansException {
        _applicationContext = applicationContext;

    }

}