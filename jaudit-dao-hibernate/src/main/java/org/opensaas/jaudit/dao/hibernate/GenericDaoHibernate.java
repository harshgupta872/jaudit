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

import org.hibernate.Session;
import org.opensaas.jaudit.dao.GenericDao;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * This is a default Hibernate 3 based implementation of the {@link GenericDao}
 * interface.
 * 
 * @param <T>
 *            Type of the VO being managed.
 * @param <PK>
 *            The primary key type of the VO being managed.
 */
public class GenericDaoHibernate<T, PK extends Serializable> extends
        HibernateDaoSupport implements GenericDao<T, PK> {

    /**
     * A factory that uses the dao's own {@link #getSession()} method to provide
     * a session.
     */
    private final static SessionFactory SESSION_FACTORY = new SessionFactory() {
        public <T, PK extends Serializable> Session getSession(
                GenericDaoHibernate<T, PK> dao) {
            return dao.getSession();
        }

    };

    private final Class<T> _type;

    private SessionFactory _sessionFactory = SESSION_FACTORY;

    /**
     * Default constructor with required type passed in.
     * 
     * @param type
     *            required type of the dao's managed object
     */
    public GenericDaoHibernate(final Class<T> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type is required.");
        }
        _type = type;
    }

    /**
     * {@inheritDoc}
     */
    public PK create(final T newInstance) {
        @SuppressWarnings("unchecked")
        final PK save = (PK) getMySession().save(newInstance);
        return save;
    }

    /**
     * {@inheritDoc}
     */
    public T read(final PK id) {
        @SuppressWarnings("unchecked")
        final T t = (T) getMySession().get(_type, id);
        return t;
    }

    /**
     * Return the type.
     * 
     * @return the type.
     */
    public final Class<T> getType() {
        return _type;
    }

    /**
     * Return the session factory currently in use.
     * 
     * @return the session factory currently in use.
     */
    public final SessionFactory getHibernateSessionFactory() {
        return _sessionFactory;
    }

    /**
     * Set the session factory to the given value. Used for testing.
     * 
     * @param sessionFactory
     *            the session factory to set
     */
    public final void setHibernateSessionFactory(
            final SessionFactory sessionFactory) {
        if (sessionFactory == null) {
            throw new IllegalArgumentException("factor cannot be null");
        }
        _sessionFactory = sessionFactory;
    }

    /**
     * Reset the session factory to the initial value. Used for testing.
     */
    public final void resetHibernateSessionFactory() {
        _sessionFactory = SESSION_FACTORY;
    }

    /**
     * Because {@link HibernateDaoSupport#getSession()} is declared
     * <tt>final</tt>, we supply a non-final version we can override for
     * testing.
     * 
     * @return The {@link Session} this instance should use.
     */
    /* package */Session getMySession()
            throws DataAccessResourceFailureException, IllegalStateException {
        return getHibernateSessionFactory().getSession(this);
    }
}
