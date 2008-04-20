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

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.opensaas.jaudit.AuditSubject;
import org.opensaas.jaudit.ResponsibleInformation;
import org.opensaas.jaudit.SessionRecord;
import org.opensaas.jaudit.SessionRecordMutable;
import org.opensaas.jaudit.dao.SessionRecordDao;
import org.opensaas.jaudit.service.AuditService;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Required;

/**
 * The default implementation of {@link AuditService}.
 */
public class AuditServiceImpl implements AuditService {

    static private final Logger LOGGER = Logger
            .getLogger(AuditServiceImpl.class.getName());

    /**
     * For interacting with the persistence layer.
     */
    private SessionRecordDao _sessionRecordDao;

    /**
     * Will return new {@link ResponsibleInformation} objects.
     */
    private ObjectFactory _responsibleInformationFactory;

    /**
     * Will return new {@link SessionRecord} objects.
     */
    private ObjectFactory _sessionRecordFactory;

    /**
     * Will be used to fill in new session records.
     */
    private AuditSubject _auditSystem;

    /**
     * Sets the audit system address that will be filled in on new session
     * records.
     */
    private String _auditSystemAddress;

    /**
     * {@inheritDoc}
     */
    public ResponsibleInformation newResponsibleInformation() {
        return (ResponsibleInformation) _responsibleInformationFactory
                .getObject();
    }

    /**
     * {@inheritDoc}
     */
    public SessionRecord createSessionRecord(String sessionId,
            ResponsibleInformation responsibleInformation) {
        SessionRecordMutable sessionRecord = (SessionRecordMutable) _sessionRecordFactory
                .getObject();

        sessionRecord.setSessionId(sessionId);
        sessionRecord.setResponsibleInformation(responsibleInformation);
        sessionRecord.setStartedTs(new Date());
        sessionRecord.setSystem(_auditSystem);
        sessionRecord.setSystemAddress(_auditSystemAddress);

        String id = _sessionRecordDao.create(sessionRecord);
        LOGGER.log(Level.FINE, "Created new session record with id {0}.", id);
        return sessionRecord;
    }

    /**
     * {@inheritDoc}
     */
    public SessionRecord createSessionRecord() {
        return createSessionRecord(null, null);
    }

    /**
     * {@inheritDoc}
     */
    public SessionRecord sessionEnded(SessionRecord sessionRecord) {
        return _sessionRecordDao.updateEndedTs(sessionRecord, new Date());
    }

    /**
     * {@inheritDoc}
     */
    public SessionRecord updateResponsible(SessionRecord sessionRecord,
            ResponsibleInformation responsibleInformation) {
        return _sessionRecordDao.updateResponsibleInformation(sessionRecord,
                responsibleInformation);
    }

    /**
     * Sets the required responsible information factory.
     * 
     * @param responsibleInformationFactory
     */
    @Required
    public void setResponsibleInformationFactory(
            ObjectFactory responsibleInformationFactory) {
        _responsibleInformationFactory = responsibleInformationFactory;
    }

    /**
     * Sets the required session record dao which allows us to complete our
     * work.
     * 
     * @param sessionRecordDao
     *            SessionRecordDao to set.
     */
    @Required
    public void setSessionRecordDao(SessionRecordDao sessionRecordDao) {
        _sessionRecordDao = sessionRecordDao;
    }

    /**
     * Sets the required sessionRecordFactory which will give us new
     * SessionRecord objects.
     * 
     * @param sessionRecordFactory
     *            SessionRecordFactory to set.
     */
    @Required
    public void setSessionRecordFactory(ObjectFactory sessionRecordFactory) {
        _sessionRecordFactory = sessionRecordFactory;
    }

    /**
     * Sets the optional audit system.
     * 
     * @param auditSystem
     */
    public void setAuditSystem(AuditSubject auditSystem) {
        _auditSystem = auditSystem;
    }

    /**
     * Sets the optional audit system address.
     * 
     * @param auditySystemAddress
     */
    public void setAuditSystemAddress(String auditSystemAddress) {
        _auditSystemAddress = auditSystemAddress;
    }

}
