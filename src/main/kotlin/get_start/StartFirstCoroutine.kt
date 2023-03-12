package get_start

import kotlinx.coroutines.*

class StartFirstCoroutine {
    suspend fun startCoroutine(string: String) = coroutineScope {
        print("Hello $string")
    }

    fun getCoroutineList(): List<Job> {
        val coroutines = List(100) {
            runBlocking {
                launch { println("") }
            }
        }
        return coroutines
    }

}

fun main() {
    val firstCoroutine = StartFirstCoroutine()
    runBlocking {
        (Dispatchers.IO) {
            for (i in 0..1000) {
                firstCoroutine.startCoroutine("$i ")
            }

            async {
                firstCoroutine.getCoroutineList().forEach { it.cancel() }
                print("Done")
            }.await()
        }
    }
}

