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

/**
 * A change in a property's value.
 * 
 */
public interface PropertyValueChange {

    /**
     * Returns the globally unique id of this property value change.
     * 
     * Required.
     * 
     * TODO: Document some strategies for GUID/Unique id generation. Should
     * include the max length and content of the id. Maybe UUID?
     * 
     * @return a globally unique id for this property value change.
     */
    CharSequence getId();

    /**
     * The name of the property which should be unique within the scope of the
     * entity that declares the property.
     * 
     * Required. Length less than or equal to 256;
     * 
     * @return the property name.
     */
    CharSequence getPropertyName();

    /**
     * Returns the data type of the property. If not one of the reserved types,
     * this should return a class name like <tt>com.foobar.Foo</tt>. Reserved
     * types include:
     * 
     * <ul>
     * <li>string</li>
     * <li>double</li>
     * <li>boolean</li>
     * <li>datetime</li>
     * <li>timezone</li>
     * <li>locale</li>
     * <li>reference</li>
     * </ul>
     * 
     * Required. Length is no longer than 256 characters.
     * 
     * TODO: We need to document the format of all of the above.
     * 
     * @return the old type.
     */
    CharSequence getPropertyType();

    /**
     * This is the old value of the property.
     * 
     * Optional. Null will mean two things based on the value returned by
     * {@link #isOldValueSpecified()}. Length is no longer than 1024
     * characters.
     * 
     * 
     * @return old value of the property.
     */
    CharSequence getOldValue();

    /**
     * Returns the new value of the property.
     * 
     * Optional. Null will mean two things based on the value returned by
     * {@link #isOldValueSpecified()}. Length is no longer than 1024
     * characters.
     * 
     * @return new value of the property.
     */
    CharSequence getNewValue();

    /**
     * Is there an old value actually specified or should the
     * {@link #getOldValue()} method be ignored?
     * 
     * @return is the old value specified.
     */
    boolean isOldValueSpecified();

    /**
     * Is there an new value actually specified or should the
     * {@link #getNewValue()} method be ignored?
     * 
     * @return is the new value specified.
     */
    boolean isNewValueSpecified();

    /**
     * Returns the required associated LifeCycleAuditEvent.
     * 
     * Required.
     * 
     * @return the associated life cycle audit event.
     */
    LifeCycleAuditEvent getLifeCycleAuditEvent();

}
