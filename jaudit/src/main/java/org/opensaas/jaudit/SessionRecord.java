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

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * A session record is a record of a new session within the system. Most often,
 * a session is created when a user or entity authenticates to a system. All
 * work done by that user or entity is this executed as part of the session
 * record.
 * 
 */
public interface SessionRecord extends Serializable {

    /**
     * Returns the globally unique id of this audit record. Implementors and
     * creators of a SessionRecord should take care to try to ensure uniqueness
     * of this ID.
     * 
     * The default implementation of ids provided by the jaudit project are
     * based on Java's default {@link UUID#randomUUID()}.
     * 
     * Required.
     * 
     * @return a globally unique id for this session record.
     */
    String getId();

    /**
     * Returns the session implementation's specific id for this session. For
     * example, a servlet container will have its own id generation mechanism
     * and way of identifying sessions within its own context. This session id
     * should be globally unique so you may want to modify the implementations
     * returned session id to add more uniqueness to it.
     * 
     * Required.
     * 
     * @return String session id.
     */
    String getSessionId();

    /**
     * Returns the time at which this session was started.
     * 
     * Required.
     * 
     * @return Date at which this session started.
     */
    Date getStartedTs();

    /**
     * Returns the time at which this session was ended.
     * 
     * Optional.
     * 
     * @return Date at which this session ended.
     */
    Date getEndedTs();

    /**
     * The system on which this session was created and is being executed.
     * 
     * @see #getSystemType()
     * 
     * TODO: Document some strategies for GUID/Unique id generation. Should
     * include the max length and content of the id. Maybe UUID?
     * 
     * Optional.
     * 
     * @return the unique identifier of the system.
     */
    AuditSubject getSystem();

    /**
     * The address of the system on which this session was created and is being
     * executed. For example, an ip address.
     * 
     * When not null, should be less than or equal to 255 characters.
     * 
     * @see #getResponsibleInformation()
     * 
     * Optional.
     * 
     * @return the address of the entity responsible for this session.
     */
    String getSystemAddress();

    /**
     * Return informaiton about the entity that was responsible for the session.
     * 
     * Optional.
     * 
     * @return responsible information.
     */
    ResponsibleInformation getResponsibleInformation();

}
