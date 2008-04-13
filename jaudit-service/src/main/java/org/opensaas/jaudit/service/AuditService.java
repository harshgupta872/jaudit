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
package org.opensaas.jaudit.service;

import org.opensaas.jaudit.AuditSubject;
import org.opensaas.jaudit.ResponsibleInformation;
import org.opensaas.jaudit.SessionRecord;

/**
 * The default interface for working with the audit framework.
 */
public interface AuditService {

    /**
     * Create a new mutable audit subject. This instance is NOT persisted by may
     * be associated with a {@link ResponsibleInformation} or other such object.
     * 
     * @return AuditSubjectMutable
     */
    AuditSubject newAuditSubjectMutable();

    /**
     * Returns a new empty responsible information object. This instance is NOT
     * persisted but may be associated with a {@link SessionRecord} or other
     * such object.
     * 
     * @return ResponsibleInformationMutable
     */
    ResponsibleInformation newResponsibleInformation();

    /**
     * Creates and saves a new instance of a SessionRecord. This instance will
     * be filled by the implementation of {@link AuditService} but will use the
     * values of sessionId and responsibleInformation passed in.
     * 
     * @see #create(SessionRecord)
     * 
     * @return the newly created session record.
     */
    SessionRecord createSessionRecord(String sessionId,
            ResponsibleInformation responsibleInformation);

    /**
     * Creates a new empty instance of a SessionRecord.
     * 
     * @see #create(SessionRecord)
     * 
     * @return the newly created session record.
     */
    SessionRecord createSessionRecord();

    /**
     * The audit service will handle finishing the session information. Mainly
     * the ended timestmap.
     * 
     * @param sessionRecord=
     * 
     */
    void sessionEnded(SessionRecord sessionRecord);

}
