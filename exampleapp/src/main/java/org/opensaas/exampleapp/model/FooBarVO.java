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
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.appfuse.model.BaseObject;

@Entity
@Table(name = "foobar")
public class FooBarVO extends BaseObject implements FooBar {

    /**
     * Generated Serial Id.
     */
    private static final long serialVersionUID = -752949129473302614L;

    private Long _id;

    private String _name;

    private String _description;

    /**
     * {@inheritDoc}
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return _id;
    }

    /**
     * {@inheritDoc}
     */
    public void setId(Long id) {
        _id = id;
    }

    /**
     * {@inheritDoc}
     */
    @Column(name = "name", unique = true, nullable = false, length = 256)
    public String getName() {
        return _name;
    }

    /**
     * {@inheritDoc}
     */
    public void setName(String name) {
        _name = name;
    }

    /**
     * {@inheritDoc}
     */
    @Column(name = "description", length = 1024)
    public String getDescription() {
        return _description;
    }

    /**
     * {@inheritDoc}
     */
    public void setDescription(String description) {
        _description = description;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof FooBar)) {
            return false;
        }

        FooBar otherBar = (FooBar) o;

        EqualsBuilder eb = new EqualsBuilder();
        eb.append(_name, otherBar.getName());
        return eb.isEquals();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return _name != null ? _name.hashCode() : 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
