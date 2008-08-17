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
package org.opensaas.jaudit.service;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.opensaas.jaudit.AuditEvent;
import org.opensaas.jaudit.AuditSubject;
import org.opensaas.jaudit.LifeCycleAuditEvent;
import org.opensaas.jaudit.LifeCycleAuditEventMutable;
import org.opensaas.jaudit.LifeCycleType;
import org.opensaas.jaudit.ResponsibleInformation;
import org.opensaas.jaudit.SessionRecord;
import org.opensaas.jaudit.SessionRecordMutable;
import org.opensaas.jaudit.TransactionRecord;
import org.opensaas.jaudit.TransactionRecordMutable;
import org.opensaas.jaudit.TransactionCompletionStatus;
import org.opensaas.jaudit.dao.LifeCycleAuditEventDao;
import org.opensaas.jaudit.dao.SessionRecordDao;
import org.opensaas.jaudit.dao.TransactionRecordDao;
import org.opensaas.jaudit.service.AuditService;
import org.opensaas.jaudit.service.ObjectFactory;
import org.opensaas.jaudit.session.AuditSession;

/**
 * The default implementation of {@link AuditService}.
 */
public class AuditServiceImpl implements AuditService {

    static private final Logger LOGGER = Logger
            .getLogger(AuditServiceImpl.class.getName());

    /**
     * For interacting with the persistence layer.
     */
    private SessionRecordDao<SessionRecordMutable> _sessionRecordDao;

    /**
     * For interacting with the persistence layer.
     */
    private TransactionRecordDao<TransactionRecordMutable> _transactionRecordDao;

    /**
     * The DAO for working with LifeCycleAuditEvent objects.
     */
    private LifeCycleAuditEventDao<LifeCycleAuditEventMutable> _lifeCycleAuditEventDao;

    /**
     * Will return new {@link ResponsibleInformation} objects.
     */
    private ObjectFactory<ResponsibleInformation> _responsibleInformationFactory;

    /**
     * Will return new {@link SessionRecord} objects.
     */
    private ObjectFactory<SessionRecord> _sessionRecordFactory;

    /**
     * Will return new {@link LifeCycleAuditEventMutable} objects.
     */
    private ObjectFactory<LifeCycleAuditEventMutable> _lifeCycleAuditEventFactory;

    /**
     * Will return new {@link TransactionRecordMutable} objects.
     */
    private ObjectFactory<TransactionRecordMutable> _transactionRecordFactory;

    /**
     * Will return new globally unique id objects represented by a
     * {@link String}.
     * 
     * @see AuditEvent#getId()
     * @see TransactionRecord#getId()
     * @see SessionRecord#getId()
     * 
     */
    private ObjectFactory<String> _guidFactory;

    /**
     * Will be used to fill in new session records.
     */
    private AuditSubject _auditSystem;

    /**
     * The audit system address that will be filled in on new session records.
     */
    private String _auditSystemAddress;
    
    public AuditServiceImpl() {
        super();
    }

    /**
     * To be called to check on the viability of our properites.
     */
    public void afterPropertiesSet() {

        // TODO: Check

    }

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
    public TransactionRecord createTransactionRecord(
            final String transactionId, final SessionRecord sessionRecord) {
        if (transactionId == null) {
            throw new IllegalArgumentException("Transaction id is required.");
        }
        final TransactionRecordMutable trm = (TransactionRecordMutable) _transactionRecordFactory
                .getObject();
        trm.setId((String) _guidFactory.getObject());
        trm.setStartedTs(new Date());
        trm.setTransactionId(transactionId);
        trm.setSessionRecord(sessionRecord);

        final String id = _transactionRecordDao.create(trm);

        LOGGER.log(Level.FINE, "Created new transaction record with id {0}.",
                id);

        return trm;
    }

    /**
     * {@inheritDoc}
     */
    public SessionRecord createSessionRecord(final String sessionId,
            final ResponsibleInformation responsibleInformation) {
        final SessionRecordMutable sessionRecord = (SessionRecordMutable) _sessionRecordFactory
                .getObject();

        sessionRecord.setId((String) _guidFactory.getObject());
        sessionRecord.setSessionId(sessionId);
        sessionRecord.setResponsibleInformation(responsibleInformation);
        sessionRecord.setStartedTs(new Date());
        sessionRecord.setSystem(_auditSystem);
        sessionRecord.setSystemAddress(_auditSystemAddress);

        final String id = _sessionRecordDao.create(sessionRecord);
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
    public SessionRecord updateSessionEnded(final SessionRecord sessionRecord) {

        final SessionRecord sr = _sessionRecordDao.updateEndedTs(sessionRecord,
                new Date());
        return sr;

    }

    /**
     * {@inheritDoc}
     */
    public TransactionRecord updateTransactionEnded(
            final TransactionRecord transactionRecord,
            final TransactionCompletionStatus transactionStatus, final Date endedTs) {

        final TransactionRecord tr = _transactionRecordDao.transactionEnded(
                transactionRecord, transactionStatus, endedTs);

        if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER
                    .log(
                            Level.FINE,
                            "Updated transaction record with id {0}.  New status={1}, endedTs={2}",
                            new Object[] { tr.getId(), transactionStatus,
                                    endedTs });
        }

        return tr;

    }

    /**
     * {@inheritDoc}
     */
    public SessionRecord updateResponsible(final SessionRecord sessionRecord,
            final ResponsibleInformation responsibleInformation) {

        final SessionRecord sr = _sessionRecordDao
                .updateResponsibleInformation(sessionRecord,
                        responsibleInformation);

        return sr;

    }

    /**
     * {@inheritDoc}
     */
    public LifeCycleAuditEvent createLifeCycleAuditEvent(
            final LifeCycleType type, final AuditSubject target,
            final String description) {
        final LifeCycleAuditEventMutable event = (LifeCycleAuditEventMutable) _lifeCycleAuditEventFactory
                .getObject();

        event.setId((String) _guidFactory.getObject());
        event.setDescription(description);
        event.setLifeCycleEventType(type);
        event.setTarget(target);
        event.setTs(new Date());
        event.setSessionRecord(AuditSession.getAuditSession()
                .getSessionRecord());

        final String id = _lifeCycleAuditEventDao.create(event);
        LOGGER.log(Level.FINE,
                "Created new life cycle audit event with id {0}.", id);
        return event;
    }

    /**
     * Sets the required responsible information factory.
     * 
     * @param responsibleInformationFactory
     */
    public void setResponsibleInformationFactory(
            final ObjectFactory<ResponsibleInformation> responsibleInformationFactory) {
        _responsibleInformationFactory = responsibleInformationFactory;
    }

    /**
     * Sets the required session record dao which allows us to complete our
     * work.
     * 
     * @param sessionRecordDao
     *            SessionRecordDao to set.
     */
    public void setSessionRecordDao(
            final SessionRecordDao<SessionRecordMutable> sessionRecordDao) {
        _sessionRecordDao = sessionRecordDao;
    }

    /**
     * Sets the required life cycle event dao which allows us to complete our
     * work.
     * 
     * @param dao
     *            Dao to set.
     */
    public void setLifeCycleAuditEventDao(
            final LifeCycleAuditEventDao<LifeCycleAuditEventMutable> dao) {
        _lifeCycleAuditEventDao = dao;
    }

    /**
     * Sets the required sessionRecordFactory which will give us new
     * SessionRecord objects.
     * 
     * @param sessionRecordFactory
     *            SessionRecordFactory to set.
     */
    public void setSessionRecordFactory(
            final ObjectFactory<SessionRecord> sessionRecordFactory) {
        _sessionRecordFactory = sessionRecordFactory;
    }

    /**
     * Sets the required lifeCycleAuditEventFactory which will give us new
     * LifeCycleAuditEvent objects.
     * 
     * @param lifeCycleAuditEventFactory
     *            LifeCycleAuditEventFactory to set.
     */
    public void setLifeCycleAuditEventFactory(
            final ObjectFactory<LifeCycleAuditEventMutable> lifeCycleAuditEventFactory) {
        _lifeCycleAuditEventFactory = lifeCycleAuditEventFactory;
    }

    /**
     * Sets the required guid factory which returns globally unique ids of type
     * String.
     * 
     * @param guidFactory
     *            the guidFactory to set.
     */
    public void setGuidFactory(final ObjectFactory<String> guidFactory) {
        _guidFactory = guidFactory;
    }

    /**
     * Sets the optional audit system.
     * 
     * @param auditSystem
     */
    public void setAuditSystem(final AuditSubject auditSystem) {
        _auditSystem = auditSystem;
    }

    /**
     * Sets the optional audit system address.
     * 
     * @param auditSystemAddress
     */
    public void setAuditSystemAddress(final String auditSystemAddress) {
        _auditSystemAddress = auditSystemAddress;
    }

    /**
     * Sets the required transaction record factory that will create
     * {@link TransactionRecordMutable}.
     * 
     * @param transactionRecordFactory
     */
    public void setTransactionRecordFactory(
            final ObjectFactory<TransactionRecordMutable> transactionRecordFactory) {
        _transactionRecordFactory = transactionRecordFactory;
    }

    /**
     * Sets the required transaction record dao.
     * 
     * @param transactionRecordDao
     */
    public void setTransactionRecordDao(
            final TransactionRecordDao<TransactionRecordMutable> transactionRecordDao) {
        _transactionRecordDao = transactionRecordDao;
    }

}
