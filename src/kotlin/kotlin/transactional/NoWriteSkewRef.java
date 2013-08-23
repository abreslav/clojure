package kotlin.transactional;

import clojure.lang.LockingTransaction;
import clojure.lang.Ref;

public class NoWriteSkewRef extends Ref implements KRef {
    public NoWriteSkewRef(Object initVal) {
        super(initVal);
    }

    @Override
    public Object deref() {
        if (LockingTransaction.isRunning()) {
            touch();
        }
        return super.deref();
    }
}
