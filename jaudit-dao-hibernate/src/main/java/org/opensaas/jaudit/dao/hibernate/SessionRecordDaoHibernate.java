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
package org.opensaas.jaudit.dao.hibernate;

import java.util.Date;

import org.opensaas.jaudit.ResponsibleInformation;
import org.opensaas.jaudit.SessionRecord;
import org.opensaas.jaudit.SessionRecordMutable;
import org.opensaas.jaudit.dao.SessionRecordDao;

/**
 * Default hibernate implementation of {@link SessionRecordDao}.
 * 
 * @param <T>
 *            the specific type of {@link SessionRecordMutable} in use.
 */
public class SessionRecordDaoHibernate<T extends SessionRecordMutable> extends
        GenericDaoHibernate<T, String> implements SessionRecordDao<T> {

    /**
     * Required constructor.
     * 
     * @param type
     *            the type we are managing.
     */
    public SessionRecordDaoHibernate(final Class<T> type) {
        super(type);
    }

    /**
     * {@inheritDoc}
     */
    public SessionRecord updateEndedTs(final SessionRecord sessionRecord,
            final Date endedTs) {
        if (sessionRecord == null) {
            throw new IllegalArgumentException(
                    "Session record must not be null.");
        }

        final SessionRecordMutable sr = read(sessionRecord.getId());
        if (sr == null) {
            throw new IllegalArgumentException(
                    "Session record does not exist in persistence.");
        }

        sr.setEndedTs(endedTs);

        return (SessionRecord) getMySession().save(sr);
    }

    /**
     * {@inheritDoc}
     */
    public SessionRecord updateResponsibleInformation(
            final SessionRecord sessionRecord,
            final ResponsibleInformation responsibleInformation) {
        if (sessionRecord == null) {
            throw new IllegalArgumentException(
                    "Session record must not be null.");
        }

        final SessionRecordMutable sr = read(sessionRecord.getId());
        if (sr == null) {
            throw new IllegalArgumentException(
                    "Session record does not exist in persistence.");
        }

        if (sr.getEndedTs() != null) {
            throw new IllegalArgumentException(
                    "Session record has already been ended at: "
                            + sr.getEndedTs());
        }

        sr.setResponsibleInformation(responsibleInformation);

        return (SessionRecord) getMySession().save(sr);
    }
}
