package kotlin.collections.persistent.facade

import java.util.Comparator
import clojure.lang.*
import kotlin.collections.persistent.KSeq

public fun vector<E>(vararg items: E): PVectorEditable<E> {
    return LazilyPersistentVector.createOwning(items) as PVectorEditable<E>
}

public fun toVector<E>(items: Collection<E>): PVectorEditable<E> {
    return LazilyPersistentVector.create(items) as PVectorEditable<E>
}

public fun list<E>(): PList<E> {
    return PersistentList.EMPTY as PList<E>
}

public fun list<E>(e: E): PList<E> {
    return PersistentList(e) as PList<E>
}

public fun list<E>(list: List<E>): PList<E> {
    return PersistentList.create(list) as PList<E>
}

public fun queue<E>(): PQueue<E> {
    return PersistentQueue.EMPTY as PQueue<E>
}

public fun treeSet<E>(): PTreeSet<E> {
    return PersistentTreeSet.EMPTY as PTreeSet<E>
}

public fun treeSet<E>(seq: KSeq<E>): PTreeSet<E> {
    return PersistentTreeSet.create(seq as ISeq) as PTreeSet<E>
}

public fun treeSet<E>(seq: KSeq<E>, comparator: Comparator<in E>): PTreeSet<E> {
    return PersistentTreeSet.create(comparator, seq as ISeq) as PTreeSet<E>
}

public fun treeSet<E>(seq: KSeq<E>, comparator: (E, E) -> Int): PTreeSet<E> {
    return treeSet(seq, Comparator(comparator))
}

public fun hashSet<E>(): PSet<E> {
    return PersistentHashSet.EMPTY as PSet<E>
}

public fun hashSet<E>(vararg items: E): PSet<E> {
    return PersistentHashSet.create(items) as PSet<E>
}

public fun hashSet<E>(items: List<E>): PSet<E> {
    return PersistentHashSet.create(items) as PSet<E>
}

public fun hashSet<E>(items: KSeq<E>): PSet<E> {
    return PersistentHashSet.create(items) as PSet<E>
}

public fun arrayMap<K, V>(): PMapEditable<K, V> {
    return PersistentArrayMap.EMPTY as PMapEditable<K, V>
}

public fun arrayMap<K, V>(map: Map<K, V>): PMapEditable<K, V> {
    return PersistentArrayMap.create(map) as PMapEditable<K, V>
}

public fun hashMap<K, V>(): PMapEditable<K, V> {
    return PersistentHashMap.EMPTY as PMapEditable<K, V>
}

public fun hashMap<K, V>(map: Map<K, V>): PMapEditable<K, V> {
    return PersistentHashMap.create(map) as PMapEditable<K, V>
}

public fun treeMap<K, V>(): PTreeMap<K, V> {
    return PersistentTreeMap.EMPTY as PTreeMap<K, V>
}

public fun treeMap<K, V>(map: Map<K, V>): PTreeMap<K, V> {
    return PersistentTreeMap.create(map) as PTreeMap<K, V>
}

public fun treeMap<K, V>(comparator: Comparator<in K>): PTreeMap<K, V> {
    return PersistentTreeMap.create(comparator, PersistentList.EMPTY) as PTreeMap<K, V>
}

public fun treeMap<K, V>(comparator: (K, K) -> Int): PTreeMap<K, V> {
    return treeMap(Comparator(comparator))
}

