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

package org.opensaas.jaudit.service.impl;

import org.opensaas.jaudit.SessionRecord;
import org.opensaas.jaudit.TransactionRecord;

/**
 * Implementors of this service will be able to interact with the system's
 * transaction manager to create {@link TransactionRecords}. Will be used by
 * {@link AuditServiceImpl}.
 * 
 * TODO: Finish this service interface.
 */
public interface TransactionRecordService {

    /**
     * Returns a new or saved TransactionRecord based on the system's
     * transaction manager.
     * 
     * @param sessionRecord
     * 
     * @return The new or saved TransactionRecord.
     */
    TransactionRecord getTransactionRecord(final SessionRecord sessionRecord);

}
