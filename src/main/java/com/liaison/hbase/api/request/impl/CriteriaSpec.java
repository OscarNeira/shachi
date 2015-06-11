/**
 * Copyright 2015 Liaison Technologies, Inc.
 * This software is the confidential and proprietary information of
 * Liaison Technologies, Inc. ("Confidential Information").  You shall
 * not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Liaison Technologies.
 */
package com.liaison.hbase.api.request.impl;

import com.liaison.hbase.api.request.fluid.CriteriaSpecFluid;
import com.liaison.hbase.util.TreeNode;

import java.io.Serializable;

public abstract class CriteriaSpec<C extends CriteriaSpec<C, P>, P extends TreeNode<P>> extends StatefulSpec<C, P> implements CriteriaSpecFluid<P>, Serializable {
    
    private static final long serialVersionUID = 7087926388191014497L;
    
    public P and() {
        return getParent();
    }

    public CriteriaSpec(final P parent) throws IllegalArgumentException {
        super(parent);
    }
}
