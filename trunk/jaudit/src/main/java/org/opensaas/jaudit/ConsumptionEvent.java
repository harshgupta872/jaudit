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
 * A consumption event extends the concept of an {@link AuditEvent} to include
 * information specific to the idea of a resource being consumed (in partial or
 * whole). For example, in the case where a printer is printing 100 pages then
 * the resources that may be tracked for consumption are:
 * <ul>
 * <li>Paper</li>
 * <li>Ink</li>
 * <li>Electricity</li>
 * <li>Printer Time</li>
 * <li>Network Time</li>
 * </ul>
 */
public interface ConsumptionEvent extends AuditEvent {

    /**
     * Returns the amount of the {@link AuditEvent#getTarget()} consumed.
     * 
     * Required. Amount must be positive.
     * 
     * @return amount consumed.
     */
    Double getAmountConsumed();

    /**
     * Returns the scale to use for the amount {@link #getAmountConsumed()}.
     * The scale is the count of decimal digits in the fractional part, to the
     * right of the decimal point.
     * 
     * The number 23.5141 has a precision of 6 and a scale of 4. Integers can be
     * considered to have a scale of zero.
     * 
     * Optional. Default 0.
     * 
     * Negative or null numbers will be treated as 0.
     * 
     * @return
     */
    Integer getScale();

}
