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
import java.util.logging.Logger;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;
import org.opensaas.jaudit.ResponsibleInformation;
import org.opensaas.jaudit.SessionRecord;
import org.opensaas.jaudit.test.DefaultFactories;
import org.opensaas.jaudit.test.ObjectFactory;
import org.opensaas.jaudit.test.ObjectFactoryAssert;

/**
 * Test class for
 * {@link org.opensaas.jaudit.dao.hibernate.SessionRecordDaoHibernate}.
 */
public class SessionRecordDaoHibernateTest
        extends
        GenericDaoHibernateTestBase<SimpleSessionRecord, Long, SessionRecordDaoHibernate<SimpleSessionRecord>> {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = Logger
            .getLogger(SessionRecordDaoHibernateTest.class.getName());

    private static final ObjectFactory<Long> LONG_FACTORY = new DefaultFactories.LongFactory();

    private static final ObjectFactory<SessionRecordDaoHibernate<SimpleSessionRecord>> DAO_FACTORY = new ObjectFactory<SessionRecordDaoHibernate<SimpleSessionRecord>>() {

        public SessionRecordDaoHibernate<SimpleSessionRecord> createEquivalent() {
            throw new UnsupportedOperationException();
        }

        public SessionRecordDaoHibernate<SimpleSessionRecord> createUnique() {
            return new SessionRecordDaoHibernate<SimpleSessionRecord>(
                    SimpleSessionRecord.class);
        }
    };

    private static final ObjectFactory<SimpleSessionRecord> VO_FACTORY = new ObjectFactory<SimpleSessionRecord>() {

        public SimpleSessionRecord createEquivalent() {
            return new SimpleSessionRecord(LONG_FACTORY.createEquivalent());
        }

        public SimpleSessionRecord createUnique() {
            return new SimpleSessionRecord(LONG_FACTORY.createUnique());
        }
    };

    /**
     * ${@inheritDoc}
     */
    @Override
    public ObjectFactory<SessionRecordDaoHibernate<SimpleSessionRecord>> getDAOFactory() {
        return DAO_FACTORY;
    }

    /**
     * ${@inheritDoc}
     */
    @Override
    public ObjectFactory<Long> getPKFactory() {
        return LONG_FACTORY;
    }

    /**
     * ${@inheritDoc}
     */
    @Override
    public ObjectFactory<SimpleSessionRecord> getVOFactory() {
        return VO_FACTORY;
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.dao.hibernate.SessionRecordDaoHibernate#updateEndedTs(org.opensaas.jaudit.SessionRecord, java.util.Date)}.
     */
    @Test
    public void testUpdateEndedTs1() {
        final Date ts = new Date();
        final SessionRecord record = checkUpdateEndedTs(ts);
        Assert.assertEquals(ts, record.getEndedTs());
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.dao.hibernate.SessionRecordDaoHibernate#updateEndedTs(org.opensaas.jaudit.SessionRecord, java.util.Date)}.
     */
    @Test
    public void testUpdateEndedTs2() {
        final SessionRecord record = checkUpdateEndedTs(null);
        Assert.assertNull(record.getEndedTs());
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.dao.hibernate.SessionRecordDaoHibernate#updateEndedTs(org.opensaas.jaudit.SessionRecord, java.util.Date)}.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUpdateEndedTsNull() {
        final SessionRecordDaoHibernate<SimpleSessionRecord> dao = DAO_FACTORY
                .createUnique();
        dao.updateEndedTs(null, new Date());
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.dao.hibernate.SessionRecordDaoHibernate#updateEndedTs(org.opensaas.jaudit.SessionRecord, java.util.Date)}.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUpdateEndedTsReadNull() {
        final SessionRecordDaoHibernate<SimpleSessionRecord> dao = DAO_FACTORY
                .createUnique();
        final MockedGenericDao<SessionRecordDaoHibernate<SimpleSessionRecord>> mock = new MockedGenericDao<SessionRecordDaoHibernate<SimpleSessionRecord>>(
                dao);

        final SessionRecord record = VO_FACTORY.createUnique();

        EasyMock.expect(
                mock.getMockSession().get(SimpleSessionRecord.class,
                        record.getId())).andReturn(null);
        mock.mockReplay();

        dao.updateEndedTs(record, new Date());
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.dao.hibernate.SessionRecordDaoHibernate#updateResponsibleInformation(org.opensaas.jaudit.SessionRecord, org.opensaas.jaudit.ResponsibleInformation)}.
     */
    @Test
    public void testUpdateResponsibleInformation1() {
        final ResponsibleInformation info = new ResponsibleInformation();
        final SessionRecord record = checkUpdateResponsibleInformation(info);
        Assert.assertEquals(info, record.getResponsibleInformation());
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.dao.hibernate.SessionRecordDaoHibernate#updateResponsibleInformation(org.opensaas.jaudit.SessionRecord, org.opensaas.jaudit.ResponsibleInformation)}.
     */
    @Test
    public void testUpdateResponsibleInformation2() {
        final SessionRecord record = checkUpdateResponsibleInformation(null);
        Assert.assertNull(record.getResponsibleInformation());
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.dao.hibernate.SessionRecordDaoHibernate#updateResponsibleInformation(org.opensaas.jaudit.SessionRecord, org.opensaas.jaudit.ResponsibleInformation)}.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUpdateResponsibleInformationNull() {
        final SessionRecordDaoHibernate<SimpleSessionRecord> dao = DAO_FACTORY
                .createUnique();
        dao.updateResponsibleInformation(null, new ResponsibleInformation());
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.dao.hibernate.SessionRecordDaoHibernate#updateResponsibleInformation(org.opensaas.jaudit.SessionRecord, org.opensaas.jaudit.ResponsibleInformation)}.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUpdateResponsibleInformationReadNull() {
        final SessionRecordDaoHibernate<SimpleSessionRecord> dao = DAO_FACTORY
                .createUnique();
        final MockedGenericDao<SessionRecordDaoHibernate<SimpleSessionRecord>> mock = new MockedGenericDao<SessionRecordDaoHibernate<SimpleSessionRecord>>(
                dao);

        final SessionRecord record = VO_FACTORY.createUnique();

        EasyMock.expect(
                mock.getMockSession().get(SimpleSessionRecord.class,
                        record.getId())).andReturn(null);
        mock.mockReplay();

        dao.updateResponsibleInformation(record, new ResponsibleInformation());
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.dao.hibernate.SessionRecordDaoHibernate#updateResponsibleInformation(org.opensaas.jaudit.SessionRecord, org.opensaas.jaudit.ResponsibleInformation)}.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUpdateResponsibleInformationTsSet() {
        final SessionRecordDaoHibernate<SimpleSessionRecord> dao = DAO_FACTORY
                .createUnique();
        final MockedGenericDao<SessionRecordDaoHibernate<SimpleSessionRecord>> mock = new MockedGenericDao<SessionRecordDaoHibernate<SimpleSessionRecord>>(
                dao);

        final SimpleSessionRecord record = VO_FACTORY.createUnique();
        record.setEndedTs(new Date());

        EasyMock.expect(
                mock.getMockSession().get(SimpleSessionRecord.class,
                        record.getId())).andReturn(record);
        mock.mockReplay();

        dao.updateResponsibleInformation(record, new ResponsibleInformation());
    }

    /**
     * Ensure factories are valid.
     */
    @Test
    public void testFactory() {
        ObjectFactoryAssert.assertObjectFactoryContract(VO_FACTORY);
        ObjectFactoryAssert.assertObjectFactoryContract(DAO_FACTORY);
    }

    private SessionRecord checkUpdateResponsibleInformation(
            final ResponsibleInformation info) {
        final SessionRecordDaoHibernate<SimpleSessionRecord> dao = DAO_FACTORY
                .createUnique();
        final MockedGenericDao<SessionRecordDaoHibernate<SimpleSessionRecord>> mock = new MockedGenericDao<SessionRecordDaoHibernate<SimpleSessionRecord>>(
                dao);

        final SessionRecord record = VO_FACTORY.createUnique();
        EasyMock.expect(
                mock.getMockSession().get(SimpleSessionRecord.class,
                        record.getId())).andReturn(record);
        EasyMock.expect(mock.getMockSession().save(record)).andReturn(
                record.getId());
        mock.mockReplay();

        Assert.assertNull(record.getEndedTs());
        Assert.assertSame(record, dao
                .updateResponsibleInformation(record, info));
        return record;
    }

    private SessionRecord checkUpdateEndedTs(final Date ts) {
        final SessionRecordDaoHibernate<SimpleSessionRecord> dao = DAO_FACTORY
                .createUnique();
        final MockedGenericDao<SessionRecordDaoHibernate<SimpleSessionRecord>> mock = new MockedGenericDao<SessionRecordDaoHibernate<SimpleSessionRecord>>(
                dao);

        final SessionRecord record = VO_FACTORY.createUnique();
        EasyMock.expect(
                mock.getMockSession().get(SimpleSessionRecord.class,
                        record.getId())).andReturn(record);
        EasyMock.expect(mock.getMockSession().save(record)).andReturn(
                record.getId());
        mock.mockReplay();

        Assert.assertNull(record.getEndedTs());
        Assert.assertSame(record, dao.updateEndedTs(record, ts));
        return record;
    }
}
