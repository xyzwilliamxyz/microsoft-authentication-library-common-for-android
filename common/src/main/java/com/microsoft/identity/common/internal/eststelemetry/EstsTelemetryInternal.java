package com.microsoft.identity.common.internal.eststelemetry;

public class EstsTelemetryInternal {

    private EstsTelemetryContext mEstsTelemetryContext;
    private CurrentRequestTelemetry mCurrentRequestTelemetry;

    public EstsTelemetryInternal(
            EstsTelemetryContext estsTelemetryContext,
            CurrentRequestTelemetry currentRequestTelemetry) {
        this.mEstsTelemetryContext = estsTelemetryContext;
        this.mCurrentRequestTelemetry = currentRequestTelemetry;
    }

    public EstsTelemetryContext getEstsTelemetryContext() {
        return mEstsTelemetryContext;
    }

    public CurrentRequestTelemetry getCurrentRequestTelemetry() {
        return mCurrentRequestTelemetry;
    }
}
