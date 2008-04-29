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

/**
 * A SessionFactory can generate a Hibernate {@link org.hibernate.Session}
 * object for a {@link GenericDaoHibernate} instance.
 */
public interface SessionFactory {
    /**
     * Return a hibernate session for use by the given dao.
     * 
     * @param dao
     *            The dao that needs a session.
     * @param <T>
     *            The type managed by the DAO.
     * @param <PK>
     *            The primary key for the type managed by the DAO.
     * @return a hibernate session.
     */
    <T, PK extends Serializable> Session getSession(
            GenericDaoHibernate<T, PK> dao);
}
