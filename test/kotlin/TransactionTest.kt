import kotlin.transactional.*
import kotlin.concurrent.thread
import java.util.Random
import java.util.concurrent.atomic.AtomicInteger

class Account(initial: Int) {
    val r = ref<Int>(initial, preventWriteSkew = true) { it >= 0 }
    var balance: Int by r

    fun withdraw(amount: Int): Boolean {
        return r.setIfValid(balance - amount)
    }

    fun deposit(amount: Int) {
        balance += amount
    }
}

fun main(args: Array<String>) {
    val x = System.nanoTime();
    val a = Account(100)
    val b = Account(100)

    var totalTries = AtomicInteger(0)

    val threads = arrayListOf<Thread>()
    for (t in 1..3)
        threads add thread {
            val r = Random()
            for (i in 1..5) {
                var retry = 0
                transaction {
                    totalTries.incrementAndGet()
                    retry++
                    if (retry > 1) {
                        println("!!$t: attempt $i [retry $t:$i:$retry]: ${a.balance} : ${b.balance}")
                    }
                    println("$t: attempt $i [enter]: ${a.balance} : ${b.balance}")
                    if (a.withdraw(10)) {
                        b.deposit(10)
                        println("$t: attempt $i ${a.balance} : ${b.balance}")
                    }
                    else {
                        println("$t: attempt $i [failure]: ${a.balance} : ${b.balance}")
                    }
                }
            }
            println("$t finished")
        }

    println("All started")

    for (t in threads)
        t.join()

    println((System.nanoTime() - x) / 1e9)

    println("Total: $totalTries")
}