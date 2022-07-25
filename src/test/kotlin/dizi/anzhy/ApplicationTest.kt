package dizi.anzhy

import dizi.anzhy.models.ApiResponse
import dizi.anzhy.repository.HeroRepository
import dizi.anzhy.repository.NEXT_PAGE_KEY
import dizi.anzhy.repository.PREVIOUS_PAGE_KEY
import io.ktor.client.call.*
import io.ktor.http.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlin.test.*
import io.ktor.server.testing.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.koin.core.context.GlobalContext
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.inject

class ApplicationTest {

    private val heroRepository: HeroRepository by inject(HeroRepository::class.java)

    @Test
    fun `access root endpoint, assert correct information`() = testApplication {
        client.get("/").apply {
            assertEquals(
                expected = HttpStatusCode.OK,
                actual = status
            )
            assertEquals(
                expected = "\"Welcome to Anime API\"",
                actual = bodyAsText()
            )
        }
    }

    @ExperimentalSerializationApi
    @Test
    fun `access all heroes endpoint, assert correct information`() = testApplication {
        environment {
            developmentMode = false
        }
        client.get("/anime/heroes").apply {
            assertEquals(
                expected = HttpStatusCode.OK,
                actual = status
            )
            //serialize the result from server and convert to JSON response back into this API response
            val actual = Json.decodeFromString<ApiResponse>(this.body())

            val expected = ApiResponse(
                success = true,
                message = "ok",
                prevPage = null,
                nextPage = 2,
                //we can inject here hero repository
                heroes = heroRepository.page1,
                lastUpdated = actual.lastUpdated
            )
            assertEquals(
                expected = expected,
                actual = actual
            )
        }
    }

    @ExperimentalSerializationApi
    @Test
    fun `access non existing endpoint,assert not found`() = testApplication {
        client.get("/unknown").apply {
            assertEquals(
                expected = HttpStatusCode.NotFound,
                actual = status
            )
            assertEquals(
                expected = "\"Page not Found.\"",
                actual = this.body()
            )
        }
    }

    /*
    @ExperimentalSerializationApi
    @Test
    fun `access all heroes endpoint, query all pages, assert correct information`() = testApplication {
        // int range
        val pages = 1..5
        val heroes = listOf(
            heroRepository.page1,
            heroRepository.page2,
            heroRepository.page3,
            heroRepository.page4,
            heroRepository.page5
        )

        pages.forEach { page ->

            client.get("/anime/heroes?page=$page").apply {

                assertEquals(
                    expected = HttpStatusCode.OK,
                    actual = status
                )
                val actual = Json.decodeFromString<ApiResponse>(this.body())

                val expected = ApiResponse(
                    success = true,
                    message = "ok", //default message
                    prevPage = calculatePage(page = page)[PREVIOUS_PAGE_KEY],
                    nextPage = calculatePage(page = page)[NEXT_PAGE_KEY],
                    heroes = heroes[page - 1],
                    lastUpdated = actual.lastUpdated// page -1 because index start from 0 and page starts from 1
                )

                assertEquals(
                    expected = expected,
                    actual = actual
                )
            }
        }
    }

     */

    private fun calculatePage(page: Int): Map<String, Int?> {
        var prevPage: Int? = page
        var nextPage: Int? = page
        if (page in 1..4) {
            nextPage = nextPage?.plus(1)
        }
        if (page in 2..5) {
            prevPage = prevPage?.minus(1)
        }
        if (page == 1) {
            prevPage = null
        }
        if (page == 5) {
            nextPage = null
        }
        return mapOf(PREVIOUS_PAGE_KEY to prevPage, NEXT_PAGE_KEY to nextPage)
    }
}