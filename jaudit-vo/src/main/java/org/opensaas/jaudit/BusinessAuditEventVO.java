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
import javax.persistence.Table;

/**
 * Default implementation and persistence mapping for an
 * {@link LifeCycleAuditEvent}.
 */
@Entity
@Table(name = "business_audit_events")
public class BusinessAuditEventVO extends AuditEventVO implements
        BusinessAuditEvent {

    private String _businessClass;

    /**
     * {@inheritDoc}
     */
    @Column(name = "business_class", length = 256, nullable = false)
    public String getBusinessClass() {
        return _businessClass;
    }

    /**
     * Sets the required business class.
     * 
     * @see #getBusinessClass()
     * 
     * @param businessClass
     *            the class to set.
     */
    public void setBusinessClass(String businessClass) {
        if (businessClass == null) {
            throw new IllegalArgumentException(
                    "Business class must not be null.");
        }
        _businessClass = businessClass;
    }

}
