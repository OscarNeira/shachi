/**
 * Copyright 2015 Liaison Technologies, Inc.
 * This software is the confidential and proprietary information of
 * Liaison Technologies, Inc. ("Confidential Information").  You shall
 * not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Liaison Technologies.
 */
package com.liaison.hbase.dto;

import java.io.Serializable;
import java.util.Arrays;
import java.util.function.Consumer;

import com.liaison.hbase.util.AbstractSelfRefBuilder;
import com.liaison.hbase.util.DefensiveCopyStrategy;
import com.liaison.hbase.util.Util;

public abstract class NullableValue implements Serializable {
    
    private static final long serialVersionUID = 8100342808865479731L;

    protected abstract static class AbstractValueBuilder<T, B extends AbstractSelfRefBuilder<T, B>> extends AbstractSelfRefBuilder<T, B> {
        protected byte[] value;
        public B value(final byte[] value, final DefensiveCopyStrategy copyStrategy) {
            this.value = Util.setInternalByteArray(value, copyStrategy);
            return self();
        }
        @Deprecated
        public B value(final byte[] value) {
            return value(value, DefensiveCopyStrategy.DEFAULT);
        }
        
        protected AbstractValueBuilder() throws IllegalArgumentException {
            this.value = null;
        }
    }
    
    private static final String ENTITY_PREFIX_FOR_TOSTRING = "v";
    
    protected static String buildStrRep(final String entityTypeIdentifier, final Consumer<StringBuilder> contentGenerator) {
        final StringBuilder strGen;
        strGen = new StringBuilder();
        strGen.append("<HB:");
        if (entityTypeIdentifier != null) {
            strGen.append(entityTypeIdentifier);
            strGen.append(":");
        }
        contentGenerator.accept(strGen);
        strGen.append(">");
        return strGen.toString();
    }
    protected static String buildStrRep(final Consumer<StringBuilder> contentGenerator) {
        return buildStrRep(null, contentGenerator);
    }
    
    private final byte[] value;
    private Integer hc;
    private String strRep;
    
    public byte[] getValue(final DefensiveCopyStrategy copyStrategy) {
        return Util.getInternalByteArray(this.value, copyStrategy);
    }
    @Deprecated
    public byte[] getValue() {
        return getValue(DefensiveCopyStrategy.DEFAULT);
    }
    
    @Override
    public int hashCode() {
        if (this.hc == null) {
            this.hc = Integer.valueOf(Arrays.hashCode(getValue(DefensiveCopyStrategy.NEVER)));
        }
        return this.hc.intValue();
    }
    @Override
    public boolean equals(final Object otherObj) {
        final NullableValue otherVal;
        if (otherObj instanceof NullableValue) {
            otherVal = (Value) otherObj;
            return Util.refEquals(this.value, otherVal.value);
        }
        return false;
    }
    @Override
    public String toString() {
        if (this.strRep == null) {
            this.strRep =
                buildStrRep(ENTITY_PREFIX_FOR_TOSTRING, (strGen) -> {
                    strGen.append(Util.toString(this.value));
                });
        }
        return this.strRep;
    }
    
    // TODO equals, toString, hashCode, etc.
    
    protected NullableValue(final AbstractValueBuilder<?,?> build) throws IllegalArgumentException {
        this.value = build.value;
    }
}
