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
package com.microsoft.identity.common.internal.providers.microsoft.azureactivedirectory;

import android.support.annotation.NonNull;

import com.microsoft.identity.common.adal.internal.util.StringExtensions;
import com.microsoft.identity.common.exception.ServiceException;
import com.microsoft.identity.common.internal.cache.SchemaUtil;
import com.microsoft.identity.common.internal.providers.oauth2.IDToken;
import com.microsoft.identity.common.internal.providers.oauth2.RefreshToken;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class AzureActiveDirectoryRefreshToken extends RefreshToken {

    private boolean mIsFamilyRefreshToken;
    private String mFamilyId;
    private Date mExpiresOn;
    private ClientInfo mClientInfo;
    private IDToken mIdToken;
    private String mScope;
    private String mClientId;

    /**
     * Constructor of AzureActiveDirectoryRefreshToken.
     *
     * @param response AzureActiveDirectoryTokenResponse
     */
    public AzureActiveDirectoryRefreshToken(
            @NonNull final AzureActiveDirectoryTokenResponse response) {
        super(response);
        try {
            mFamilyId = response.getFamilyId();
            mIsFamilyRefreshToken = !StringExtensions.isNullOrBlank(mFamilyId);
            mExpiresOn = response.getExpiresOn();
            mClientInfo = new ClientInfo(response.getClientInfo());
            mIdToken = new IDToken(response.getIdToken());
            mScope = response.getScope();
            mClientId = response.getClientId();
        } catch (ServiceException e) {
            // TODO handle this properly
            throw new RuntimeException(e);
        }
    }

    /**
     * @return true if the token is family refresh token, false otherwise.
     */
    public boolean getIsFamilyRefreshToken() {
        return mIsFamilyRefreshToken;
    }

    @Override
    public String getFamilyId() {
        return mFamilyId;
    }

    @Override
    public String getHomeAccountId() {
        return SchemaUtil.getHomeAccountId(mClientInfo);
    }

    @Override
    public String getEnvironment() {
        return SchemaUtil.getEnvironment(mIdToken);
    }

    @Override
    public String getClientId() {
        return mClientId;
    }

    @Override
    public String getSecret() {
        return getRefreshToken();
    }

    @Override
    public String getTarget() {
        return mScope;
    }

    @Override
    public String getExpiresOn() {
        String expiresOn = null;

        if (null != mExpiresOn) {
            final long millis = mExpiresOn.getTime();
            final long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
            expiresOn = String.valueOf(seconds);
        }

        return expiresOn;
    }

}