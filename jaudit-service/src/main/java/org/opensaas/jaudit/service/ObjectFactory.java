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
package org.opensaas.jaudit.service;

/**
 * Defines a factory which can return an Object instance when invoked.
 * 
 * <p>
 * This interface is typically used to encapsulate a generic factory which
 * returns a new instance (prototype) of some target object on each invocation.
 * 
 * Borrowed from Spring's ObjectFactory but extended to have type safe
 * attributes.
 */
public interface ObjectFactory<T> {

    /**
     * Return an instance (possibly shared or independent) of the object managed
     * by this factory.
     * 
     * @return an instance of the bean (should never be <code>null</code>)
     */
    T getObject();

}
