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
package org.opensaas.exampleapp.action;

import java.util.List;

import org.appfuse.service.GenericManager;
import org.appfuse.webapp.action.BaseAction;
import org.opensaas.exampleapp.model.FooBar;
import org.opensaas.exampleapp.model.FooBarVO;
import org.springframework.beans.factory.annotation.Required;

public class FooBarAction extends BaseAction {

    /**
     * Generated Serial Id.
     */
    private static final long serialVersionUID = -4962888146356198754L;

    private GenericManager<FooBar, Long> _fooBarManager;

    @SuppressWarnings("unchecked")
    private List fooBars;

    private FooBar fooBar;

    private Long id;

    /**
     * @return the fooBars
     */
    @SuppressWarnings("unchecked")
    public List getFooBars() {
        return fooBars;
    }

    public String list() {
        fooBars = _fooBarManager.getAll();
        return SUCCESS;
    }

    /**
     * @param fooBarManager
     *            the fooBarManager to set
     */
    @Required
    public void setFooBarManager(GenericManager<FooBar, Long> fooBarManager) {
        _fooBarManager = fooBarManager;
    }

    /**
     * @return the fooBar
     */
    public FooBar getFooBar() {
        return fooBar;
    }

    /**
     * @param fooBar
     *            the fooBar to set
     */
    public void setFooBar(FooBar fooBar) {
        this.fooBar = fooBar;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    public String delete() {
        _fooBarManager.remove(fooBar.getId());
        saveMessage(getText("fooBar.deleted"));

        return SUCCESS;
    }

    public String edit() {
        if (id != null) {
            fooBar = _fooBarManager.get(id);
        } else {
            fooBar = new FooBarVO();
        }

        return SUCCESS;
    }

    public String save() throws Exception {
        if (cancel != null) {
            return "cancel";
        }

        if (delete != null) {
            return delete();
        }

        boolean isNew = (fooBar.getId() == null);

        fooBar = _fooBarManager.save(fooBar);

        String key = (isNew) ? "fooBar.added" : "fooBar.updated";
        saveMessage(getText(key));

        if (!isNew) {
            return INPUT;
        } else {
            return SUCCESS;
        }
    }

}
