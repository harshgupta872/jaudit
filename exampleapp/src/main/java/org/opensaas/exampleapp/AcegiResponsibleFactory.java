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
package org.opensaas.exampleapp;

import javax.servlet.http.HttpServletRequest;

import org.acegisecurity.Authentication;
import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;
import org.opensaas.jaudit.AuditSubject;
import org.opensaas.jaudit.ResponsibleInformation;
import org.opensaas.jaudit.webapp.ResponsibleFactory;

/**
 * A simple implementaiton of {@link ResponsibleFactory} that demonstrates how
 * to fill in information for an Acegi based application.
 * 
 * TODO: We may want to move this into another more shareable project.
 */
public class AcegiResponsibleFactory implements ResponsibleFactory {

    /**
     * {@inheritDoc}
     */
    public void fillInResponsible(
            ResponsibleInformation responsibleInformation,
            HttpServletRequest request) {
        if (responsibleInformation == null) {
            throw new IllegalArgumentException(
                    "Responsible Information is required.");
        }

        if (request == null) {
            throw new IllegalArgumentException("Request is required.");
        }

        SecurityContext sc = SecurityContextHolder.getContext();

        responsibleInformation.setResponsibleAddress(request.getRemoteAddr());
        responsibleInformation.setResponsibleAgent(request
                .getHeader("User-Agent"));

        if (sc == null || sc.getAuthentication() == null) {
            return;
        }
        Authentication auth = sc.getAuthentication();
        responsibleInformation.setCredentialsType(auth.getCredentials()
                .getClass().getSimpleName());
        AuditSubject auditSubject = responsibleInformation.getResponsible();
        auditSubject.setSubjectId(auth.getName());
        auditSubject.setSubjectType(auth.getPrincipal().getClass()
                .getSimpleName());

    }

    /**
     * {@inheritDoc}
     */
    public boolean updateResponsible(
            ResponsibleInformation responsibleInformation,
            HttpServletRequest request) {
        if (responsibleInformation == null) {
            throw new IllegalArgumentException(
                    "Responsible Information is required.");
        }

        AuditSubject auditSubject = new AuditSubject();

        SecurityContext sc = SecurityContextHolder.getContext();
        if (sc == null || sc.getAuthentication() == null) {
            if (auditSubject.equals(responsibleInformation.getResponsible())) {
                return false;
            }
            responsibleInformation.setResponsible(auditSubject);
            return true;
        }
        Authentication auth = sc.getAuthentication();

        auditSubject.setSubjectId(auth.getName());
        auditSubject.setSubjectType(auth.getPrincipal().getClass()
                .getSimpleName());

        if (auditSubject.equals(responsibleInformation.getResponsible())) {
            return false;
        }
        responsibleInformation.setResponsible(auditSubject);
        return true;
    }

}
