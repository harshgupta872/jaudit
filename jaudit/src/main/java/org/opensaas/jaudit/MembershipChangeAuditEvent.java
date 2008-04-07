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

/**
 * This interface extends upon an AuditEvent to include information specific to
 * the change of membership.
 */
public interface MembershipChangeAuditEvent extends AuditEvent {

    /**
     * This is the group that the {@link AuditEvent#getTarget()} is being added
     * or removed from.
     * 
     * Required.
     * 
     * @return the membership group.
     */
    AuditSubject getMembershipGroup();


    /**
     * Returns the type of this membership change event.
     * 
     * Required.
     * 
     * @return the type of membership change event.
     */
    MembershipChangeType getMembershipChangeEventType();

}
