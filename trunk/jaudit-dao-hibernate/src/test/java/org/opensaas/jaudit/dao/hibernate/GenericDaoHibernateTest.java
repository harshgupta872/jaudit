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
import java.util.logging.Logger;

import org.hibernate.Session;
import org.junit.Assert;
import org.junit.Test;
import org.opensaas.jaudit.test.AccessorAssert;
import org.opensaas.jaudit.test.DefaultFactories;
import org.opensaas.jaudit.test.ObjectFactory;
import org.opensaas.jaudit.test.ObjectFactoryAssert;

/**
 * Test class for {@link org.opensaas.jaudit.dao.hibernate.GenericDaoHibernate}.
 */
public class GenericDaoHibernateTest
        extends
        GenericDaoHibernateTestBase<String, Integer, GenericDaoHibernate<String, Integer>> {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = Logger
            .getLogger(GenericDaoHibernateTest.class.getName());

    private static final ObjectFactory<String> STRING_FACTORY = new DefaultFactories.StringFactory();

    private static final ObjectFactory<Integer> INTEGER_FACTORY = new DefaultFactories.IntegerFactory();

    private static final ObjectFactory<GenericDaoHibernate<String, Integer>> DAO_FACTORY = new ObjectFactory<GenericDaoHibernate<String, Integer>>() {

        public GenericDaoHibernate<String, Integer> createEquivalent() {
            throw new UnsupportedOperationException();
        }

        public GenericDaoHibernate<String, Integer> createUnique() {
            return new GenericDaoHibernate<String, Integer>(String.class);
        }
    };

    private static class FakeSessionFactory implements SessionFactory {
        private final int id;

        FakeSessionFactory(final int id) {
            this.id = id;
        }

        /**
         * {@inheritDoc}
         */
        public <V, K extends Serializable> Session getSession(
                final GenericDaoHibernate<V, K> hibernateDao) {
            return null;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            return 37 * id;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(final Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final FakeSessionFactory other = (FakeSessionFactory) obj;
            return id == other.id;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ObjectFactory<GenericDaoHibernate<String, Integer>> getDAOFactory() {
        return DAO_FACTORY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ObjectFactory<Integer> getPKFactory() {
        return INTEGER_FACTORY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ObjectFactory<String> getVOFactory() {
        return STRING_FACTORY;
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.dao.hibernate.GenericDaoHibernate#setHibernateSessionFactory(SessionFactory)}.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSessionFactoryNull() {
        final GenericDaoHibernate<String, Integer> dao = DAO_FACTORY
                .createUnique();
        dao.setHibernateSessionFactory(null);
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.dao.hibernate.GenericDaoHibernate#getHibernateSessionFactory()},
     * {@link org.opensaas.jaudit.dao.hibernate.GenericDaoHibernate#setHibernateSessionFactory(SessionFactory)},
     * and
     * {@link org.opensaas.jaudit.dao.hibernate.GenericDaoHibernate#resetHibernateSessionFactory()}.
     * 
     * @throws Exception
     *             when an error occurs.
     */
    @Test
    public void testSessionFactory() throws Exception {
        final GenericDaoHibernate<String, Integer> dao = DAO_FACTORY
                .createUnique();
        final SessionFactory original = dao.getHibernateSessionFactory();
        Assert.assertNotNull(original);

        AccessorAssert.assertGetterAndSetter(dao, "setHibernateSessionFactory",
                SessionFactory.class, new FakeSessionFactory(1),
                new FakeSessionFactory(2), new FakeSessionFactory(3));

        Assert.assertTrue(!original.equals(dao.getHibernateSessionFactory()));
        dao.resetHibernateSessionFactory();
        Assert.assertEquals(original, dao.getHibernateSessionFactory());
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.dao.hibernate.GenericDaoHibernate#GenericDaoHibernate(java.lang.Class)}.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGenericDaoHibernateNull() {
        new GenericDaoHibernate<String, Integer>((Class<String>) null);
    }

    /**
     * Ensure factory is valid.
     */
    @Test
    public void testFactory() {
        ObjectFactoryAssert.assertObjectFactoryContract(DAO_FACTORY);
    }
}
