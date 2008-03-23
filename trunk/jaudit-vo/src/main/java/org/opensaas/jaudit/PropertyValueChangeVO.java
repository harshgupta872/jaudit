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
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.opensaas.jaudit.LifeCycleAuditEvent;
import org.opensaas.jaudit.PropertyValueChange;

/**
 * A defuault implementation and persistence mapping for
 * {@link PropertyValueChange}.
 * 
 */
@Entity
@Table(name = "property_value_changes")
public class PropertyValueChangeVO implements PropertyValueChange {

    private CharSequence _id;

    private CharSequence _newValue;

    private CharSequence _oldValue;

    private CharSequence _propertyName;

    private CharSequence _propertyType;

    private boolean _newValueSpecified = false;

    private boolean _oldValueSpecified = false;

    private LifeCycleAuditEvent _lifeCycleAuditEvent;

    /**
     * {@inheritDoc}
     */
    @Id
    public CharSequence getId() {
        return _id;
    }

    /**
     * Sets the required id.
     * 
     * Required.
     * 
     * @see #getId()
     * 
     * @param id
     *            to set.
     */
    public void setId(CharSequence id) {
        if (id == null) {
            throw new IllegalArgumentException("Id must not be null.");
        }
        _id = id;
    }

    /**
     * {@inheritDoc}
     */
    @Column(name = "new_value", length = 1024)
    public CharSequence getNewValue() {
        return _newValue;
    }

    /**
     * Sets the optional new value. Side effect: if newValue != null then
     * {@link #isNewValueSpecified()} will be set to true automatically.
     * 
     * @see #getOldValue()
     * 
     * @param newValue
     *            new value to set.
     */
    public void setNewValue(CharSequence newValue) {
        _newValue = newValue;
        if (_newValue != null) {
            _newValueSpecified = true;
        }

    }

    /**
     * {@inheritDoc}
     */
    @Column(name = "old_value", length = 1024)
    public CharSequence getOldValue() {
        return _oldValue;
    }

    /**
     * Sets the optional old value. Side effect: if oldValue != null then
     * {@link #isOldValueSpecified()} will be set to true automatically.
     * 
     * @see #getOldValue()
     * 
     * @param oldValue
     *            old value to set.
     */
    public void setOldValue(CharSequence oldValue) {
        _oldValue = oldValue;
        if (_oldValue != null) {
            _oldValueSpecified = true;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Column(name = "property_name", length = 256, nullable = false)
    public CharSequence getPropertyName() {
        return _propertyName;
    }

    /**
     * Sets the required property name.
     * 
     * @see #getPropertyName()
     * 
     * @param propertyName
     *            the name of the property changed.
     */
    public void setPropertyName(CharSequence propertyName) {
        if (propertyName == null) {
            throw new IllegalArgumentException(
                    "Property name must not be null.");
        }
        _propertyName = propertyName;
    }

    /**
     * {@inheritDoc}
     */
    @Column(name = "property_type", length = 256, nullable = false)
    public CharSequence getPropertyType() {
        return _propertyType;
    }

    /**
     * Sets the required property type.
     * 
     * @see #getPropertyType()
     * 
     * @param propertyType
     *            the property type.
     */
    public void setPropertyType(CharSequence propertyType) {
        if (propertyType == null) {
            throw new IllegalArgumentException(
                    "Property type must not be null.");
        }
        _propertyType = propertyType;
    }

    /**
     * {@inheritDoc}
     */
    @Column(name = "new_value_specified")
    public boolean isNewValueSpecified() {
        return _newValueSpecified;
    }

    /**
     * Sets whether or not the new value is specified. If specified then any
     * value, including null, will be deemed intentional.
     * 
     * @see #isNewValueSpecified()
     * 
     * @param newValueSpecified
     */
    public void setNewValueSpecified(boolean newValueSpecified) {
        _newValueSpecified = newValueSpecified;
    }

    /**
     * {@inheritDoc}
     */
    @Column(name = "old_value_specified")
    public boolean isOldValueSpecified() {
        return _oldValueSpecified;
    }

    /**
     * Sets whether or not the old value is specified. If specified then any
     * value, including null, will be deemed intentional.
     * 
     * @see #isOldValueSpecified()
     * 
     * @param oldValueSpecified
     */
    public void setOldValueSpecified(boolean oldValueSpecified) {
        _oldValueSpecified = oldValueSpecified;
    }

    /**
     * {@inheritDoc}
     */
    @ManyToOne(optional = false, targetEntity = LifeCycleAuditEventVO.class)
    @JoinColumn(name = "life_cycle_audit_event_id", nullable = false)
    public LifeCycleAuditEvent getLifeCycleAuditEvent() {
        return _lifeCycleAuditEvent;
    }

    /**
     * Sets the required associated life cycle audit event.
     * 
     * @see #getLifeCycleAuditEvent()
     * 
     * @param lifeCycleAuditEvent
     *            the new event.
     */
    public void setLifeCycleAuditEvent(LifeCycleAuditEvent lifeCycleAuditEvent) {
        if (lifeCycleAuditEvent == null) {
            throw new IllegalArgumentException(
                    "Life Cycle Audit Event must not be null.");
        }
        _lifeCycleAuditEvent = lifeCycleAuditEvent;
    }

}
