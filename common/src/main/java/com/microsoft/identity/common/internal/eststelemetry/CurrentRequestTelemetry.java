package com.microsoft.identity.common.internal.eststelemetry;

public class CurrentRequestTelemetry extends RequestTelemetry {

    private String mApiId;

    private String mForceRefresh;

    String getApiId() {
        return mApiId;
    }

    String getForceRefresh() {
        return mForceRefresh;
    }

    CurrentRequestTelemetry() {
        super(Schema.CURRENT_SCHEMA_VERSION);
    }

    @Override
    public String getHeaderStringForFields() {
        return mApiId + "," + mForceRefresh;
    }

    void putInCommonTelemetry(final String key, final String value) {
        switch (key) {
            case Schema.Key.API_ID:
                mApiId = value;
                break;
            case Schema.Key.FORCE_REFRESH:
                mForceRefresh = value;
                break;
            default:
                break;
        }
    }
}
