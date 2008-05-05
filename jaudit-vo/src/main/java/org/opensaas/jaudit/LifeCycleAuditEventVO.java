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

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Default implementation and persistence mapping for an
 * {@link LifeCycleAuditEvent}.
 * 
 */
@Entity
@Table(name = "life_cycle_audit_events")
public class LifeCycleAuditEventVO extends AuditEventVO implements
        LifeCycleAuditEvent {

    private LifeCycleType _lifeCycleEventType;

    private Collection<PropertyValueChange> _propertyValueChanges = new ArrayList<PropertyValueChange>();

    /**
     * {@inheritDoc}
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "life_cycle_event_type", nullable = false)
    public LifeCycleType getLifeCycleEventType() {
        return _lifeCycleEventType;
    }

    /**
     * Sets the required type of this life cycle event.
     * 
     * @see #getLifeCycleEventType()
     * 
     * @param lifeCycleEventType
     *            the required type.
     */
    public void setLifeCycleEventType(LifeCycleType lifeCycleEventType) {
        _lifeCycleEventType = lifeCycleEventType;
    }

    /**
     * {@inheritDoc}
     */
    @OneToMany(mappedBy = "lifeCycleAuditEvent", targetEntity = PropertyValueChangeVO.class)
    public Collection<PropertyValueChange> getPropertyValueChanges() {
        return (Collection<PropertyValueChange>) _propertyValueChanges;
    }

    /**
     * Sets the optional property value changes.
     * 
     * @see #getPropertyValueChanges()
     * 
     * @param propertyValueChanges
     */
    public void setPropertyValueChanges(
            Collection<PropertyValueChange> propertyValueChanges) {

        if (propertyValueChanges == null) {
            try {
                _propertyValueChanges.clear();
            } catch (UnsupportedOperationException e) {
                _propertyValueChanges = new ArrayList<PropertyValueChange>();
            }
        } else {
            for (PropertyValueChange pvc : propertyValueChanges) {
                if (pvc instanceof PropertyValueChangeVO) {
                    ((PropertyValueChangeVO) pvc).setLifeCycleAuditEvent(this);
                }
            }
            _propertyValueChanges = propertyValueChanges;
        }

    }
}
