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
public class EntityVO implements AuditSubject {

    private String _subjectType;

    private String _subjectId;

    private String _subjectDiscriminator;

    /**
     * {@inheritDoc}
     */
    @Column(name = "subject_type", length = 255)
    public String getSubjectType() {
        return _subjectType;
    }

    /**
     * Sets the subject type.
     * 
     * @see #setId(String)
     * 
     * @param subjectType
     *            to set.
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
     * {@inheritDoc}
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
     * @param discriminator
     *            the optional discriminator to set.
     */
    public void setDiscriminator(String discriminator) {
        _subjectDiscriminator = discriminator;
    }

}
