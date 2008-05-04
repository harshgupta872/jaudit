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
 * Default implementation and peristence mapping of
 * {@link ConsumptionAuditEvent}.
 * 
 */
@Entity
@Table(name = "consumption_events")
public class ConsumptionAuditEventVO extends AuditEventVO implements
        ConsumptionAuditEvent {

    private Double _amountConsumed;

    private Integer _scale = 0;

    /**
     * {@inheritDoc}
     */
    @Column(name = "amount_consumed", nullable = false)
    public Double getAmountConsumed() {
        return _amountConsumed;
    }

    /**
     * Sets the required amount consumed.
     * 
     * @see #getAmountConsumed()
     * 
     * @param amountConsumed
     *            to set.
     */
    public void setAmountConsumed(Double amountConsumed) {
        if (amountConsumed != null && amountConsumed < 0) {
            throw new IllegalArgumentException(
                    "Amount consumed can not be negative.");
        }
        _amountConsumed = amountConsumed;
    }

    /**
     * {@inheritDoc}
     */
    @Column(name = "scale")
    public Integer getScale() {
        return _scale;
    }

    /**
     * Sets the optional scale. Null is treated as 0.
     * 
     * @see #getScale()
     * 
     * @param scale
     *            scale to set.
     */
    public void setScale(Integer scale) {
        if (scale != null && scale < 0) {
            throw new IllegalArgumentException("Scale can not be negative.");
        }
        _scale = scale;
    }

}
