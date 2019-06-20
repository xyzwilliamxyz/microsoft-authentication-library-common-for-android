// Copyright (c) Microsoft Corporation.
// All rights reserved.
//
// This code is licensed under the MIT License.
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files(the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and / or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions :
//
// The above copyright notice and this permission notice shall be included in
// all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
// THE SOFTWARE.
package com.microsoft.identity.common.internal.telemetry.rules;

import com.microsoft.identity.common.internal.telemetry.TelemetryEventStrings;
import com.microsoft.identity.common.internal.util.StringUtil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.microsoft.identity.common.internal.telemetry.TelemetryEventStrings.*;

final public class TelemetryPiiOiiRules {
    private TelemetryPiiOiiRules sInstance;
    private Set<String> piiPropertiesSet;
    private Set<String> oiiPropertiesSet;

    final private String piiArray[] = {
            TELEMETRY_KEY_USER_ID,
            TELEMETRY_KEY_DEVICE_ID,
            TELEMETRY_KEY_LOGIN_HINT,
            TELEMETRY_KEY_ERROR_DESCRIPTION,
            TELEMETRY_KEY_REQUEST_QUERY_PARAMS
    } ;

    final private String oiiArray[] = {
            TELEMETRY_KEY_TENANT_ID,
            TELEMETRY_KEY_CLIENT_ID,
            TELEMETRY_KEY_HTTP_PATH,
            TELEMETRY_KEY_AUTHORITY,
            TELEMETRY_KEY_IDP,
            TELEMETRY_KEY_APPLICATION_NAME,
            TELEMETRY_KEY_APPLICATION_VERSION
    };

    private TelemetryPiiOiiRules() {
        piiPropertiesSet = new HashSet<>(Arrays.asList(piiArray));
        oiiPropertiesSet = new HashSet<>(Arrays.asList(oiiArray));
    }

    public TelemetryPiiOiiRules getInstance() {
        if (sInstance == null) {
            sInstance = new TelemetryPiiOiiRules();
        }

        return sInstance;
    }

    /**
     * @param propertyName String of propertyName {@link TelemetryEventStrings}
     * @return true if the property belongs to Personally identifiable information. False otherwise.
     */
    public boolean isPii (final String propertyName) {
        if (StringUtil.isEmpty(propertyName)) {
            return false;
        }

        return piiPropertiesSet.contains(propertyName);
    }

    /**
     * @param propertyName String of propertyName {@link TelemetryEventStrings}
     * @return true if the property belongs to Objective identifiable information. False otherwise.
     */
    public boolean isOii (final String propertyName) {
        if (StringUtil.isEmpty(propertyName)) {
            return false;
        }

        return oiiPropertiesSet.contains(propertyName);
    }

    /**
     * @param propertyName String of propertyName {@link TelemetryEventStrings}
     * @return true if the property belongs to PII/OII. False otherwise.
     */
    public boolean isPiiOrOii (final String propertyName) {
        if (StringUtil.isEmpty(propertyName)) {
            return false;
        }

        return piiPropertiesSet.contains(propertyName) || oiiPropertiesSet.contains(propertyName);
    }
}