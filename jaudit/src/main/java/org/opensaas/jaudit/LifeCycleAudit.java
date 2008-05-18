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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to assign to methods to indicate a need to create life cycle audit
 * events when the method is executed.
 * 
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LifeCycleAudit {

    /**
     * The type of life cycle event that is occuring.
     * 
     * @return LifeCycleType
     */
    LifeCycleType type();

    /**
     * When this is not left at its default, indicates which class is the
     * subject's type. Useful for delete methods with a signature similar to
     * <tt>public void delete( Long myObjPrimaryKeyId );</tt>
     * 
     * OPTIONAL
     * 
     * @return Class<?> type of the subject.
     */
    Class<?> subjectType() default Void.class;

    /**
     * Returns a description to append to the life cycle audit event being
     * created.
     * 
     * OPTIONAL
     * 
     * @return String a description.
     */
    String description() default "";

}
