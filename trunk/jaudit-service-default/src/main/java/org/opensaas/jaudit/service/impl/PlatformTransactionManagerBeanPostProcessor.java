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
package org.opensaas.jaudit.service.impl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Date;

import org.opensaas.jaudit.TransactionRecordMutable;
import org.opensaas.jaudit.session.AuditSession;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.PriorityOrdered;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;

/**
 * Start of a class to wrap the Spring PlatformTransactionManager to implement
 * TransactionRecord processing. Note our invocation handler is not yet
 * complete.
 */
public class PlatformTransactionManagerBeanPostProcessor implements
        PriorityOrdered, BeanPostProcessor {

    private int _order;

    private ObjectFactory _transactionRecordFactory;

    /**
     * {@inheritDoc}
     */
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {
        return bean;
    }

    /**
     * {@inheritDoc}
     */
    public Object postProcessBeforeInitialization(final Object bean,
            final String beanName) throws BeansException {

        if (PlatformTransactionManager.class.isAssignableFrom(bean.getClass())) {

            final Class<?> clazz = bean.getClass();
            final Class<?>[] interfaces = clazz.getInterfaces();

            final ArrayList<Class<?>> filteredInterfaces = new ArrayList<Class<?>>(
                    interfaces.length);
            for (final Class<?> interfaceClass : interfaces) {
                if (PlatformTransactionManager.class.equals(interfaceClass)) {
                    continue;
                }
                filteredInterfaces.add(interfaceClass);
            }

            final Object returnBean = Proxy.newProxyInstance(bean.getClass()
                    .getClassLoader(), filteredInterfaces
                    .toArray(new Class[filteredInterfaces.size()]),
                    new PlatformTransactionManagerInvocationHandler(bean));
            return returnBean;

        } else {
            return bean;
        }
    }

    /**
     * {@inheritDoc}
     */
    public int getOrder() {
        return _order;
    }

    /**
     * @see #getOrder()
     * 
     * @param order
     *            the order to set
     */
    public void setOrder(int order) {
        _order = order;
    }

    private class PlatformTransactionManagerInvocationHandler implements
            InvocationHandler {

        private final Object _target;

        public PlatformTransactionManagerInvocationHandler(final Object target) {
            if (target == null) {
                throw new IllegalArgumentException("Target must not be null.");
            }
            _target = target;
        }

        /**
         * {@inheritDoc}
         */
        public Object invoke(final Object proxy, final Method method,
                final Object[] args) throws Throwable {
            if ("getTransaction".equals(method.getName())
                    && TransactionStatus.class.isAssignableFrom(method
                            .getReturnType())) {

                final TransactionStatus ts = (TransactionStatus) method.invoke(
                        _target, args);

                if (ts.isNewTransaction()) {
                    final TransactionRecordMutable transactionRecord = (TransactionRecordMutable) _transactionRecordFactory
                            .getObject();
                    transactionRecord.setTransactionId(Integer.toHexString(ts
                            .hashCode()));
                    transactionRecord.setSessionRecord(AuditSession
                            .getAuditSession().getSessionRecord());
                    transactionRecord.setStartedTs(new Date());
                }

                // TODO: Finish this method.

                return ts;

            } else {
                return method.invoke(_target, args);
            }
        }
    }

}
