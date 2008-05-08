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

import java.util.logging.Logger;

import org.appfuse.dao.GenericDao;
import org.appfuse.service.impl.GenericManagerImpl;
import org.opensaas.exampleapp.model.FooBar;
import org.opensaas.jaudit.AuditTargetId;
import org.opensaas.jaudit.LifeCycleAudit;
import org.opensaas.jaudit.LifeCycleType;

public class FooBarManagerImpl extends GenericManagerImpl<FooBar, Long>
        implements FooBarManager {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = Logger
            .getLogger(FooBarManagerImpl.class.getName());

    @SuppressWarnings("unused")
    private final GenericDao<FooBar, Long> _fooBarDao;

    public FooBarManagerImpl(final GenericDao<FooBar, Long> genericDao) {
        super(genericDao);
        _fooBarDao = genericDao;
    }

    /**
     * ${@inheritDoc}
     */
    @Override
    @LifeCycleAudit(type = LifeCycleType.SAVE)
    public FooBar save(final FooBar object) {
        return super.save(object);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @LifeCycleAudit(type = LifeCycleType.DELETE)
    public void remove(@AuditTargetId(targetTypeClass = FooBar.class)
    final Long id) {
        super.remove(id);
    }

}
