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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * A defuault implementation and persistence mapping for
 * {@link PropertyValueChange}.
 * 
 */
@Entity
@Table(name = "property_value_changes")
public class PropertyValueChangeVO implements PropertyValueChange {

    private String _id;

    private String _newValue;

    private String _oldValue;

    private String _propertyName;

    private String _propertyType;

    private boolean _newValueSpecified = false;

    private boolean _oldValueSpecified = false;

    private LifeCycleAuditEvent _lifeCycleAuditEvent;

    /**
     * {@inheritDoc}
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public String getId() {
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
    public void setId(String id) {
        _id = id;
    }

    /**
     * {@inheritDoc}
     */
    @Column(name = "new_value", length = 1024)
    public String getNewValue() {
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
    public void setNewValue(String newValue) {
        _newValue = newValue;
        if (_newValue != null) {
            _newValueSpecified = true;
        }

    }

    /**
     * {@inheritDoc}
     */
    @Column(name = "old_value", length = 1024)
    public String getOldValue() {
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
    public void setOldValue(String oldValue) {
        _oldValue = oldValue;
        if (_oldValue != null) {
            _oldValueSpecified = true;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Column(name = "property_name", length = 255, nullable = false)
    public String getPropertyName() {
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
    public void setPropertyName(String propertyName) {
        _propertyName = propertyName;
    }

    /**
     * {@inheritDoc}
     */
    @Column(name = "property_type", length = 255, nullable = false)
    public String getPropertyType() {
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
    public void setPropertyType(String propertyType) {
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
    @JoinColumn(name = "life_cycle_audit_event", nullable = false)
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
        _lifeCycleAuditEvent = lifeCycleAuditEvent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        if (_id == null) {
            return super.hashCode();
        }
        return _id.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null || !(o instanceof PropertyValueChange)) {
            return false;
        }

        PropertyValueChange pvc = (PropertyValueChange) o;
        if (_id == null || pvc.getId() == null) {
            return false;
        }

        return _id.equals(pvc.getId());

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        final StringBuilder buff = new StringBuilder(255);
        buff.append("PropertyValueChange[property=");
        buff.append(_propertyName);
        buff.append(", type=");
        buff.append(_propertyType);
        if (_lifeCycleAuditEvent != null) {
            buff.append(", lifeCycleAuditEvent=");

            buff.append(_lifeCycleAuditEvent.getId());
        }
        buff.append(", id=");
        buff.append(_id);
        buff.append("]");
        return buff.toString();
    }

}
