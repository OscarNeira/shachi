package com.liaison.hbase.context;

public class DefaultHBaseContext extends CommonHBaseContext {

    public static final class Builder extends AbstractHBaseContextBuilder<DefaultHBaseContext, Builder> {
        @Override
        public Builder self() {
            return this;
        }
        @Override
        public DefaultHBaseContext build() {
            return new DefaultHBaseContext(this);
        }
        private Builder() { }
    }
    
    private static final TableNamingStrategy
        DEFAULT_TABLENAMINGSTRATEGY = new IdentityTableNamingStrategy();

    public static final Builder getBuilder() {
        return new Builder();
    }
    
    @Override
    protected final TableNamingStrategy getDefaultTableNamingStrategy() {
        return DEFAULT_TABLENAMINGSTRATEGY;
    }
    
    public DefaultHBaseContext(final Builder build) {
        super(build);
    }
}
