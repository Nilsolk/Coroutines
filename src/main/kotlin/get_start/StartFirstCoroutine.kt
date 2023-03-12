package get_start

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class StartFirstCoroutine {
    suspend fun startMakeCoroutine(string: String) {
        print("Hello $string")
    }

}

fun main(array: Array<String>) {
    val firstCoroutine = StartFirstCoroutine()
    runBlocking {
        launch {
            delay(5000L)
            firstCoroutine.startMakeCoroutine("2")
        }
        firstCoroutine.startMakeCoroutine("1")
    }
}