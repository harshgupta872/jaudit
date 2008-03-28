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

import java.io.Serializable;

/**
 * A generic interface for a create/read dao.
 * 
 * @param <T>
 *            The type being managed by this dao.
 * @param <PK>
 *            The type of primary key for the type being managed by this dao.
 */
public interface GenericDao<T, PK extends Serializable> {

    /**
     * Save an unsaved instance of the type managed by this dao.
     * 
     * @param newInstance
     *            new instance to create.
     * @return the new primary key of the newly created instance.
     */
    PK create(T newInstance);

    /**
     * Read the instance by the passed primary key.
     * 
     * @param id
     *            the id to read by.
     * @return the instance found or null if none.
     */
    T read(PK id);
}
