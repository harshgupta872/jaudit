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
 * 
 */
public interface SessionRecord {

    /**
     * Returns the globally unique id of this audit record. Implementors and
     * creators of a SessionRecord should take care to try to ensure uniqueness
     * of this ID.
     * 
     * Required.
     * 
     * TODO: Document some strategies for GUID/Unique id generation. Should
     * include the max length and content of the id. Maybe UUID?
     * 
     * @return a globally unique id for this session record.
     */
    CharSequence getId();

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
     * The unique identifier of the entity which is accountable for this
     * session. Example: a logged in user's id or a subsystem's identifier.
     * 
     * @see #getResponsibleType()
     * 
     * TODO: Document some strategies for GUID/Unique id generation. Should
     * include the max length and content of the id. Maybe UUID?
     * 
     * Required.
     * 
     * @return the unique identifier of the entity responsible.
     */
    AuditSubject getResponsible();

    /**
     * The address of the responsible entity that is accountable for this
     * session. An example is an IP Address.
     * 
     * When not null, should be less than or equal to 256 characters.
     * 
     * @see #getResponsible()
     * 
     * Optional.
     * 
     * @return the address of the entity responsible for this session.
     */
    CharSequence getResponsibleAddress();

    /**
     * The agent the responsible entity for this session is using to communicate
     * with the system. For example, if a web browser is being used, it would be
     * the user agent that the browser supplies.
     * 
     * <tt>Mozilla/5.001 (Macintosh; N; PPC; ja) Gecko/25250101 MegaCorpBrowser/1.0 (MegaCorp, Inc.)</tt>
     * 
     * When not null, should be less than or equal to 256 characters.
     * 
     * @see #getResponsible()
     * 
     * Optional.
     * 
     * @return the address of the entity responsible for this session.
     */
    CharSequence getResponsibleAgent();

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
     * When not null, should be less than or equal to 256 characters.
     * 
     * @see #getResponsible()
     * 
     * Optional.
     * 
     * @return the address of the entity responsible for this session.
     */
    CharSequence getSystemAddress();

    /**
     * Returns the credentials used to start this session.
     * 
     * Optional. When not null, less than 256 characters.
     * 
     * @return the credentials type.
     */
    CharSequence getCredentialsType();

}
