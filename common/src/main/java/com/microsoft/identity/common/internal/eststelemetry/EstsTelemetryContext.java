package com.microsoft.identity.common.internal.eststelemetry;

/**
 * Class to hold data that would supplement capturing telemetry
 *  This data is not actually sent over the wire
 */
public class EstsTelemetryContext {

    private String mCorrelationId;
    private String mCommandType;
    private boolean mResultServicedFromCache = false; //only relevant to silent token requests;

    public EstsTelemetryContext(String mCorrelationId, String mCommandType) {
        this.mCorrelationId = mCorrelationId;
        this.mCommandType = mCommandType;
    }

    public void setIsResultServicedFromCache(boolean isResultServicedFromCache) {
        this.mResultServicedFromCache = isResultServicedFromCache;
    }

    public String getCorrelationId() {
        return mCorrelationId;
    }

    public String getCommandType() {
        return mCommandType;
    }

    public boolean isResultServicedFromCache() {
        return mResultServicedFromCache;
    }
}
