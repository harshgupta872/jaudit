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

import java.util.logging.Level;
import java.util.logging.Logger;

import org.opensaas.exampleapp.model.FooBar;
import org.opensaas.jaudit.LifeCycleAudit;
import org.opensaas.jaudit.LifeCycleType;
import org.opensaas.jaudit.service.AuditService;
import org.opensaas.jaudit.session.AuditSession;

/**
 * AuditExecutor is used...
 */
public class AuditExecutor {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = Logger.getLogger(AuditExecutor.class
            .getName());

    private AuditService auditService;

    /**
     * Create an audit record detailing that a save operation has taken place.
     * 
     * @param annotation
     *            The required annotation that triggers this join point.
     * @param foobar
     *            The object being saved.
     */
    public void recordSave(final LifeCycleAudit annotation, final FooBar foobar) {
        if (annotation.type().equals(LifeCycleType.SAVE)) {
            LOGGER.log(Level.INFO, "==========SAVE! obj={0} session={1}",
                    new Object[] { foobar, getAuditSession() });
        }
    }

    /**
     * Create an audit record detailing that a delete operation has taken place.
     * 
     * @param annotation
     *            The required annotation that triggers this join point.
     * @param id
     *            The id of the object being deleted.
     */
    public void recordDelete(final LifeCycleAudit annotation, final Long id) {
        if (annotation.type().equals(LifeCycleType.DELETE)) {
            LOGGER.log(Level.INFO, "==========DELETE! id={0} session={1}",
                    new Object[] { id, getAuditSession() });
        }
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

    private AuditSession getAuditSession() {
        return AuditSession.getAuditSession();
    }
}
