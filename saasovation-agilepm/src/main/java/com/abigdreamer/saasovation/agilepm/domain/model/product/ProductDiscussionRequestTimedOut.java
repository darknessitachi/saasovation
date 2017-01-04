package com.abigdreamer.saasovation.agilepm.domain.model.product;

import com.abigdreamer.infinity.ddd.domain.model.process.ProcessId;
import com.abigdreamer.infinity.ddd.domain.model.process.ProcessTimedOut;

public class ProductDiscussionRequestTimedOut extends ProcessTimedOut {

    public ProductDiscussionRequestTimedOut(
            String aTenantId,
            ProcessId aProcessId,
            int aTotalRetriesPermitted,
            int aRetryCount) {

        super(aTenantId, aProcessId, aTotalRetriesPermitted, aRetryCount);
    }
}
