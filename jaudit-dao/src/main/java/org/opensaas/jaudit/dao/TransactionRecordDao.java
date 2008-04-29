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
package org.opensaas.jaudit.dao;

import org.opensaas.jaudit.TransactionRecord;

/**
 * Dao interface for working with {@link TransactionRecord}s.
 */
public interface TransactionRecordDao extends
        GenericDao<TransactionRecord, String> {
    // no added behavior
}
