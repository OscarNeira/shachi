package com.liaison.hbase.api.opspec;

import java.io.Serializable;
import java.util.LinkedHashMap;

import com.liaison.hbase.HBaseControl;
import com.liaison.hbase.api.OpResult;
import com.liaison.hbase.context.HBaseContext;
import com.liaison.hbase.util.TreeNodeRoot;
import com.liaison.hbase.util.Util;

public class OperationController extends TreeNodeRoot<OperationController> implements Serializable {
    
    private static final long serialVersionUID = -6620685078075615195L;

    private static enum State {
        ACCEPTING, EXECUTING;
    }
    
    private final Object stateLock;
    private final State state;
    private final HBaseControl control;
    private final HBaseContext context;
    private final LinkedHashMap<Object, OperationSpec<?>> ops;


    @Override
    protected OperationController self() {
        return this;
    }
    
    private final void verifyStateForAddingOps() throws IllegalStateException {
        Util.verifyState(State.ACCEPTING, this.state, this.stateLock);
    }
    private final void putOpWithNewHandle(final Object handle, final OperationSpec<?> op) throws IllegalArgumentException {
        if (handle == null) {
            throw new IllegalArgumentException("Operation must be specified with non-null handle");
        }
        if (ops.putIfAbsent(handle, op) != null) {
            throw new IllegalArgumentException("Handle "
                                               + handle
                                               + "already in use for other operation");
        }
    }
    
    public ReadOpSpec read(final Object handle) throws IllegalStateException, IllegalArgumentException {
        final ReadOpSpec nextReadOp;
        verifyStateForAddingOps();
        nextReadOp = new ReadOpSpec(this.context, this);
        putOpWithNewHandle(handle, nextReadOp);
        return nextReadOp;
    }
    public WriteOpSpec write(final Object handle) throws IllegalStateException, IllegalArgumentException {
        final WriteOpSpec nextCreateOp;
        verifyStateForAddingOps();
        nextCreateOp = new WriteOpSpec(this.context, this);
        putOpWithNewHandle(handle, nextCreateOp);
        return nextCreateOp;
    }
    
    public void show() {
        // TODO
        System.out.println(this.ops);
    }
    
    public OpResult exec() {
        // TODO
        return null;
    }
    
    public OperationController(final HBaseControl control, final HBaseContext context) {
        Util.ensureNotNull(control, this, "control", HBaseControl.class);
        Util.ensureNotNull(context, this, "context", HBaseContext.class);
        this.stateLock = new Object();
        this.state = State.ACCEPTING;
        this.control = control;
        this.context = context;
        this.ops = new LinkedHashMap<>();
    }
}
