package kotlin.transactional

import java.util.concurrent.Callable
import clojure.lang.LockingTransaction
import clojure.lang.*
import kotlin.collections.persistent.facade.*

public trait KDeref<out T> {
    fun deref(): T
}

public trait KRef<T> : KDeref<T> {
    fun <T1: T> set(value: T1): T1
}

fun <T> KRef<T>.validate(value: T): Boolean {
    val validator = (this as Ref).getValidator()
    if (validator == null) return true
    return validator.invoke(value) as Boolean
}

fun <T> KRef<T>.setIfValid(value: T): Boolean {
    if (!validate(value)) return false
    set(value)
    return true
}

public fun <T, R: T> KRef<T>.alter(f: (T) -> R): R {
    return set(f(this.deref()))
}

public fun <T, P1, R: T> KRef<T>.alter(f: (T, P1) -> R, p1: P1): R {
    return set(f(this.deref(), p1))
}

public fun <T, P1, P2, R: T> KRef<T>.alter(f: (T, P1, P2) -> R, p1: P1, p2: P2): R {
    return set(f(this.deref(), p1, p2))
}

public fun <T, R: T> KRef<T>.commute(f: (T) -> R): R {
    return (this as Ref).commute(
            object : AFn() {
                override fun invoke(arg1: Any?): Any? {
                    return f(arg1 as T)
                }
            },
            PersistentList.EMPTY
    ) as R
}

public fun <T, P1, R: T> KRef<T>.commute(f: (T, P1) -> R, p1: P1): R {
    return (this as Ref).commute(
            object : AFn() {
                override fun invoke(arg1: Any?, arg2: Any?): Any? {
                    return f(arg1 as T, arg2 as P1)
                }
            },
            list(p1) as ISeq
    ) as R
}

public fun <T> KRef<T>.ensure(): T {
    (this as Ref).touch()
    return (this as KRef<T>).deref()
}

// Delegated properties

fun ref<T>(value: T, preventWriteSkew: Boolean = false, validator: ((T) -> Boolean)? = null): KRef<T> {
    val result = if (preventWriteSkew) NoWriteSkewRef(value) else Ref(value)
    if (validator != null) {
        result.setValidator(object : AFn() {
            override fun invoke(p: Any?): Any? {
                return validator(p as T)
            }
        })
    }
    return result as KRef<T>
}

public fun <T> KDeref<T>.get(_this: Any?, p: Any?): T = deref()
public fun <T> KRef<T>.set(_this: Any?, p: Any?, v: T): Unit {
    set(v)
}

// Running a transaction

public fun <T> transaction(body: () -> T): T {
    return LockingTransaction.runInTransaction(Callable(body)) as T
}

// Cancellable transaction

private class CancelTransactionException: Throwable()
public fun cancelTransaction() {
    throw CancelTransactionException()
}

public fun <T: Any> cancellableTransaction(body: () -> T): T? {
    try {
        return LockingTransaction.runInTransaction(Callable(body)) as T
    } catch (e: CancelTransactionException) {
        return null
    }
}


