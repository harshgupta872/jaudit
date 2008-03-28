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
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.opensaas.jaudit.AuditSubject;
import org.opensaas.jaudit.MembershipChangeEvent;
import org.opensaas.jaudit.MembershipChangeEventType;

/**
 * The default implementation and persistence mapping of
 * {@link MembershipChangeEvent}.
 * 
 */
@Entity
@Table(name = "membership_change_events")
public class MembershipChangeEventVO extends AuditEventVO implements
        MembershipChangeEvent {

    private MembershipChangeEventType _membershipChangeEventType;

    private AuditSubject _membershipGroup;

    private String _membershipProperty;

    /**
     * {@inheritDoc}
     */
    @Column(name = "membership_change_event_type", nullable = false)
    @Enumerated(value = EnumType.STRING)
    public MembershipChangeEventType getMembershipChangeEventType() {
        return _membershipChangeEventType;
    }

    /**
     * Sets the required membership change event type.
     * 
     * @see #getMembershipChangeEventType()
     * 
     * @param membershipChangeEventType
     *            the type to set.
     */
    public void setMembershipChangeEventType(
            MembershipChangeEventType membershipChangeEventType) {
        if (membershipChangeEventType == null) {
            throw new IllegalArgumentException(
                    "Membership change event type must not be null.");
        }
        _membershipChangeEventType = membershipChangeEventType;
    }

    /**
     * {@inheritDoc}
     */
    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name = "id", column = @Column(name = "membership_group_subject_id", nullable = false)),
            @AttributeOverride(name = "subjectType", column = @Column(name = "membership_group_subject_type")) })
    public AuditSubject getMembershipGroup() {
        return _membershipGroup;
    }

    /**
     * Sets the required membership group.
     * 
     * @see #getMembershipGroup()
     * 
     * @param membershipGroup
     */
    public void setMembershipGroup(AuditSubject membershipGroup) {
        if (membershipGroup == null) {
            throw new IllegalArgumentException(
                    "Membership Group must not be null.");
        }
        _membershipGroup = membershipGroup;
    }

    /**
     * {@inheritDoc}
     */
    @Column(name = "membership_property", length=256)
    public String getMembershipProperty() {
        return _membershipProperty;
    }

    /**
     * Sets the optional membership property.
     * 
     * @see #getMembershipProperty()
     * 
     * @param membershipProperty
     *            the membership property to set.
     */
    public void setMembershipProperty(String membershipProperty) {
        _membershipProperty = membershipProperty;
    }

}
