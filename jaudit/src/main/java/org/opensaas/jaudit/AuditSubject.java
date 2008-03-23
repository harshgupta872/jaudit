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
 * A simple representation of a uniquely identifable entity within an
 * application.
 * 
 */
public interface AuditSubject {

    /**
     * The unique identifier of the entity which is accountable for whatever is
     * being audited. Usually the actor.
     * 
     * TODO: Document some strategies for GUID/Unique id generation. Should
     * include the max length and content of the id. Maybe UUID?
     * 
     * @see #getSubjectType()
     * 
     * Optional.
     * 
     * @return the unique identifier of the audit subject.
     */
    CharSequence getId();

    /**
     * Returns the type of this entity.
     * 
     * @see #getId()
     * 
     * Optional. If not null, length less than or equal to 256.
     * 
     * @return the type of the audit subject.
     */
    CharSequence getSubjectType();

}
