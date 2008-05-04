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
package org.opensaas.jaudit;

import org.opensaas.jaudit.test.BeanTest;
import org.opensaas.jaudit.test.ObjectFactory;

/**
 * Test class for {@link TransactionRecordVO}.
 */
public class TransactionRecordVOTest extends BeanTest<TransactionRecordVO> {

    static final ObjectFactory<TransactionRecordVO> FACTORY = new TransactionRecordFactory();

    /**
     * {@inheritDoc}
     */
    @Override
    protected ObjectFactory<TransactionRecordVO> getObjectFactory() {
        return FACTORY;
    }

    /**
     * An {@link ObjectFactory} for {@link TransactionRecordVO}s.
     */
    private static class TransactionRecordFactory implements
            ObjectFactory<TransactionRecordVO> {

        /**
         * {@inheritDoc}
         */
        public TransactionRecordVO createEquivalent() {
            return new TransactionRecordVO();
        }

        /**
         * {@inheritDoc}
         */
        public TransactionRecordVO createUnique() {
            final TransactionRecordVO tr = new TransactionRecordVO();

            tr.setTransactionId(STRING_FACTORY.createUnique());
            return tr;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Object[] getTestValues(Class<?> valueType) {

        if (SessionRecord.class.isAssignableFrom(valueType)) {
            return new Object[] { new SessionRecordVO(), null };
        }
        return super.getTestValues(valueType);
    }

}
