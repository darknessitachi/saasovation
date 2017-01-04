package com.abigdreamer.infinity.ddd.domain.model.process;

import java.util.UUID;

import com.abigdreamer.infinity.ddd.domain.model.AbstractId;


public final class ProcessId extends AbstractId {

    private static final long serialVersionUID = 1L;

    public static ProcessId existingProcessId(String anId) {
        ProcessId processId = new ProcessId(anId);

        return processId;
    }

    public static ProcessId newProcessId() {
        ProcessId processId =
                new ProcessId(UUID.randomUUID().toString().toLowerCase());

        return processId;
    }

    protected ProcessId(String anId) {
        super(anId);
    }

    protected ProcessId() {
        super();
    }

    @Override
    protected int hashOddValue() {
        return 3773;
    }

    @Override
    protected int hashPrimeValue() {
        return 43;
    }
}
