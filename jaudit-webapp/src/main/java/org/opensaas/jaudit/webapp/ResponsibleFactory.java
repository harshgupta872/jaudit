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
package org.opensaas.jaudit.webapp;

import javax.servlet.http.HttpServletRequest;

import org.opensaas.jaudit.ResponsibleInformation;

/**
 * Delineates an interfaces primarily used by {@link AuditSessionFilter}. Will
 * be used to fill in {@link ResponsibleInformation} based upon the underlying
 * system's implementaiton of authentication and connectivity.
 */
public interface ResponsibleFactory {

    /**
     * Called when a new, empty responsible information needs to be filled in
     * with information. Implementors should try to fill in as many different
     * fields of the passed responsibleInformation as possible.
     * 
     * @param responsibleInformation
     *            empty responsible information.
     * @param request
     *            the HttpServletRequest to determine contextual information.
     * 
     */
    void fillInResponsible(ResponsibleInformation responsibleInformation,
            HttpServletRequest request);

    /**
     * Called when this factory should check if the responsibleInformation has
     * been changed like, for example, if an anonymous user became a registered
     * user.
     * 
     * @param responsibleInformation
     *            non-null but partially filled out responsible information to
     *            update.
     * 
     * @param request
     *            the HttpServletRequest to determine contextual information.
     * @return boolean did this implementation update the responsibleInformation
     *         object?
     */
    boolean updateResponsible(ResponsibleInformation responsibleInformation,
            HttpServletRequest request);

}
