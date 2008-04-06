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

    private String _id;

    private String _discriminator;

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
    public String getId() {
        return _id;
    }

    /**
     * Sets the required id.
     * 
     * @see #getId().
     * 
     * @param id
     */
    public void setId(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Id may not be null.");
        }
        _id = id;
    }

    /**
     * {@inheritDoc}
     */
    @Column(name = "discriminator")
    public String getDiscriminator() {
        return _discriminator;
    }

    /**
     * Sets the optional discriminator.
     * 
     * @see #getDiscriminator()
     * 
     * @param discriminator
     *            the optional discriminator to set.
     */
    public void setDiscriminator(String discriminator) {
        _discriminator = discriminator;
    }

}
