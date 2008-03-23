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
 * This interface extends upon an AuditEvent to include information specific to
 * a life cycle event. Life cycle events are events that happen to a specific
 * {@link #getTarget()} like property changes, state changes, creation and
 * deletion.
 */
public interface LifeCycleAuditEvent extends AuditEvent {

    /**
     * Returns the type of life cycle event that happened to the
     * {@link #getTarget()}.
     * 
     * Required.
     * 
     * @return the type of this event.
     */
    LifeCycleEventType getLifeCycleEventType();

    /**
     * If this event is an {@link LifeCycleEventType#UPDATE} type, then this
     * collection should be non-null and return what properties were changed. If
     * empty or null, then no further information is available.
     * 
     * Optional.
     * 
     * @return properties that changed.
     */
    Collection<PropertyValueChange> getPropertyValueChanges();

}
