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
package org.opensaas.jaudit.session;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.opensaas.jaudit.AuditSubject;
import org.opensaas.jaudit.ResponsibleInformation;
import org.opensaas.jaudit.SessionRecord;
import org.opensaas.jaudit.test.DefaultFactories;
import org.opensaas.jaudit.test.LoopingTester;
import org.opensaas.jaudit.test.ObjectAssert;
import org.opensaas.jaudit.test.ObjectFactory;
import org.opensaas.jaudit.test.ObjectFactoryAssert;
import org.opensaas.jaudit.test.SerializableAssert;

/**
 * Test class for {@link org.opensaas.jaudit.session.AuditSession}.
 */
public class AuditSessionTest extends LoopingTester {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = Logger
            .getLogger(AuditSessionTest.class.getName());

    @SuppressWarnings("serial")
    private static class TestSessionRecord implements SessionRecord {

        private static final ObjectFactory<String> STRING_FACTORY = new DefaultFactories.StringFactory();

        private final String id = STRING_FACTORY.createUnique();

        /**
         * {@inheritDoc}
         */
        public Date getEndedTs() {
            return null;
        }

        /**
         * {@inheritDoc}
         */
        public String getId() {
            return id;
        }

        /**
         * {@inheritDoc}
         */
        public ResponsibleInformation getResponsibleInformation() {
            return null;
        }

        /**
         * {@inheritDoc}
         */
        public String getSessionId() {
            return null;
        }

        /**
         * {@inheritDoc}
         */
        public Date getStartedTs() {
            return null;
        }

        /**
         * {@inheritDoc}
         */
        public AuditSubject getSystem() {
            return null;
        }

        /**
         * {@inheritDoc}
         */
        public String getSystemAddress() {
            return null;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return String.format("TestSessionRecord[id=%s]", id);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            return id.hashCode();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(final Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final TestSessionRecord other = (TestSessionRecord) obj;
            return id.equals(other.id);
        }
    }

    private static ObjectFactory<AuditSession> SESSION_FACTORY = new ObjectFactory<AuditSession>() {
        private SessionRecord equiv = new TestSessionRecord();

        /**
         * {@inheritDoc}
         */
        public AuditSession createEquivalent() {
            return new AuditSession(equiv);
        }

        /**
         * {@inheritDoc}
         */
        public AuditSession createUnique() {
            return new AuditSession(new TestSessionRecord());
        }
    };

    /**
     * Ensure that there isn't a Session still attached to the main thread after
     * executing.
     */
    @After
    public void clearThread() {
        AuditSession.removeAuditSession();
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.session.AuditSession#createAuditSession(org.opensaas.jaudit.SessionRecord)}.
     * 
     * @throws Exception
     *             if an error occurs.
     */
    @Test
    public void testCreateAuditSession() throws Exception {
        final Set<String> strings = new HashSet<String>();

        // create sessions in multiple threads
        LOOPS = 10;
        runInThreads(new Runnable() {
            public void run() {
                final AuditSession session = AuditSession
                        .createAuditSession(new TestSessionRecord());
                strings.add(session.toString());
                AuditSession.removeAuditSession();
            }
        }, 60L, 5);

        // all session strings should be unique, so...
        Assert.assertEquals(LOOPS, strings.size());
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.session.AuditSession#createAuditSession(org.opensaas.jaudit.SessionRecord)}.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCreateAuditSessionNull() {
        AuditSession.createAuditSession(null);
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.session.AuditSession#createAuditSession(org.opensaas.jaudit.SessionRecord)}.
     */
    @Test(expected = IllegalStateException.class)
    public void testCreateAuditSessionMultiple() {
        AuditSession.createAuditSession(new TestSessionRecord());
        AuditSession.createAuditSession(new TestSessionRecord());
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.session.AuditSession#getAuditSession()} and
     * {@link org.opensaas.jaudit.session.AuditSession#removeAuditSession()}.
     */
    @Test
    public void testAuditSession() {
        // starts as null
        Assert.assertNull(AuditSession.getAuditSession());

        // never changes once created...
        final AuditSession session = AuditSession
                .createAuditSession(new TestSessionRecord());
        Assert.assertSame(session, AuditSession.getAuditSession());
        Assert.assertSame(session, AuditSession.getAuditSession());
        Assert.assertSame(session, AuditSession.getAuditSession());

        // ...until it is removed
        AuditSession.removeAuditSession();
        Assert.assertNull(AuditSession.getAuditSession());

        // removing when empty causes no harm
        AuditSession.removeAuditSession();
        Assert.assertNull(AuditSession.getAuditSession());
    }

    /**
     * Test method for
     * {@link org.opensaas.jaudit.session.AuditSession#getSessionRecord()}.
     */
    @Test
    public void testGetSessionRecord() {
        final SessionRecord record = new TestSessionRecord();
        final AuditSession session = AuditSession.createAuditSession(record);
        Assert.assertSame(record, session.getSessionRecord());
    }

    /**
     * Test method for serialization.
     * 
     * @throws Exception
     *             if an error occurs.
     */
    @Test
    public void testSerialization() throws Exception {
        SerializableAssert.testSerialization(SESSION_FACTORY);
    }

    /**
     * Test method for hashCode() method.
     */
    @Test
    public void testHashCode() {
        ObjectAssert.assertHashCodeContract(SESSION_FACTORY);
    }

    /**
     * Test method for equals method.
     */
    @Test
    public void testEquals() {
        ObjectAssert.assertEqualsContract(SESSION_FACTORY);
    }

    /**
     * Verify that the factory we'll use for the other tests satisfies the
     * {@ObjectFactory} contract.
     */
    @Test
    public final void verifyFactory() {
        ObjectFactoryAssert.assertObjectFactoryContract(SESSION_FACTORY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Logger getLogger() {
        return LOGGER;
    }
}
