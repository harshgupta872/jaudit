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
 * a business event. A Business event is a generic record of an important
 * business level event such as:
 * <ul>
 * <li>Store opening</li>
 * <li>New Feature Released</li>
 * <li>Marketing campaign started</li>
 * <li>IPO</li>
 * <li>Website unavailable</li>
 * <li>New CEO hired</li>
 * </ul>
 */
public interface BusinessAuditEvent extends AuditEvent {

    /**
     * Returns the business class of this event. Classes might include:
     * 
     * <ul>
     * <li>HR</li>
     * <li>IT</li>
     * <li>MARKETING</li>
     * <li>R_AND_D</li>
     * <li>SALES</li>
     * </ul>
     * 
     * The business class is just a way of classifying like events in a very
     * generic business sense.
     * 
     * Required. Less than or equal to 255 characters.
     * 
     * @return the business class
     */
    String getBusinessClass();

    /**
     * Returns the action executed. Actions might include:
     * 
     * <ul>
     * <li>HIRE</li>
     * <li>FIRE</li>
     * <li>NEW_RELEASE</li>
     * <li>NEW_SYSTEM_ONLINE</li>
     * </ul>
     * 
     * Optional. Less than or equal to 1024 characters.
     * 
     * @return the action executed by the subject on the target.
     */
    String getBusinessAction();

}
