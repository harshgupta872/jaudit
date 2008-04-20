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
package org.opensaas.jaudit.session;

import java.io.Serializable;

import org.opensaas.jaudit.SessionRecord;

/**
 * 
 */
public class AuditSession implements Serializable {

    /**
     * Generated Serial Id.
     */
    private static final long serialVersionUID = -4332917630799895418L;

    private final SessionRecord _sessionRecord;

    private static final ThreadLocal<AuditSession> AUDIT_SESSION_CONTEXT = new ThreadLocal<AuditSession>();

    private AuditSession(SessionRecord sessionRecord) {
        if (sessionRecord == null) {
            throw new IllegalArgumentException("Session record is required.");
        }
        _sessionRecord = sessionRecord;
    }

    /**
     * Returns a new empty instance of an AuditSession. The SessionRecord is
     * required.
     * 
     * @param sessionRecord
     *            the required session record.
     * @return the newly created audit session. This audit session will
     *         immediately be associated into context and available via
     *         {@link #getAuditSession()}.
     */
    static public AuditSession createAuditSession(SessionRecord sessionRecord) {
        if (sessionRecord == null) {
            throw new IllegalArgumentException("Session record is required.");
        }
        AuditSession auditSession = new AuditSession(sessionRecord);

        AuditSession oldSession = AUDIT_SESSION_CONTEXT.get();
        if (oldSession != null) {
            throw new IllegalStateException(
                    "Audit Session is already registered.  Old session="
                            + oldSession + " new session record="
                            + sessionRecord);
        }

        AUDIT_SESSION_CONTEXT.set(auditSession);
        return auditSession;
    }

    static public AuditSession getAuditSession() {
        return AUDIT_SESSION_CONTEXT.get();
    }

    static public void removeAuditSession(AuditSession auditSession) {
        AUDIT_SESSION_CONTEXT.remove();
    }

    /**
     * Returns our associated session record.
     * 
     * @return the sessionRecord
     */
    public SessionRecord getSessionRecord() {
        return _sessionRecord;
    }

    /**
     * {@inheritDoc}
     */
    public String toString() {
        StringBuilder sb = new StringBuilder(255);
        sb.append("AuditSession[");
        sb.append("_sessionRecord=");
        sb.append(_sessionRecord);
        sb.append("]");
        return sb.toString();
    }

}
