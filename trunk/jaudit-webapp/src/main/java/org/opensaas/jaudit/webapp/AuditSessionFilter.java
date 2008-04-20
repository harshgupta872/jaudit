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
package org.opensaas.jaudit.webapp;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.opensaas.jaudit.ResponsibleInformation;
import org.opensaas.jaudit.SessionRecord;
import org.opensaas.jaudit.service.AuditService;
import org.opensaas.jaudit.session.AuditSession;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * A web application filter responsible for creating an {@link AuditSession},
 * populating it and setting the context.
 */
public class AuditSessionFilter extends OncePerRequestFilter {

    public static final String AUDIT_SESSION_NAME = "org.opensaas.jaudit.auditsession";

    public static final String AUDIT_SERVICE_NAME = "auditService";

    public static final String RESPONSIBLE_FACTORY_NAME = "responsibleFactory";

    private String _auditServiceName = AUDIT_SERVICE_NAME;

    private String _responsibleFactoryName = RESPONSIBLE_FACTORY_NAME;

    private String _auditSessionName = AUDIT_SESSION_NAME;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final HttpSession session = request.getSession();
        AuditSession auditSession = (AuditSession) session
                .getAttribute(_auditSessionName);

        final AuditService auditService = lookupAuditService();
        final ResponsibleFactory responsibleFactory = lookupResponsibleFactory();

        if (auditSession == null) {
            final ResponsibleInformation ri = auditService
                    .newResponsibleInformation();

            responsibleFactory.fillInResponsible(ri, request);

            SessionRecord sessionRecord = auditService.createSessionRecord(
                    session.getId(), ri);

            auditSession = AuditSession.createAuditSession(sessionRecord);

        } else {

            // now check if the responsible information is set.
            SessionRecord sr = auditSession.getSessionRecord();
            ResponsibleInformation ri = sr.getResponsibleInformation();

            if (ri == null) {
                ri = auditService.newResponsibleInformation();
                responsibleFactory.fillInResponsible(ri, request);
                sr = auditService.updateResponsible(sr, ri);

            } else {
                if (responsibleFactory.updateResponsible(ri, request)) {
                    sr = auditService.updateResponsible(sr, ri);
                }
            }

        }

        session.setAttribute(_auditSessionName, auditSession);

        try {
            filterChain.doFilter(request, response);
        } finally {
            AuditSession.removeAuditSession(auditSession);
        }

    }

    /**
     * Sets the audit session name as an override to {@link #AUDIT_SESSION_NAME}.
     * 
     * @param auditSessionName
     *            the auditSessionName to set
     */
    public void setAuditSessionName(String auditSessionName) {
        _auditSessionName = auditSessionName;
    }

    /**
     * Sets the responsible factory name to use to lookup in the spring context
     * as an override to {@link #RESPONSIBLE_FACTORY_NAME}.
     * 
     * @param responsibleFactoryName
     *            the responsibleFactoryName to set
     */
    public void setResponsibleFactoryName(String responsibleFactoryName) {
        _responsibleFactoryName = responsibleFactoryName;
    }

    /**
     * @param auditServiceName
     *            the auditServiceName to set
     */
    public void setAuditServiceName(String auditServiceName) {
        _auditServiceName = auditServiceName;
    }

    /**
     * Look up the AuditService this filter will use.
     * 
     * @return AuditService
     */
    private AuditService lookupAuditService() {

        WebApplicationContext wac = WebApplicationContextUtils
                .getRequiredWebApplicationContext(getServletContext());
        return (AuditService) wac
                .getBean(_auditServiceName, AuditService.class);
    }

    /**
     * Look up the {@link ResponsibleFactory} this filter will use.
     * 
     * @return ResponsibleFactory
     */
    private ResponsibleFactory lookupResponsibleFactory() {

        WebApplicationContext wac = WebApplicationContextUtils
                .getRequiredWebApplicationContext(getServletContext());
        return (ResponsibleFactory) wac.getBean(_responsibleFactoryName,
                ResponsibleFactory.class);
    }

}
