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

import java.math.BigInteger;
import java.util.NoSuchElementException;

/**
 * Popular {@link ObjectFactory} instances useful for testing.
 */
public final class DefaultFactories {

    /**
     * A factory for generating string objects.
     */
    public static final class StringFactory implements ObjectFactory<String> {
        // use BigInt to guarantee no wrapping regardless of the number of
        // objects created
        private BigInteger counter = BigInteger.ZERO;

        /**
         * {@inheritDoc}
         */
        public String createEquivalent() {
            // explicitly create an entirely new copy with each call
            return new String(toString());
        }

        /**
         * {@inheritDoc}
         */
        public String createUnique() {
            counter = counter.add(BigInteger.ONE);
            return String.format("%s:%s", this, counter);
        }
    }

    /**
     * A factory for generating integer objects.
     */
    public static final class IntegerFactory implements ObjectFactory<Integer> {
        // package protected instead of private for testing
        /* package */int counter = 0;

        /**
         * {@inheritDoc}
         */
        public Integer createEquivalent() {
            // explicitly create an entirely new copy with each call
            return new Integer(0);
        }

        /**
         * {@inheritDoc}
         */
        public Integer createUnique() {
            if (counter + 1 == 0) {
                throw new NoSuchElementException(
                        "Every possible integer value has already been returned by this factory");
            }
            return ++counter;
        }
    }

    /**
     * A factory for generating long objects.
     */
    public static final class LongFactory implements ObjectFactory<Long> {
        // package protected instead of private for testing
        /* package */long counter = 0;

        /**
         * {@inheritDoc}
         */
        public Long createEquivalent() {
            // explicitly create an entirely new copy with each call
            return new Long(0);
        }

        /**
         * {@inheritDoc}
         */
        public Long createUnique() {
            if (counter + 1L == 0L) {
                throw new NoSuchElementException(
                        "Every possible long value has already been returned by this factory");
            }
            return ++counter;
        }
    }

    /**
     * Created with package protection for testability.
     */
    /* package */DefaultFactories() {
        throw new UnsupportedOperationException(
                "Cannot construct utility class.");
    }
}
