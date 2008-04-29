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

import java.io.Serializable;

import org.easymock.EasyMock;
import org.hibernate.Session;
import org.junit.Assert;
import org.junit.Test;
import org.opensaas.jaudit.test.ObjectFactory;

/**
 * GenericDaoHibernateTestBase is used to test subclasses of
 * {@link GenericDaoHibernate}.
 * 
 * @param <VO>
 *            Type of the VO being tested.
 * @param <PK>
 *            The primary key type of the VO being managed.
 * @param <DAO>
 *            The GenericDaoHibernate under test.
 */
public abstract class GenericDaoHibernateTestBase<VO, PK extends Serializable, DAO extends GenericDaoHibernate<VO, PK>> {

    /**
     * MockedGenericDao is used to mock a Hibernate session so that DAOs can be
     * tested without a database connection.
     * 
     * @param <T>
     *            The DAO under test.
     */
    protected class MockedGenericDao<T extends GenericDaoHibernate<?, ?>> {
        private Session mockSession_;

        private final T dao_;

        MockedGenericDao(final T dao) {
            dao_ = dao;
            dao_.setHibernateSessionFactory(new SessionFactory() {

                public <V, K extends Serializable> Session getSession(
                        final GenericDaoHibernate<V, K> hibernateDao) {
                    return getMockSession();
                }
            });
        }

        T getDao() {
            return dao_;
        }

        Session getMockSession() {
            if (mockSession_ == null) {
                mockSession_ = EasyMock.createStrictMock(Session.class);
            }
            return mockSession_;
        }

        void mockReplay() {
            if (mockSession_ != null) {
                EasyMock.replay(mockSession_);
            }
        }

        void mockReset() {
            if (mockSession_ != null) {
                EasyMock.reset(mockSession_);
            }
        }

        void mockVerify() {
            if (mockSession_ != null) {
                EasyMock.verify(mockSession_);
            }
        }
    }

    /**
     * Return a factory for generating DAOs.
     * 
     * @return a factory for generating DAOs.
     */
    public abstract ObjectFactory<DAO> getDAOFactory();

    /**
     * Return a factory for generating VOs.
     * 
     * @return a factory for generating VOs.
     */
    public abstract ObjectFactory<VO> getVOFactory();

    /**
     * Return a factory for generating primary keys for VOs.
     * 
     * @return a factory for generating primary keys for VOs.
     */
    public abstract ObjectFactory<PK> getPKFactory();

    /**
     * Test method for
     * {@link org.opensaas.jaudit.dao.hibernate.GenericDaoHibernate#GenericDaoHibernate(java.lang.Class)}.
     */
    @Test
    public void testGenericDaoHibernate() {
        final DAO dao = getDAOFactory().createUnique();
        Assert.assertEquals(getVOClass(), dao.getType());
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.dao.hibernate.GenericDaoHibernate#create(java.lang.Object)}.
     */
    @Test
    public void testCreate() {
        final DAO dao = getDAOFactory().createUnique();
        final MockedGenericDao<DAO> mock = new MockedGenericDao<DAO>(dao);

        final PK key1 = getPKFactory().createUnique();
        EasyMock.expect(mock.getMockSession().save(null)).andReturn(key1);

        final PK key2 = getPKFactory().createUnique();
        EasyMock.expect(
                mock.getMockSession().save(getVOFactory().createEquivalent()))
                .andReturn(key2);
        mock.mockReplay();

        Assert.assertEquals(key1, dao.create(null));
        Assert
                .assertEquals(key2, dao.create(getVOFactory()
                        .createEquivalent()));
        mock.mockVerify();
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.dao.hibernate.GenericDaoHibernate#read(java.io.Serializable)}.
     */
    @Test
    public void testRead() {
        final DAO dao = getDAOFactory().createUnique();
        final MockedGenericDao<DAO> mock = new MockedGenericDao<DAO>(dao);

        EasyMock.expect(
                mock.getMockSession().get(getVOClass(),
                        getPKFactory().createEquivalent())).andReturn(
                getVOFactory().createEquivalent());
        mock.mockReplay();

        Assert.assertEquals(getVOFactory().createEquivalent(), dao
                .read(getPKFactory().createEquivalent()));
        mock.mockVerify();
    }

    private Class<VO> voClass = null;

    @SuppressWarnings("unchecked")
    private Class<VO> getVOClass() {
        if (voClass == null) {
            voClass = (Class<VO>) getVOFactory().createEquivalent().getClass();
        }
        return voClass;
    }
}