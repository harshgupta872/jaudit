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
package org.opensaas.jaudit;

import java.util.Date;

/**
 * A session record is a record of a new session within the system. Most often,
 * a session is created when a user or entity authenticates to a system. All
 * work done by that user or entity is this executed as part of the session
 * record.
 */
public interface SessionRecordMutable extends SessionRecord {

    /**
     * Sets the required id.
     * 
     * @param id
     *            String globally unique id.
     */
    void setId(String id);

    /**
     * Sets the optional session Id.
     * 
     * @see SessionRecord#getSessionId()
     * @param sessionId
     *            Optional sesison id.
     */
    void setSessionId(String sessionId);

    /**
     * Sets the required started time stamp.
     * 
     * @see SessionRecord#getStartedTs()
     * 
     * @param startedTs
     *            Date at which this session started.
     */
    void setStartedTs(Date startedTs);

    /**
     * Sets the optional ended time stamp.
     * 
     * @see SessionRecord#getEndedTs()
     * 
     * @param endedTs
     *            Date at which this session ended.
     */
    void setEndedTs(Date endedTs);

    /**
     * Sets the optional system subject to associate with this session.
     * 
     * @see SessionRecord#getSystem()
     * 
     * @param system
     *            the system to set.
     */
    void setSystem(AuditSubject system);

    /**
     * Sets the optional system address for this session.
     * 
     * @see SessionRecord#getSystemAddress()
     * 
     * @param systemAddress
     *            the system address.
     */
    void setSystemAddress(String systemAddress);

    /**
     * Sets the optional responsible information for this session record.
     * 
     * @see SessionRecord#getResponsibleInformation()
     * 
     * @param responsibleInformation
     *            Responsible information to set.
     */
    void setResponsibleInformation(ResponsibleInformation responsibleInformation);

}
