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

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

/**
 * Default VO impelmentaiton of {@link ResponsibleInformation}.
 */
@Embeddable
public class ResponsibleInformation {

    private AuditSubject _responsible;

    private String _responsibleAddress;

    private String _responsibleAgent;

    private String _credentialsType;

    /**
     * The unique identifier of the entity which is accountable for this
     * session. Example: a logged in user's id or a subsystem's identifier.
     * 
     * TODO: Document some strategies for GUID/Unique id generation. Should
     * include the max length and content of the id. Maybe UUID?
     * 
     * Optional.
     * 
     * @return the unique identifier of the entity responsible.
     */
    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name = "subjectId", column = @Column(name = "responsible_subject_id")),
            @AttributeOverride(name = "subjectType", column = @Column(name = "responsible_subject_type")),
            @AttributeOverride(name = "subjectDiscriminator", column = @Column(name = "responsible_subject_discriminator")) })
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
     * The address of the responsible entity that is accountable for this
     * session. An example is an IP Address.
     * 
     * When not null, should be less than or equal to 255 characters.
     * 
     * @see #getResponsible()
     * 
     * Optional.
     * 
     * @return the address of the entity responsible for this session.
     */
    @Column(name = "responsible_address", length = 255)
    public String getResponsibleAddress() {
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
    public void setResponsibleAddress(String responsibleAddress) {
        _responsibleAddress = responsibleAddress;
    }

    /**
     * The agent the responsible entity for this session is using to communicate
     * with the system. For example, if a web browser is being used, it would be
     * the user agent that the browser supplies.
     * 
     * <tt>Mozilla/5.001 (Macintosh; N; PPC; ja) Gecko/25250101 MegaCorpBrowser/1.0 (MegaCorp, Inc.)</tt>
     * 
     * When not null, should be less than or equal to 255 characters.
     * 
     * @see #getResponsible()
     * 
     * Optional.
     * 
     * @return the address of the entity responsible for this session.
     */
    @Column(name = "responsible_agent", length = 255)
    public String getResponsibleAgent() {
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
    public void setResponsibleAgent(String responsibleAgent) {
        _responsibleAgent = responsibleAgent;
    }

    /**
     * Returns the credentials used to start this session.
     * 
     * Optional. When not null, less than 255 characters.
     * 
     * @return the credentials type.
     */
    @Column(name = "credentials_type", length = 255)
    public String getCredentialsType() {
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
    public void setCredentialsType(String credentialsType) {
        _credentialsType = credentialsType;
    }

}
