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

import android.net.Uri;
import android.support.annotation.NonNull;

import com.microsoft.identity.common.exception.ServiceException;
import com.microsoft.identity.common.internal.logging.Logger;
import com.microsoft.identity.common.internal.net.HttpResponse;
import com.microsoft.identity.common.internal.net.ObjectMapper;
import com.microsoft.identity.common.internal.providers.microsoft.MicrosoftTokenErrorResponse;
import com.microsoft.identity.common.internal.providers.oauth2.AuthorizationResponse;
import com.microsoft.identity.common.internal.providers.oauth2.AuthorizationStrategy;
import com.microsoft.identity.common.internal.providers.oauth2.IDToken;
import com.microsoft.identity.common.internal.providers.oauth2.OAuth2Strategy;
import com.microsoft.identity.common.internal.providers.oauth2.TokenErrorResponse;
import com.microsoft.identity.common.internal.providers.oauth2.TokenResponse;
import com.microsoft.identity.common.internal.providers.oauth2.TokenResult;

import java.net.HttpURLConnection;

/**
 * The Azure Active Directory OAuth 2.0 Strategy.
 */
public class AzureActiveDirectoryOAuth2Strategy
        extends OAuth2Strategy<
        AzureActiveDirectoryAccessToken,
        AzureActiveDirectoryAccount,
        AzureActiveDirectoryAuthorizationRequest,
        AuthorizationResponse,
        AuthorizationStrategy,
        AzureActiveDirectoryOAuth2Configuration,
        AzureActiveDirectoryRefreshToken,
        AzureActiveDirectoryTokenRequest,
        AzureActiveDirectoryTokenResponse,
        TokenResult> {

    private static final String TAG = AzureActiveDirectoryOAuth2Strategy.class.getSimpleName();

    /**
     * Constructor of AzureActiveDirectoryOAuth2Strategy.
     * <<<<<<< HEAD
     * <p>
     * =======
     * >>>>>>> dev
     *
     * @param config Azure Active Directory OAuth2 configuration
     */
    public AzureActiveDirectoryOAuth2Strategy(final AzureActiveDirectoryOAuth2Configuration config) {
        super(config);
        Logger.verbose(TAG, "Init: " + TAG);
        setTokenEndpoint("https://login.microsoftonline.com/microsoft.com/oauth2/token");
    }

    @Override
    public String getIssuerCacheIdentifier(final AzureActiveDirectoryAuthorizationRequest authRequest) {
        final String methodName = "getIssuerCacheIdentifier";
        Logger.entering(TAG, methodName, authRequest);

        final AzureActiveDirectoryCloud cloud = AzureActiveDirectory.getAzureActiveDirectoryCloud(authRequest.getAuthority());

        if (!cloud.isValidated() && getOAuth2Configuration().isAuthorityHostValidationEnabled()) {
            Logger.warn(TAG + ":" + methodName, "Authority host validation has been enabled. This data hasn't been validated, though.");
            // We have invalid cloud data... and authority host validation is enabled....
            // TODO: Throw an exception in this case... need to see what ADAL does in this case.
        }

        if (!cloud.isValidated() && !getOAuth2Configuration().isAuthorityHostValidationEnabled()) {
            Logger.warn(
                    TAG + ":" + methodName,
                    "Authority host validation not specified..."
                            + "but there is no cloud..."
                            + "Hence just return the passed in Authority"
            );
            return authRequest.getAuthority().toString();
        }

        Uri authorityUri = Uri.parse(authRequest.getAuthority().toString())
                .buildUpon()
                .authority(cloud.getPreferredCacheHostName())
                .build();

        final String issuerCacheIdentifier = authorityUri.toString();

        Logger.exiting(TAG, methodName, issuerCacheIdentifier);

        return issuerCacheIdentifier;
    }

    @Override
    public AzureActiveDirectoryAccessToken getAccessTokenFromResponse(
            @NonNull final AzureActiveDirectoryTokenResponse response) {
        final String methodName = "getAccessTokenFromResponse";
        Logger.entering(TAG, methodName, response);

        final AzureActiveDirectoryAccessToken at = new AzureActiveDirectoryAccessToken(response);

        Logger.exiting(TAG, methodName, at);

        return at;
    }

    @Override
    public AzureActiveDirectoryRefreshToken getRefreshTokenFromResponse(
            @NonNull final AzureActiveDirectoryTokenResponse response) {
        final String methodName = "getRefreshTokenFromResponse";
        Logger.entering(TAG, methodName, response);

        final AzureActiveDirectoryRefreshToken rt = new AzureActiveDirectoryRefreshToken(response);

        Logger.exiting(TAG, methodName, rt);

        return rt;
    }

    /**
     * Stubbed out for now, but should create a new AzureActiveDirectory account.
     * Should accept a parameter (TokenResponse) for producing that user
     *
     * @return
     */
    @Override
    public AzureActiveDirectoryAccount createAccount(
            @NonNull final AzureActiveDirectoryTokenResponse response) {
        final String methodName = "createAccount";
        Logger.entering(TAG, methodName, response);

        IDToken idToken = null;
        ClientInfo clientInfo = null;

        try {
            idToken = new IDToken(response.getIdToken());
            clientInfo = new ClientInfo(response.getClientInfo());
        } catch (ServiceException ccse) {
            Logger.error(TAG + ":" + methodName, "Failed to construct IDToken or ClientInfo", null);
            Logger.errorPII(TAG + ":" + methodName, "Failed with Exception", ccse);
            // TODO: Should we bail?
        }

        final AzureActiveDirectoryAccount account = AzureActiveDirectoryAccount.create(idToken, clientInfo);

        Logger.exiting(TAG, methodName, account);

        return account;
    }

    @Override
    protected void validateAuthorizationRequest(final AzureActiveDirectoryAuthorizationRequest request) {
        final String methodName = "validateAuthorizationRequest";
        Logger.entering(TAG, methodName, request);
        // TODO
        Logger.exiting(TAG, methodName);
    }

    /**
     * validate the contents of the token request... all the base class is currently abstract
     * some of the validation for required parameters for the protocol could be there...
     *
     * @param request
     */
    @Override
    protected void validateTokenRequest(final AzureActiveDirectoryTokenRequest request) {
        final String methodName = "validateTokenRequest";
        Logger.entering(TAG, methodName, request);
        // TODO
        Logger.exiting(TAG, methodName);
    }

    @Override
    protected TokenResult getTokenResultFromHttpResponse(final HttpResponse response) {
        final String methodName = "getTokenResultFromHttpResponse";
        Logger.entering(TAG, methodName, response);

        TokenResponse tokenResponse = null;
        TokenErrorResponse tokenErrorResponse = null;

        if (response.getStatusCode() >= HttpURLConnection.HTTP_BAD_REQUEST) {
            //An error occurred
            Logger.warn(TAG + ":" + methodName, "Status code was: " + response.getStatusCode());
            tokenErrorResponse = ObjectMapper.deserializeJsonStringToObject(response.getBody(), MicrosoftTokenErrorResponse.class);
        } else {
            tokenResponse = ObjectMapper.deserializeJsonStringToObject(response.getBody(), AzureActiveDirectoryTokenResponse.class);
        }

        final TokenResult result = new TokenResult(tokenResponse, tokenErrorResponse);

        Logger.exiting(TAG, methodName, result);

        return result;
    }

}