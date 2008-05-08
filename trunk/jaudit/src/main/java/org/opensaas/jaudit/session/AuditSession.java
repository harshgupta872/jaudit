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
 * Keeps track of {@link SessionRecord}s on a per-thread basis.
 */
final public class AuditSession implements Serializable {

    /**
     * Generated Serial Id.
     */
    private static final long serialVersionUID = -4332917630799895418L;

    private final SessionRecord _sessionRecord;

    private static final ThreadLocal<AuditSession> AUDIT_SESSION_CONTEXT = new ThreadLocal<AuditSession>();

    /* Not private for testing only. */
    /* package */AuditSession(final SessionRecord sessionRecord) {
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
    static public AuditSession createAuditSession(
            final SessionRecord sessionRecord) {
        final AuditSession oldSession = AUDIT_SESSION_CONTEXT.get();
        if (oldSession != null) {
            throw new IllegalStateException(
                    "Audit Session is already registered.  Old session="
                            + oldSession + "; new session=" + sessionRecord);
        }

        final AuditSession auditSession = new AuditSession(sessionRecord);
        AUDIT_SESSION_CONTEXT.set(auditSession);
        return auditSession;
    }

    /**
     * Return the AuditSession associated with the current thread, or null if
     * {@link #createAuditSession(SessionRecord)} has not yet been called.
     * 
     * @return the AuditSession associated with the current thread.
     */
    static public AuditSession getAuditSession() {
        return AUDIT_SESSION_CONTEXT.get();
    }

    /**
     * Remove the AuditSession currently associated with the current thread.
     */
    static public void removeAuditSession() {
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
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(255);
        sb.append("AuditSession[");
        sb.append("_sessionRecord=");
        sb.append(_sessionRecord);
        sb.append("]");
        return sb.toString();
    }

    /**
     * ${@inheritDoc}
     */
    @Override
    public int hashCode() {
        // sessionRecord can't be null
        return 37 * _sessionRecord.hashCode();
    }

    /**
     * ${@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AuditSession other = (AuditSession) obj;
        // sessionRecord can't be null
        return _sessionRecord.equals(other._sessionRecord);
    }
}
