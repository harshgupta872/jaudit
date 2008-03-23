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
 * The different types of life cycle events.
 * 
 * @see LifeCycleAuditEvent
 * 
 */
public enum LifeCycleEventType {

    /**
     * An entity was created. Example: An account is created.
     */
    CREATE,

    /**
     * An entity's properties were updated. Example: An account name is updated.
     */
    UPDATE,

    /**
     * An entity was deleted. Example: An account is delted.
     */
    DELETE,

    /**
     * An entity's state is changed. Example: a system is shut down.
     */
    STATE_CHANGE

}
