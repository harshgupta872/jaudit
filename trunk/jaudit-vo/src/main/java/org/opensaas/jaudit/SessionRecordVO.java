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

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Id;
import javax.persistence.Table;

import org.opensaas.jaudit.AuditSubject;
import org.opensaas.jaudit.SessionRecord;

/**
 * This is the default implementation and persistence definition for
 * {@link SessionRecord}.
 * 
 */
@javax.persistence.Entity
@Table(name = "session_records")
public class SessionRecordVO implements SessionRecord {

    private Date _endedTs;

    private CharSequence _id;

    private AuditSubject _responsible;

    private CharSequence _responsibleAddress;

    private CharSequence _responsibleAgent;

    private Date _startedTs;

    private AuditSubject _system;

    private CharSequence _systemAddress;

    private CharSequence _credentialsType;

    /**
     * {@inheritDoc}
     */
    @Column(name = "ended_ts")
    public Date getEndedTs() {
        return _endedTs;
    }

    /**
     * Sets the optional time this session ended.
     * 
     * @see #getEndedTs()
     * 
     * @param endedTs
     *            time ended.
     * 
     */
    public void setEndedTs(Date endedTs) {
        _endedTs = endedTs;
    }

    /**
     * {@inheritDoc}
     */
    @Id
    public CharSequence getId() {
        return _id;
    }

    /**
     * Sets the unique id for this record.
     * 
     * @see #getId()
     * 
     * @param id
     */
    public void setId(CharSequence id) {
        if (id == null) {
            throw new IllegalArgumentException("Id is required.");
        }
        _id = id;
    }

    /**
     * {@inheritDoc}
     */
    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name = "id", column = @Column(name = "responsible_subject_id", nullable=false)),
            @AttributeOverride(name = "subjectType", column = @Column(name = "responsible_subject_type")) })
    public AuditSubject getResponsible() {
        return _responsible;
    }

    /**
     * Sets the subject responsible for work done from this session.
     * 
     * @see #getResponsible()
     * 
     * @param responsible
     */
    public void setResponsible(AuditSubject responsible) {
        _responsible = responsible;
    }

    /**
     * {@inheritDoc}
     */
    @Column(name = "responsible_address", length=256)
    public CharSequence getResponsibleAddress() {
        return _responsibleAddress;
    }

    /**
     * Sets the address, most likely ip, of the {@link #getResponsible()}
     * subject.
     * 
     * @see #getResponsibleAddress()
     * 
     * @param responsibleAddress
     */
    public void setResponsibleAddress(CharSequence responsibleAddress) {
        _responsibleAddress = responsibleAddress;
    }

    /**
     * {@inheritDoc}
     */
    @Column(name = "responsible_agent", length=256)
    public CharSequence getResponsibleAgent() {
        return _responsibleAgent;
    }

    /**
     * Sets the optional agent string which is agent strning for the client used
     * by the {@link #getResponsible()} subject.
     * 
     * @see #getResponsibleAgent()
     * @param responsibleAgent
     *            the responsible agent to set.
     */
    public void setResponsibleAgent(CharSequence responsibleAgent) {
        _responsibleAgent = responsibleAgent;
    }

    /**
     * {@inheritDoc}
     */
    @Column(name = "started_ts")
    public Date getStartedTs() {
        return _startedTs;
    }

    /**
     * Sets the optional started time for this session.
     * 
     * @see #getStartedTs()
     * 
     * @param startedTs
     *            the started time.
     */
    public void setStartedTs(Date startedTs) {
        _startedTs = startedTs;
    }

    /**
     * {@inheritDoc}
     */
    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name = "id", column = @Column(name = "system_subject_id")),
            @AttributeOverride(name = "subjectType", column = @Column(name = "system_subject_type")) })
    public AuditSubject getSystem() {
        return _system;
    }

    /**
     * Sets the optional system subject to associate with this session.
     * 
     * @see #getSystem()
     * 
     * @param system
     *            the system to set.
     */
    public void setSystem(AuditSubject system) {
        _system = system;
    }

    /**
     * {@inheritDoc}
     */
    @Column(name = "system_address", length=256)
    public CharSequence getSystemAddress() {
        return _systemAddress;
    }

    /**
     * Sets the optional system address for this session.
     * 
     * @see #getSystemAddress()
     * 
     * @param systemAddress
     *            the system address.
     */
    public void setSystemAddress(CharSequence systemAddress) {
        _systemAddress = systemAddress;
    }

    /**
     * {@inheritDoc}
     */
    @Column(name = "system_address", length=256)
    public CharSequence getCredentialsType() {
        return _credentialsType;
    }

    /**
     * Sets the optional credentials type.
     * 
     * @see #getCredentialsType()
     * 
     * @param credentialsType
     *            the type to set.
     */
    public void setCredentialsType(CharSequence credentialsType) {
        _credentialsType = credentialsType;
    }

}
