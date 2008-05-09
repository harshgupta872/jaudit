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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.logging.Logger;

import org.junit.Assert;

/**
 * Asserts that ease the burden of testing serialization.
 */
public final class SerializableAssert extends LoopingTester {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = Logger
            .getLogger(SerializableAssert.class.getName());

    /**
     * Test that the objects created by the given {@link ObjectFactory} properly
     * follow the {@link Serializable} contract.
     * 
     * @param factory
     *            the factory used to generate objects for this test.
     * @throws IOException
     *             if an unexpected error occurs during serialization.
     * @throws ClassNotFoundException
     *             if an unexpected error occurs during serialization.
     */
    public static void testSerialization(
            final ObjectFactory<? extends Serializable> factory)
            throws IOException, ClassNotFoundException {
        testSerialization(factory.createEquivalent());
        for (int i = 0; i < LOOPS; ++i) {
            testSerialization(factory.createUnique());
        }
    }

    /**
     * Test that the given object can be successfully serialized/deserialized.
     * 
     * @param original
     *            the object under test.
     * @throws IOException
     *             if an unexpected error occurs during serialization.
     * @throws ClassNotFoundException
     *             if an unexpected error occurs during serialization.
     */
    public static void testSerialization(final Serializable original)
            throws IOException, ClassNotFoundException {
        // serialize
        final byte[] stream = serialize(original);

        // deserialize
        final Object copy = deserialize(stream);

        // test the result
        Assert.assertEquals("", original, copy);
    }

    /**
     * Created with package protection for testability.
     */
    /* package */SerializableAssert() {
        throw new UnsupportedOperationException(
                "Cannot construct utility class.");
    }

    private static byte[] serialize(final Serializable x) throws IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final ObjectOutputStream stream = new ObjectOutputStream(out);
        stream.writeObject(x);
        stream.close();
        return out.toByteArray();
    }

    private static Object deserialize(final byte[] bytes) throws IOException,
            ClassNotFoundException {
        final InputStream in = new ByteArrayInputStream(bytes);
        final ObjectInputStream stream = new ObjectInputStream(in);
        return stream.readObject();
    }

    /**
     * ${@inheritDoc}
     */
    @Override
    protected Logger getLogger() {
        return LOGGER;
    }
}
