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

import java.util.Collection;

/**
 * This interface allows basic attributes of an {@link LifeCycleAuditEvent} to
 * be changed.
 */
public interface LifeCycleAuditEventMutable extends LifeCycleAuditEvent,
        AuditEventMutable {

    /**
     * Updates the type of life cycle event that happened to the
     * {@link AuditEvent#getTarget() target}.
     * 
     * @param type
     *            the type of this event.
     * @see LifeCycleAuditEvent#getLifeCycleEventType()
     */
    void setLifeCycleEventType(LifeCycleType type);

    /**
     * Update the optional property value changes.
     * 
     * @param propertyValueChanges
     *            properties that changed.
     * @see LifeCycleAuditEvent#getPropertyValueChanges()
     */
    void setPropertyValueChanges(
            Collection<PropertyValueChange> propertyValueChanges);
}
