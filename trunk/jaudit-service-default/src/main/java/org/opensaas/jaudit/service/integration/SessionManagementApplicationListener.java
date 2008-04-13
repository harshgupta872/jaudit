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
package org.opensaas.jaudit.service.integration;

import org.acegisecurity.Authentication;
import org.acegisecurity.event.authentication.AuthenticationSuccessEvent;
import org.acegisecurity.ui.WebAuthenticationDetails;
import org.opensaas.jaudit.AuditSubject;
import org.opensaas.jaudit.ResponsibleInformation;
import org.opensaas.jaudit.SessionRecord;
import org.opensaas.jaudit.service.AuditService;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * Can be used to integrate into a spring application to create and end
 * {@link SessionRecord}s. This is more of reference implementation than
 * anything else.
 */
public class SessionManagementApplicationListener implements
        ApplicationListener {

    private AuditService _auditService;

    /**
     * {@inheritDoc}
     */
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof AuthenticationSuccessEvent) {
            AuthenticationSuccessEvent ae = (AuthenticationSuccessEvent) event;
            Authentication auth = ae.getAuthentication();
            Object details = auth.getDetails();
            String remoteIp = null;
            String sessionId = null;
            if (details instanceof WebAuthenticationDetails) {
                WebAuthenticationDetails webDetails = (WebAuthenticationDetails) details;
                remoteIp = webDetails.getRemoteAddress();
                sessionId = webDetails.getSessionId();
            }

            AuditSubject auditSubject = _auditService.newAuditSubjectMutable();
            auditSubject.setSubjectId(auth.getName());
            auditSubject.setSubjectType(auth.getPrincipal().getClass()
                    .getSimpleName());

            ResponsibleInformation responsibleInformation = _auditService
                    .newResponsibleInformation();
            responsibleInformation.setResponsible(auditSubject);
            responsibleInformation.setCredentialsType(auth.getCredentials()
                    .getClass().getSimpleName());
            responsibleInformation.setResponsibleAddress(remoteIp);

            _auditService
                    .createSessionRecord(sessionId, responsibleInformation);
        }

    }

    /**
     * Sets our required audit service.
     * 
     * @param auditService
     */
    @Required
    public void setAuditService(AuditService auditService) {
        _auditService = auditService;
    }
}
