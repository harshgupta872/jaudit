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
package org.opensaas.exampleapp.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public interface FooBar {

    /**
     * @return the id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long getId();

    /**
     * @param id
     *            the id to set
     */
    void setId(Long id);

    /**
     * @return the name
     */
    @Column(name = "name", unique = true, nullable = false, length = 256)
    String getName();

    /**
     * @param name
     *            the name to set
     */
    void setName(String name);

    /**
     * @return the description
     */
    @Column(name = "description", length = 1024)
    String getDescription();

    /**
     * @param description
     *            the description to set
     */
    void setDescription(String description);

}