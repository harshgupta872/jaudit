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

import junit.framework.Assert;

import org.junit.Test;
import org.opensaas.jaudit.service.UUIDStringFactory;

/**
 * Default test for {@link UUIDStringFactory}.
 * 
 */
public class UUIDFactoryStringTest {

    /**
     * Test the constructor exists.
     */
    @Test
    public void testConstructor() {
        Assert.assertNotNull(new UUIDStringFactory());
    }

    /**
     * Tests that not null is returned by {@link UUIDStringFactory#getObject()}.
     */
    @Test
    public void testGetObject() {
        Assert.assertNotNull(new UUIDStringFactory().getObject());
    }

    /**
     * Tests that the factory is returning a String.
     */
    @Test
    public void testGetObjectIsString() {
        Assert.assertTrue(new UUIDStringFactory().getObject() instanceof String);
    }

}
