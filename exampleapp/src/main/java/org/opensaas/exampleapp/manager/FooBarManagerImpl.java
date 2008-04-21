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
 package org.opensaas.exampleapp.manager;

import org.appfuse.dao.GenericDao;
import org.appfuse.service.impl.GenericManagerImpl;
import org.opensaas.exampleapp.model.FooBar;

public class FooBarManagerImpl extends GenericManagerImpl<FooBar, Long>
        implements FooBarManager {

    @SuppressWarnings("unused")
    private GenericDao<FooBar, Long> _fooBarDao;

    public FooBarManagerImpl(GenericDao<FooBar, Long> genericDao) {
        super(genericDao);
        _fooBarDao = genericDao;
    }

}
