package com.liaison.hbase.model;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import com.liaison.hbase.util.Util;

public class FamilyModel {
    
    public static final class Builder {
        private Name name;
        private LinkedHashMap<Name, QualModel> quals;
        private boolean closedQualSet;
        
        public Builder name(final Name name) {
            this.name = name;
            return this;
        }
        public Builder qual(final QualModel qual) throws IllegalArgumentException {
            Util.ensureNotNull(qual, this, "qual", QualModel.class);
            this.quals.put(qual.getName(), qual);
            return this;
        }
        public Builder closedQualSet(final boolean closedQualSet) {
            this.closedQualSet = closedQualSet;
            return this;
        }
        
        public FamilyModel build() {
            return new FamilyModel(this);
        }
        private Builder() {
            this.closedQualSet = false;
            this.name = null;
            this.quals = new LinkedHashMap<>();
        }
    }
    
    public static Builder with(final Name name) {
        return new Builder().name(name);
    }
    public static FamilyModel of(final Name name) {
        return with(name).build();
    }
    
    private final Name name;
    private final Map<Name, QualModel> quals;
    private final boolean closedQualSet;
    
    public Name getName() {
        return this.name;
    }
    public Map<Name, QualModel> getQuals() {
        return this.quals;
    }
    public boolean isClosedQualSet() {
        return closedQualSet;
    }
    
    private FamilyModel(final Builder build) throws IllegalArgumentException {
        Util.ensureNotNull(build.name, this, "name", Name.class);
        this.name = build.name;
        this.quals = Collections.unmodifiableMap(build.quals);
        this.closedQualSet = build.closedQualSet;
    }
}
