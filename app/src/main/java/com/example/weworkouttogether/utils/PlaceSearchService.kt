package com.example.weworkouttogether.utils

import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.*
import org.jsoup.Connection
import org.jsoup.helper.HttpConnection.connect
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.RuntimeException
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class PlaceSearchService {
    val TAG = "NaverSearch"
    private val scope = CoroutineScope(Dispatchers.Default)

    fun placeSearch(keyword: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val clientId = "ajr7ph296r" //애플리케이션 클라이언트 아이디값";
            val clientSecret = "CoZ1FQ5F5muBXMEpFiItg9vj9R2TM9vDz5IdTn6M" //애플리케이션 클라이언트 시크릿값";
            withContext(scope.coroutineContext) {
                try {
                    var keyword = URLEncoder.encode(keyword, "UTF-8")
                    val apiURL = "https://naveropenapi.apigw.ntruss.com/datalab/v1/search"
                    val body =
                        "{\"startDate\":\"2017-01-01\",\"endDate\":\"2017-04-30\",\"timeUnit\":\"month\",\"keywordGroups\":[{\"groupName\":\"한글\",\"keywords\":[\"한글\",\"korean\"]},{\"groupName\":\"영어\",\"keywords\":[\"영어\",\"english\"]}],\"device\":\"pc\",\"ages\":[\"1\",\"2\"],\"gender\":\"f\"}";
                    val url = URL(apiURL)
                    val con = url.openConnection() as HttpURLConnection
                    con.requestMethod = "GET"
                    con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId)
                    con.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret)
                    con.setRequestProperty("Content-Type", "application/json")
                    con.doOutput = true
                    val wr = DataOutputStream(con.outputStream)
                    wr.write(body.encodeToByteArray())
                    wr.flush()
                    wr.close()
                    val responseCode = con.responseCode
                    val br: BufferedReader =
                        if (responseCode == 200) { // 정상 호출
                            BufferedReader(InputStreamReader(con.inputStream))
                        } else {  // 오류 발생
                            BufferedReader(InputStreamReader(con.errorStream))
                        }
                    var inputLine: String
                    val response = StringBuffer()
                    while (br.readLine().also { inputLine = it } != null) {
                        response.append(inputLine)
                    }
                    br.close()
                    Log.e(TAG, response.toString())
                    println(response.toString())
                } catch (e: Exception) {
                    Log.e(TAG, "$e")
                    println(e)
                }
            }
        }
    }
}