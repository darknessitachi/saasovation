package com.abigdreamer.infinity.ddd.domain.model.process;

import com.abigdreamer.infinity.ddd.domain.model.DomainEventPublisher;
import com.abigdreamer.infinity.ddd.domain.model.DomainEventSubscriber;
import com.abigdreamer.infinity.ddd.domain.model.process.ProcessId;
import com.abigdreamer.infinity.ddd.domain.model.process.TestableTimeConstrainedProcess;
import com.abigdreamer.infinity.ddd.domain.model.process.TestableTimeConstrainedProcessTimedOut;
import com.abigdreamer.infinity.ddd.domain.model.process.TimeConstrainedProcessTracker;
import com.abigdreamer.infinity.ddd.domain.model.process.Process.ProcessCompletionType;

import com.saasovation.common.CommonTestCase;

public class TimeConstrainedProcessTest extends CommonTestCase {

    private static final String TENANT_ID = "1234567890";

    private TestableTimeConstrainedProcess process;
    private boolean received;

    public TimeConstrainedProcessTest() {
        super();
    }

    public void testCompletedProcess() throws Exception {
        DomainEventPublisher.instance().subscribe(new DomainEventSubscriber<TestableTimeConstrainedProcessTimedOut>() {

            @Override
            public void handleEvent(TestableTimeConstrainedProcessTimedOut aDomainEvent) {
                received = true;
                process.informTimeout(aDomainEvent.occurredOn());
            }

            @Override
            public Class<TestableTimeConstrainedProcessTimedOut> subscribedToEventType() {
                return TestableTimeConstrainedProcessTimedOut.class;
            }
        });

        process = new TestableTimeConstrainedProcess(
                TENANT_ID,
                ProcessId.newProcessId(),
                "Testable Time Constrained Process",
                5000L);

        TimeConstrainedProcessTracker tracker =
                process.timeConstrainedProcessTracker();

        process.confirm1();

        assertFalse(received);
        assertFalse(process.isCompleted());
        assertFalse(process.didProcessingComplete());
        assertEquals(ProcessCompletionType.NotCompleted, process.processCompletionType());

        process.confirm2();

        assertFalse(received);
        assertTrue(process.isCompleted());
        assertTrue(process.didProcessingComplete());
        assertEquals(ProcessCompletionType.CompletedNormally, process.processCompletionType());
        assertNull(process.timedOutDate());

        tracker.informProcessTimedOut();

        assertFalse(received);
        assertFalse(process.isTimedOut());
    }
}
