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
package org.opensaas.jaudit.service.impl;

import java.util.UUID;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;

/**
 * A default implementation of {@link ObjectFactory} which return String
 * representations of UUID suitable to be used by
 * {@link AuditServiceImpl#setGuidFactory(ObjectFactory)}.
 */
public class UUIDStringFactory implements ObjectFactory {

    /**
     * Default required constructor.
     */
    public UUIDStringFactory() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    public Object getObject() throws BeansException {
        return UUID.randomUUID().toString();
    }

}
