package get_start

import kotlinx.coroutines.*
import java.sql.Time

class StartFirstCoroutine {
    suspend fun startCoroutine(string: String) = coroutineScope {
        print("Hello $string")
    }

    suspend fun getCoroutineList(): List<Job> {
        val coroutines = List(100) {
            runBlocking {
                launch { println("") }
            }
        }
        return coroutines
    }

}

fun main(array: Array<String>) {
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

