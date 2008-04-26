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
package org.opensaas.jaudit.test;

import java.io.IOException;
import java.io.NotSerializableException;
import java.io.Serializable;

import org.junit.Test;

/**
 * Test class for {@link org.opensaas.jaudit.test.SerializableAssert}.
 */
public class SerializableAssertTest {

    private static class NotSerializable implements Serializable {
        private void writeObject(final java.io.ObjectOutputStream out)
                throws IOException {
            throw new NotSerializableException();
        }

        private void readObject(final java.io.ObjectInputStream in)
                throws IOException {
            throw new NotSerializableException();
        }
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.SerializableAssert#testSerialization(org.opensaas.jaudit.test.ObjectFactory)}.
     * 
     * @throws Exception
     *             when something unexpected happens.
     */
    @Test
    public void testTestSerialization() throws Exception {
        SerializableAssert
                .testSerialization(new DefaultFactories.StringFactory());
        SerializableAssert
                .testSerialization(new DefaultFactories.IntegerFactory());
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.SerializableAssert#testSerialization(org.opensaas.jaudit.test.ObjectFactory)}.
     * 
     * @throws Exception
     *             when something unexpected happens.
     */
    @Test(expected = NullPointerException.class)
    public void testTestSerializationNullFactory() throws Exception {
        SerializableAssert
                .testSerialization((ObjectFactory<Serializable>) null);
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.SerializableAssert#testSerialization(java.io.Serializable)}.
     * 
     * @throws Exception
     *             when something unexpected happens.
     */
    @Test(expected = NotSerializableException.class)
    public void testTestSerializationFails() throws Exception {
        SerializableAssert.testSerialization(new NotSerializable());
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.SerializableAssert#testSerialization(java.io.Serializable)}.
     * 
     * @throws Exception
     *             when something unexpected happens.
     */
    @Test
    public void testTestSerializationNull() throws Exception {
        SerializableAssert.testSerialization((Serializable) null);
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.test.SerializableAssert#SerializableAssert()}.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testSerializableAssert() {
        new SerializableAssert();
    }

}
