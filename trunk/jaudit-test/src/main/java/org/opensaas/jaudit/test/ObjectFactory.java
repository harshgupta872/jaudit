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

import java.util.NoSuchElementException;

/**
 * A factory interface for easily creating objects for testing.
 * 
 * @param <T>
 *            The type of objects the factory will create.
 */
public interface ObjectFactory<T> {

    /**
     * Return a new instance of type T that is equivalent to every other
     * instance created by this method. Every object returned from the same
     * factory instance must obey these invariants:
     * <ul>
     * <li><code>assert createEquivalent() != null</code></li>
     * <li><code>assert createEquivalent().equals(createEquivalent())</code></li>
     * <li><code>assert createEquivalent() != createEquivalent()</code></li>
     * <li><code>assert !createEquivalent().equals({@link #createUnique()})</code></li>
     * <li><code>assert createEquivalent().getClass().equals(createEquivalent().getClass())</code></li>
     * <li><code>assert createEquivalent().getClass().equals({@link #createUnique()}.getClass())</code></li>
     * </ul>
     * 
     * @return a new instance of type T.
     */
    T createEquivalent();

    /**
     * Return a unique instance of type T. If there are multiple parameters that
     * can be set for an object, it is recommended that repeated calls to
     * createUnique() return various combinations; this is especially true for
     * members that are involved in {@link Object#equals(Object)} calculations
     * to get proper coverage of the various branches. For example, a factory
     * that generated instances of a Pair object might look like this:
     * 
     * <pre>
     * class PairFactory implements ObjectFactory&lt;Pair&gt; {
     *     private int counter = 0;
     * 
     *     final DefaultFactories.StringFactory factory = new DefaultFactories.StringFactory();
     * 
     *     public Pair createEquivalent() {
     *         return new Pair();
     *     }
     * 
     *     public Pair createUnique() {
     *         final Pair pair = new Pair();
     * 
     *         // create variations of pairs on successive calls
     *         counter = ++counter % 3;
     *         if (counter &lt; 2) {
     *             pair.setFirst(factory.createUnique());
     *         }
     *         if (counter &gt; 0) {
     *             pair.setSecond(factory.createUnique());
     *         }
     * 
     *         // note this guarantees that the default Pair() is never returned
     *         // as that is used in createEquivalent()
     *         return pair;
     *     }
     * }
     * </pre>
     * 
     * Every object returned from the same factory instance must obey these
     * invariants:
     * <ul>
     * <li><code>assert createUnique() != null</code></li>
     * <li><code>assert !createUnique().equals(createUnique())</code></li>
     * <li><code>assert createUnique() != createUnique()</code></li>
     * <li><code>assert !{@link #createEquivalent()}.equals(createUnique())</code></li>
     * <li><code>assert createUnique().getClass().equals(createUnique().getClass())</code></li>
     * <li><code>assert {@link #createEquivalent()}.getClass().equals(createUnique().getClass())</code></li>
     * </ul>
     * 
     * @return a new instance of type T.
     * @throws NoSuchElementException
     *             if unique object cannot be created.
     */
    T createUnique();
}
