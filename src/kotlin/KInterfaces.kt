package kotlin.collections.persistent

import java.util.Comparator
import clojure.lang.Counted
import clojure.lang.IFn
import clojure.lang.Sequential

public trait KAssociative<K, V> : KPersistentCollection<V>, KLookup<K, V> {
    fun containsKey(key: Any?): Boolean
    fun entryAt(key: Any?): KMapEntry<K, V>?
    fun assoc(key: K, `val`: V): KAssociative<K, V>
}

public trait KEditableCollection<E> {
    fun asTransient(): KTransientCollection<E>
}

public trait KLookup<K, V> {
    fun valAt(key: Any?): V
    fun valAt<V1: V?>(key: Any?, notFound: V1): V1
}

public trait KMapEntry<K, V> : Map.Entry<K, V> {
    fun key(): K
    fun `val`(): V
}

public trait KIndexed<E> : Counted {
    fun nth(i: Int): E
    fun <E1: E?> nth(i: Int, notFound: E1): E1
}

public trait KIndexedSeq<E> : KSeq<E>, Sequential, Counted {
    fun index(): Int
}

public trait KPersistentCollection<E> : KSeqable<E> {
    fun count(): Int
    fun cons(o: E): KPersistentCollection<E>
    fun empty<T>(): KPersistentCollection<T>
    fun equiv(o: Any): Boolean
}

public trait KPersistentList<E> : Sequential, KPersistentStack<E> {
}

public trait KPersistentMap<K, V> : Iterable<Map.Entry<K, V>>, KAssociative<K, V>, Counted {
    override fun assoc(key: K, `val`: V): KPersistentMap<K, V>
    fun assocEx(key: K, `val`: V): KPersistentMap<K, V>
    fun without(key: Any?): KPersistentMap<K, V>
}

public trait KPersistentSet<E> : KPersistentCollection<E>, Counted {
    fun disjoin(key: Any?): KPersistentSet<E>
    fun contains(key: Any?): Boolean
    fun get(key: Any?): Boolean
}

public trait KPersistentStack<E> : KPersistentCollection<E> {
    fun peek(): E
    fun pop(): KPersistentStack<E>
}

public trait KPersistentVector<E> : KAssociative<Int, E>, Sequential, KPersistentStack<E>, KReversible<E>, KIndexed<E> {
    fun length(): Int
    fun assocN(i: Int, `val`: E): KPersistentVector<E>
    override fun cons(o: E): KPersistentVector<E>
}

public trait KSeq<E> : KPersistentCollection<E> {
    fun first(): E
    fun next(): KSeq<E>
    fun more(): KSeq<E>
    override fun cons(o: E): KSeq<E>
}

public trait KTransientAssociative<K, V> : KTransientCollection<V>, KLookup<K, V> {
    fun assoc(key: K, `val`: V): KTransientAssociative<K, V>
}

public trait KTransientCollection<E> {
    fun conj(`val`: E): KTransientCollection<E>
    fun persistent(): KPersistentCollection<E>
}

public trait KTransientMap<K, V> : KTransientAssociative<K, V>, Counted {
    override fun assoc(key: K, `val`: V): KTransientMap<K, V>
    fun without(key: Any?): KTransientMap<K, V>
    override fun persistent(): KPersistentMap<K, V>
}

public trait KTransientSet<E> : KTransientCollection<E>, Counted {
    fun disjoin(key: Any?): KTransientSet<E>
    fun contains(key: Any?): Boolean
    fun get(key: Any?): Boolean
}

public trait KTransientVector<E> : KTransientAssociative<Int, E>, KIndexed<E> {
    fun assocN(i: Int, `val`: E): KTransientVector<E>
    fun pop(): KTransientVector<E>
}

public trait KReversible<E> {
    fun rseq(): KSeq<E>
}

public trait KSeqable<E> {
    fun seq(): KSeq<E>
}

public trait KSorted<E> {
    fun comparator(): Comparator<in E>
    fun entryKey(entry: Any?): Any?
    fun seq(ascending: Boolean): KSeq<E>
    fun seqFrom(key: E, ascending: Boolean): KSeq<E>
}
