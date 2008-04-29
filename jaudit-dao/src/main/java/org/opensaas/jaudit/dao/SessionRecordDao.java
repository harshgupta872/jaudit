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

import org.opensaas.jaudit.ResponsibleInformation;
import org.opensaas.jaudit.SessionRecord;
import org.opensaas.jaudit.SessionRecordMutable;

/**
 * Dao interface for working with {@link SessionRecord}s.
 * 
 * @param <T>
 *            the specific type of {@link SessionRecordMutable} in use.
 */
public interface SessionRecordDao<T extends SessionRecordMutable> extends
        GenericDao<T, String> {

    /**
     * Updates the session record's ended time stamp to that passed in.
     * 
     * @param sessionRecord
     *            the session record to update.
     * @param endedTs
     *            the date to update to.
     * @return the modified session record.
     */
    SessionRecord updateEndedTs(SessionRecord sessionRecord, Date endedTs);

    /**
     * Updates who is responsible for this session. This can only be updated if
     * the session's {@link SessionRecord#getEndedTs()} is null.
     * 
     * @param sessionRecord
     *            the session record to update.
     * @param responsibleInformation
     *            the entity responsible for the given session.
     * @return the modified session record.
     */
    SessionRecord updateResponsibleInformation(SessionRecord sessionRecord,
            ResponsibleInformation responsibleInformation);

}
