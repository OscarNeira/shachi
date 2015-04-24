package com.liaison.hbase.dto;

import java.io.Serializable;
import java.util.Arrays;

import com.liaison.hbase.util.Util;

public class FamilyQualifierPair implements Serializable {

    private static final long serialVersionUID = -3126811569021715389L;

    public static final class Builder {
        private byte[] family;
        private byte[] qual;
        private String description;
        private boolean optional;
        
        public Builder family(final byte[] family) {
            this.family = Util.copyOf(family);
            return this;
        }
        public Builder qual(final byte[] qual) {
            this.qual = Util.copyOf(qual);
            return this;
        }
        public Builder description(final String description) {
            this.description = description;
            return this;
        }
        public Builder optional(final boolean optional) {
            this.optional = optional;
            return this;
        }
        public FamilyQualifierPair build() throws IllegalArgumentException {
            return new FamilyQualifierPair(this);
        }
        private Builder() {
            this.family = null;
            this.qual = null;
            this.description = null;
            this.optional = false;
        }
    }
    
    public static Builder getBuilder() {
        return new Builder();
    }
    
    private final byte[] family;
    private final byte[] qual;
    /**
     * description is an optional field, so it is important that it NOT be included in hashCode
     * and equals implementations, so that instances with and without a description (or with
     * differing descriptions) are judged equal when used as map keys, etc.
     */
    private final String description;
    /**
     * optional is an optional field, so it is important that it NOT be included in hashCode
     * and equals implementations, so that instances differing optional flags are judged equal when
     * used as map keys, etc.
     */
    private final boolean optional;

    private Integer hc;
    private String strRep;

    public byte[] getFamily() {
        return this.family;
    }
    public byte[] getQual() {
        return this.qual;
    }
    public String getDescription() {
        return this.description;
    }
    public boolean isOptional() {
        return this.optional;
    }
    
    public int hashCode() {
        int hcInt;
        if (this.hc == null) {
            hcInt = Arrays.hashCode(this.family);
            hcInt ^= Arrays.hashCode(this.qual);
            this.hc = Integer.valueOf(hcInt);
        }
        return this.hc.intValue();
    }
    public boolean equals(final Object otherObj) {
        final FamilyQualifierPair otherFQP;
        if (otherObj instanceof FamilyQualifierPair) {
            otherFQP = (FamilyQualifierPair) otherObj;
            return (Util.refEquals(this.family, otherFQP.family)
                    && Util.refEquals(this.qual, otherFQP.qual));
        }
        return false;
    }
    public String toString() {
        final StringBuilder strGen;
        if (this.strRep == null) {
            strGen = new StringBuilder();
            strGen.append(FamilyQualifierPair.class.getSimpleName());
            strGen.append("(family=");
            strGen.append(Util.toString(this.family));
            strGen.append(",qual=");
            strGen.append(Util.toString(this.qual));
            if (this.description != null) {
                strGen.append(",description='");
                strGen.append(this.description);
            }
            strGen.append("'::");
            if (this.optional) {
                strGen.append("OPT");
            } else {
                strGen.append("REQ");
            }
            strGen.append(")");
            this.strRep = strGen.toString();
        }
        return this.strRep;
    }
    
    private FamilyQualifierPair(final Builder build) throws IllegalArgumentException {
        Util.ensureNotNull(build.family, this, "family", byte[].class);
        Util.ensureNotNull(build.qual, this, "qual", byte[].class);
        this.family = build.family;
        this.qual = build.qual;
        this.description = build.description;
        this.optional = build.optional;
    }
}
