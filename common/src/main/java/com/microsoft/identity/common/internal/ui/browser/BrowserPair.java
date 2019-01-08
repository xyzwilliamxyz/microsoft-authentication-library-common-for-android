package com.microsoft.identity.common.internal.ui.browser;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BrowserPair implements Serializable {
    private static final long serialVersionUID = 1913766783403270578L;

    @SerializedName("package_name")
    private String mPackageName;

    @SerializedName("signature")
    private String mSignature;

    @SerializedName("version_lower_bound")
    private Version mLowerBound;

    @SerializedName("version_upper_bound")
    private Version mUpperBound;

    public String getPackageName() {
        return mPackageName;
    }

    public void setPackageName(String packageName) {
        mPackageName = packageName;
    }

    public String getSignature() {
        return mSignature;
    }

    public void setSignature(String signature) {
        mSignature = signature;
    }

    public Version getLowerBound() {
        return mLowerBound;
    }

    public void setLowerBound(Version lowerBound) {
        mLowerBound = lowerBound;
    }

    public Version getUpperBound() {
        return mUpperBound;
    }

    public void setUpperBound(Version upperBound) {
        mUpperBound = upperBound;
    }

    public boolean matches(@NonNull Browser browser) {
        return mPackageName.equals(browser.getPackageName())
                && browser.getSignatureHashes().contains(mSignature)
                && mLowerBound.compareTo(new Version(browser.getVersion())) != 1 //TODO fix the comparison here.
                && mUpperBound.compareTo(new Version(browser.getVersion())) != -1;
    }
}
