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

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Default hibernate annotated implemenation of {@link AuditSubject}.
 * 
 */
@Embeddable
public class AuditSubject {

    private String _subjectType;

    private String _subjectId;

    private String _subjectDiscriminator;

    /**
     * The unique identifier of the entity which is accountable for whatever is
     * being audited. Usually the actor.
     * 
     * TODO: Document some strategies for GUID/Unique id generation. Should
     * include the max length and content of the id. Maybe UUID?
     * 
     * @see #getSubjectType()
     * 
     * Optional.
     * 
     * @return the unique identifier of the audit subject.
     */
    @Column(name = "subject_type", length = 255)
    public String getSubjectType() {
        return _subjectType;
    }

    /**
     * Returns the type of this entity.
     * 
     * @see #getSubjectId()
     * 
     * Optional. If not null, length less than or equal to 255.
     * 
     * @return the type of the audit subject.
     */
    public void setSubjectType(String subjectType) {
        _subjectType = subjectType;
    }

    /**
     * {@inheritDoc}
     */
    @Column(name = "subject_id", nullable = false)
    public String getSubjectId() {
        return _subjectId;
    }

    /**
     * Sets the required id.
     * 
     * @see #getSubjectId().
     * 
     * @param id
     */
    public void setSubjectId(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Id may not be null.");
        }
        _subjectId = id;
    }

    /**
     * Returns a String which is a single level grouping for all audit subjects.
     * 
     * Most often, a discriminator represents an overall account that groups
     * users together. For example, in SalesForce, this would be the
     * organization id. Less than or equal to 255 characters in length. Can also
     * represent datawharehouse ids, departments, etc.
     * 
     * Optional.
     * 
     * @return the discriminator for this subject.
     */
    @Column(name = "subject_discriminator")
    public String getSubjectDiscriminator() {
        return _subjectDiscriminator;
    }

    /**
     * Sets the optional discriminator.
     * 
     * @see #getSubjectDiscriminator()
     * 
     * @param subjectDiscriminator
     *            the optional discriminator to set.
     */
    public void setSubjectDiscriminator(String subjectDiscriminator) {
        _subjectDiscriminator = subjectDiscriminator;
    }

}
