package edu.towson.cosc435.kraft.sidequest

import com.google.gson.Gson
import edu.towson.cosc435.kraft.sidequest.data.model.Quote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

interface IQuoteFetcher {
    suspend fun fetchQuotes(): Quote?

}

// quote fetcher class
class QuoteFetcher: IQuoteFetcher {
    // sets the url to use to get the list of quotes json objects from
    private val URL = "https://zenquotes.io/api/random"

    // suspend function that gets called from the context of a coroutine that fetched the list of json objects from the specified url
    override suspend fun fetchQuotes(): Quote? {

        // ensure we are not on the main thread
        return withContext(Dispatchers.IO) {// uses Dispatcher.IO to efficiently receive data from a web api
            // use okhttp with a get request to fetch the json
            val httpClient = OkHttpClient()
            val request = Request.Builder() // builds api request
                .get()
                .url(URL)
                .build()
            val response =
                httpClient.newCall(request).execute() // gets response from the api request
            if (response.isSuccessful) { // checks if the response was successful
                val jsonString = response.body?.string() // stringifies the response
                if (jsonString != null) { // checks if the jsonString returned was null

                    // use gson to parse the json into a list of quote objects
                    val quoteArray: Array<Quote> = Gson().fromJson(jsonString, Array<Quote>::class.java) // parses the returned json into objects of the quote data class
                    quoteArray[0]
                } else { // if the jsonString WAS null
                    null// return empty list
                }
            } else { // is the response was NOT successful
                null// return empty list
            }
        }
    }
}