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
package com.microsoft.identity.common.internal.cache;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.microsoft.identity.common.BaseAccount;
import com.microsoft.identity.common.internal.dto.AccountRecord;
import com.microsoft.identity.common.internal.dto.Credential;
import com.microsoft.identity.common.internal.dto.RefreshTokenRecord;
import com.microsoft.identity.common.internal.logging.Logger;
import com.microsoft.identity.common.internal.providers.oauth2.AuthorizationRequest;
import com.microsoft.identity.common.internal.providers.oauth2.OAuth2Strategy;
import com.microsoft.identity.common.internal.providers.oauth2.TokenResponse;

public class MicrosoftFamilyOAuth2TokenCache
        <GenericOAuth2Strategy extends OAuth2Strategy,
                GenericAuthorizationRequest extends AuthorizationRequest,
                GenericTokenResponse extends TokenResponse,
                GenericAccount extends BaseAccount,
                GenericRefreshToken extends com.microsoft.identity.common.internal.providers.oauth2.RefreshToken>
        extends MsalOAuth2TokenCache<GenericOAuth2Strategy, GenericAuthorizationRequest, GenericTokenResponse, GenericAccount, GenericRefreshToken> {

    private static final String TAG = MicrosoftFamilyOAuth2TokenCache.class.getSimpleName();

    /**
     * Constructs a new OAuth2TokenCache.
     *
     * @param context The Application Context of the consuming app.
     */
    public MicrosoftFamilyOAuth2TokenCache(final Context context,
                                           final IAccountCredentialCache accountCredentialCache,
                                           final IAccountCredentialAdapter<
                                                   GenericOAuth2Strategy,
                                                   GenericAuthorizationRequest,
                                                   GenericTokenResponse,
                                                   GenericAccount,
                                                   GenericRefreshToken> accountCredentialAdapter) {
        super(context, accountCredentialCache, accountCredentialAdapter);
    }

    /**
     * Loads the tokens available for the supplied client criteria.
     *
     * @param clientId      The current client's id.
     * @param accountRecord The current account.
     * @return An ICacheRecord containing the account. If a matching refresh token is available
     * it is returned.
     */
    public ICacheRecord loadByFamilyId(@Nullable final String clientId,
                                       @NonNull final AccountRecord accountRecord) {
        final String methodName = ":loadByFamilyId";

        final String familyId = "1";

        Logger.verbose(
                TAG + methodName,
                "ClientId[" + clientId + ", " + familyId + "]"
        );

        // The following fields must match:
        // - environment
        // - home_account_id
        // - credential_type == RT
        //
        // The following fields do not matter:
        // - clientId doesn't matter (FRT)
        // - target doesn't matter (FRT)
        // - realm doesn't matter (MRRT)

        RefreshTokenRecord rtToReturn = null;

        // First, filter down to only the refresh tokens...
        for (final Credential credential : getAccountCredentialCache().getCredentials()) {
            if (credential instanceof RefreshTokenRecord) {
                final RefreshTokenRecord rtRecord = (RefreshTokenRecord) credential;

                if (familyId.equals(rtRecord.getFamilyId())
                        && accountRecord.getEnvironment().equals(rtRecord.getEnvironment())
                        && accountRecord.getHomeAccountId().equals(rtRecord.getHomeAccountId())) {
                    rtToReturn = rtRecord;
                    break;
                }
            }
        }

        final CacheRecord result = new CacheRecord();
        result.setAccount(accountRecord);
        result.setRefreshToken(rtToReturn);

        return result;
    }
}