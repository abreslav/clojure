package kotlin.collections.persistent.facade

import kotlin.collections.persistent.*

public trait PSeq<E> : KSeq<E>, List<E>

public trait PCollection<E> : KPersistentCollection<E>, Collection<E>

public trait PVector<E> : KPersistentVector<E>, List<E>
public trait PVectorEditable<E>: PVector<E>, KEditableCollection<E> {
    override fun asTransient(): KTransientVector<E>
}

public trait PList<E> : PCollection<E>, KPersistentList<E>, List<E>

public trait PQueue<E> : PCollection<E>, KPersistentList<E>

public trait PSet<E> : PCollection<E>, KPersistentSet<E>, Set<E>
public trait PTreeSet<E> : PSet<E>, KSorted<E>, KReversible<E>
public trait PSetEditable<E> : PSet<E>, KEditableCollection<E> {
    override fun asTransient(): KTransientSet<E>
}

public trait PMap<K, V> : KPersistentMap<K, V>, Map<K, V>
public trait PMapEditable<K, V> : PMap<K, V>, KEditableCollection<V> {
    override fun asTransient(): KTransientMap<K, V>
}

public trait PTreeMap<K, V> : PMap<K, V>, KSorted<K>, KReversible<K> {
    fun keys(): Iterator<K>
    fun vals(): Iterator<V>
    fun minKey(): K
    fun maxKey(): K
    fun depth(): Int
    fun capacity(): Int
    fun doCompare(k1: K, k2: K): Int
}
