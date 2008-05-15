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
package org.opensaas.exampleapp.audit;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.opensaas.exampleapp.model.FooBar;
import org.opensaas.jaudit.AuditSubject;
import org.opensaas.jaudit.LifeCycleAudit;
import org.opensaas.jaudit.LifeCycleType;
import org.opensaas.jaudit.service.AuditService;

/**
 * AuditExecutor is used...
 */
public class AuditExecutor {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = Logger.getLogger(AuditExecutor.class
            .getName());

    private AuditService auditService;

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
    public Object recordAction(final ProceedingJoinPoint jp,
            final LifeCycleAudit annotation) throws Throwable {
        LOGGER.log(Level.FINE, "joinpoint={0}, target={2}, args={1}",
                new Object[] { jp.getSignature().toLongString(),
                        Arrays.toString(jp.getArgs()), jp.getTarget() });

        // make the call
        final Object retval = jp.proceed();

        switch (annotation.type()) {
        case SAVE:
            // we know for a save we are dealing with FooBars
            final FooBar before = (FooBar) jp.getArgs()[0];
            final FooBar after = (FooBar) retval;
            recordSave(before.getId() == null, after);
        break;

        case DELETE:
            recordDelete((Long) jp.getArgs()[0]);
        break;

        default:
            throw new UnsupportedOperationException("Unexpected type:"
                    + annotation.type());
        }

        return retval;
    }

    /**
     * Return the auditService.
     * 
     * @return the auditService.
     */
    public final AuditService getAuditService() {
        return auditService;
    }

    /**
     * Set the auditService to the given value.
     * 
     * @param auditService
     *            the auditService to set
     */
    public final void setAuditService(final AuditService auditService) {
        this.auditService = auditService;
    }

    private void recordDelete(final Long id) {
        final AuditSubject target = new AuditSubject();
        target.setSubjectType(FooBar.class.getName());
        target.setSubjectId(id.toString());
        auditService.createLifeCycleAuditEvent(LifeCycleType.DELETE, target,
                null);
    }

    private void recordSave(final boolean isCreate, final FooBar foobar) {
        final AuditSubject target = new AuditSubject();
        target.setSubjectType(FooBar.class.getName());
        target.setSubjectId(foobar.getId().toString());
        auditService.createLifeCycleAuditEvent(isCreate ? LifeCycleType.CREATE
                : LifeCycleType.UPDATE, target, foobar.toString());
    }
}
