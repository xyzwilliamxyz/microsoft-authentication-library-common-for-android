package com.microsoft.identity.common.internal.ui.browser;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Version implements Comparable<Version>, Serializable {
    private static final long serialVersionUID = 647675244658547811L;

    @SerializedName("version")
    private String mVersion;

    public Version(String version) {
        if(version == null)
            throw new IllegalArgumentException("Version can not be null");
        if(!version.matches("[0-9]+(\\.[0-9]+)*"))
            throw new IllegalArgumentException("Invalid version format");
        mVersion = version;
    }

    public String getVersion() {
        return mVersion;
    }

    /**
     * @param comparison in Version
     * @return 1 if this version is newer than comparison version
     *          -1 if this version is older than comparison version
     *          0 if this version is equal to comparison version
     */
    @Override
    public int compareTo(Version comparison) {
        if (comparison == null) {
            return 1;
        }

        String[] thisSegments = mVersion.split("\\.");
        String[] comparisonSegments = comparison.getVersion().split("\\.");

        int length = Math.max(thisSegments.length, comparisonSegments.length);

        for (int i = 0; i < length; i++) {
            int thisSegment = i < thisSegments.length ? Integer.parseInt(thisSegments[i]) : 0;
            int comparisonSegment = i < comparisonSegments.length ? Integer.parseInt(comparisonSegments[i]) : 0;
            if (thisSegment > comparisonSegment) {
                return 1;
            }

            if (thisSegment < comparisonSegment) {
                return -1;
            }
        }

        return 0;
    }
}
