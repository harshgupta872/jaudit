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

import org.opensaas.jaudit.dao.GenericDao;
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

    private final Class<T> _type;

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
        PK save = (PK) getSession().save(newInstance);
        return save;
    }

    /**
     * {@inheritDoc}
     */
    public T read(final PK id) {
        @SuppressWarnings("unchecked")
        T t = (T) getSession().get(_type, id);
        return t;
    }
}
