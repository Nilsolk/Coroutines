package cancel

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

class CancelCoroutine {
    suspend fun start() = coroutineScope {
        val job = launch {
            repeat(1000) {
                println("$it is done")
                delay(100L)
            }
        }
        delay(1000)
        job.cancelAndJoin()

    }

    suspend fun catchException() = coroutineScope {
        val job = launch {
            repeat(10) {
                try {
                    println("it delayed $it")
                    delay(500L)
                } catch (e: Exception) {
                    println(e)
                }
            }
        }
        delay(1000L)
        println("Waiting")
        job.cancel()
        println("Exit")
    }

    suspend fun tryFinally() = coroutineScope {
        val job = launch {
            try {
                repeat(1000) { i ->
                    println("it delayed $i ...")
                    delay(100L)
                }
            } finally {
                withContext(NonCancellable) {
                    println("running finally")
                    delay(5000L)
                    println(" delayed for 1 ")
                }
            }
        }
        delay(1300L)
        println("Waiting")
        job.cancelAndJoin()
    }
}

class AsyncCoroutine {
    suspend fun doSomethingUsefulOne() {
        delay(1000L)
        println("first done")
    }

    suspend fun doSomethingUsefulTwo() {
        delay(1000L)
        println("two done")
    }

    suspend fun concurrent(): String = coroutineScope {
        val one = async { doSomethingUsefulOne() }
        val two = async { doSomethingUsefulTwo() }
        one.await()
        two.await()
        return@coroutineScope "Async is done"
    }

}

fun main() = runBlocking {
    val time = measureTimeMillis {
        val one = async { AsyncCoroutine().doSomethingUsefulOne() }
        val two = async { AsyncCoroutine().doSomethingUsefulTwo() }
        one.await()
        two.await()
    }
    val concurrentTime = measureTimeMillis {
        println(AsyncCoroutine().concurrent())
    }
    println(time)
    println(concurrentTime)

}

