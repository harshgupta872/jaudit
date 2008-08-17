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
package org.opensaas.jaudit.service.spring;

import org.opensaas.jaudit.service.ObjectFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.util.Assert;

/**
 * 
 */
public class ObjectFactoryCreatingFactoryBean extends AbstractFactoryBean {

    private String targetBeanName;

    /**
     * Set the name of the target bean.
     * <p>
     * The target does not <i>have</> to be a prototype bean, but
     * realisticially always will be (because if the target bean were a
     * singleton, then said singleton bean could simply be injected straight
     * into the dependent object, thus obviating the need for the extra level of
     * indirection afforded by the approach encapsulated by this class). Please
     * note that no exception will be thrown if the supplied
     * <code>targetBeanName</code> does not reference a prototype bean.
     */
    public void setTargetBeanName(String targetBeanName) {
        this.targetBeanName = targetBeanName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.hasText(this.targetBeanName,
                "Property 'targetBeanName' is required");
        super.afterPropertiesSet();
    }

    /**
     * {@inheritDoc}
     */
    public Class<?> getObjectType() {
        return ObjectFactory.class;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    protected Object createInstance() {

        return new ObjectFactory() {
            public Object getObject() throws BeansException {
                return getTargetBean(targetBeanName);
            }
        };
    }

    /**
     * Template method for obtaining a target bean instance. Called by the
     * exposed ObjectFactory's <code>getObject()</code> method.
     * 
     * @param targetBeanName
     *            the name of the target bean
     * @return the target bean instance
     */
    protected Object getTargetBean(String targetBeanName) {
        return getBeanFactory().getBean(targetBeanName);
    }

}
