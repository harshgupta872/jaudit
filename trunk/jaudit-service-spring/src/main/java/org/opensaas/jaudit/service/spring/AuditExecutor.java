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

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.Id;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.opensaas.jaudit.AuditSubject;
import org.opensaas.jaudit.LifeCycleAudit;
import org.opensaas.jaudit.service.AuditService;

/**
 * AuditExecutor is used...
 */
public class AuditExecutor {

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

        final AuditSubject auditSubject = getAuditSubject(jp, annotation,
                retval);
        String description = annotation.description();
        if (description == null || description.length() < 1) {
            description = annotation.type().name();
        }
        auditService.createLifeCycleAuditEvent(annotation.type(), auditSubject,
                description);

        return retval;
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

    private AuditSubject getAuditSubject(final JoinPoint jp,
            final LifeCycleAudit lcAuditAnnotation, final Object returnValue) {

        final Object[] args = jp.getArgs();
        if (args == null || args.length < 1) {
            LOGGER
                    .log(
                            Level.WARNING,
                            "JoinPoint has no arguments.  Cannot create a LifeCycle audit event without an argument.  jp={0}",
                            jp);
            return null;
        }

        final Object subject = args[0];
        if (subject == null) {
            LOGGER
                    .log(
                            Level.WARNING,
                            "JoinPoint has a null subject.  Cannot create a LifeCycle audit event without a non-null subject.  jp={0}",
                            jp);
            return null;
        }

        final AuditSubject auditSubject = new AuditSubject();

        if (lcAuditAnnotation.subjectType() != null
                && !lcAuditAnnotation.subjectType().equals(Void.class)) {
            auditSubject.setSubjectType(lcAuditAnnotation.subjectType()
                    .toString());
        } else {
            auditSubject.setSubjectType(subject.getClass().getName());
        }

        final Method[] methods = subject.getClass().getMethods();

        // First, try JPA annotations. In the future, we might try something
        // else.

        Method idMethod = null;

        for (final Method m : methods) {
            final Id annotation = m.getAnnotation(Id.class);
            if (annotation != null) {
                idMethod = m;
                break;
            }
        }
        if (idMethod == null) {
            // try getId() next
            for (final Method m : methods) {
                if ("getId".equalsIgnoreCase(m.getName())
                        && m.getParameterTypes().length == 0
                        && !m.getReturnType().equals(Void.TYPE)) {
                    idMethod = m;
                }
            }
        }

        if (idMethod == null || idMethod.getParameterTypes().length > 0
                || idMethod.getReturnType().equals(Void.TYPE)) {
            LOGGER
                    .log(
                            Level.WARNING,
                            "Cannot determine an ID method for subject class of type {0}. Will default to hashcode id.",
                            subject.getClass());
            auditSubject.setSubjectId(Integer.toHexString(subject.hashCode()));
            return auditSubject;
        }

        final boolean isAccessible = idMethod.isAccessible();
        if (!isAccessible) {
            idMethod.setAccessible(true);
        }

        try {
            Object id = idMethod.invoke(subject);
            if (id == null
                    && returnValue != null
                    && returnValue.getClass().isAssignableFrom(
                            subject.getClass())) {
                id = idMethod.invoke(returnValue);
            }
            if (id != null) {
                auditSubject.setSubjectId(id.toString());
            } else {
                LOGGER
                        .log(
                                Level.WARNING,
                                "Cannot uniquely identify subject {0} as the id returned by method {1} is null.  Will default to hashcode id.",
                                new Object[] { subject, idMethod });
                auditSubject.setSubjectId(Integer.toHexString(subject
                        .hashCode()));
            }
        } catch (Exception e) {
            LOGGER
                    .log(
                            Level.WARNING,
                            "Cannot execute ID method {0} on subject {1}.  Message={2}  Will default to hashcode id.",
                            new Object[] { idMethod, subject, e.getMessage() });
            LOGGER
                    .log(Level.WARNING, "Cannot execute id method (CONTINUED)",
                            e);
            auditSubject.setSubjectId(Integer.toHexString(subject.hashCode()));
        } finally {
            if (!isAccessible) {
                idMethod.setAccessible(false);
            }
        }

        return auditSubject;
    }
}
