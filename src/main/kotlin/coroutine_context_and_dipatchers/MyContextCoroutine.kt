package coroutine_context_and_dipatchers

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MyContextCoroutine {
    @OptIn(DelicateCoroutinesApi::class)
    fun listOfDispatchers(): List<CoroutineContext> {
        return listOf(
            Dispatchers.IO,
            Dispatchers.Default,
            Dispatchers.Unconfined,
            newSingleThreadContext("MyOwnContext")
        )
    }

    fun getCurrentThread(list: List<CoroutineContext>) = runBlocking {
        list.forEach {
            launch(it) {
                println(Thread.currentThread().name + "$it")
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun fromOneThreadToAnother() = runBlocking {
        launch(newSingleThreadContext("my thread")) {
            println(Thread.currentThread().name)
            launch(Dispatchers.Unconfined) {
                println(Thread.currentThread().name)
                delay(500L)
                println(Thread.currentThread().name)
            }
        }
    }
}

fun main() {
    val contextCoroutine = MyContextCoroutine()
    val listOfDispatchers = contextCoroutine.listOfDispatchers()
    contextCoroutine.getCurrentThread(listOfDispatchers)
    println("________________________")
    contextCoroutine.fromOneThreadToAnother()
}