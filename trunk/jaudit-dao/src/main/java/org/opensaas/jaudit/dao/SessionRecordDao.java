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

import java.util.Date;

import org.opensaas.jaudit.SessionRecord;
import org.opensaas.jaudit.SessionRecordMutable;

/**
 * Dao interface for working with {@link SessionRecord}s.
 */
public interface SessionRecordDao extends
        GenericDao<SessionRecordMutable, String> {

    /**
     * Updates the session record's ended time stamp to that passed in.
     * 
     * @param sessionRecord
     *            The session record to update.
     * @param endedTs
     *            the date to update to.
     */
    void updateEndedTs(SessionRecordMutable sessionRecord, Date endedTs);

}
