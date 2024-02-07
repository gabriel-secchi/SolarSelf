package com.gma.data_remote.network.interceptors

import com.gma.data_remote.network.AUTHORIZATION_HEADER
import com.gma.data_remote.network.ApiDataEngine
import com.gma.data_remote.network.CONTENT_MD5_HEADER
import com.gma.data_remote.network.DATE_HEADER
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import okio.Buffer
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.Base64
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

internal class HeadersInterceptor(
    private val apiDataEngine: ApiDataEngine
): Interceptor {

    private fun buildGmtDateTime(): String {
        val dateFormat = SimpleDateFormat("E, dd MMM YYYY HH:mm:ss", Locale.US)
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        val date = Date()
        return dateFormat.format(date).plus(" GMT")
    }

    private fun buildAuthorization(sign: String): String {
        val data = apiDataEngine.getApiData()
        return "API ${data.keyId}:${sign}"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val formatedUtcDate = buildGmtDateTime()
        val sign = buildRequestSign(chain.request(), formatedUtcDate)
        val formatedAuthorization = buildAuthorization(sign)
        val contentBody = stringToMd5(requestBodyToString(chain.request().body))

        val requestBuilder = chain.request().newBuilder()
            .header(AUTHORIZATION_HEADER, formatedAuthorization)
            .header(CONTENT_MD5_HEADER, contentBody)
            .header(DATE_HEADER, formatedUtcDate)

        return chain.proceed(requestBuilder.build())
    }

    private fun buildRequestSign(request: Request, dateUtc: String): String {
        val apiData = apiDataEngine.getApiData()
        val body = requestBodyToString(request.body)
        val hashedBody = stringToMd5(body)
        val method = request.method
        val contentType = "application/json"
        val resource =  request.url.encodedPath

        val textToHmacHash = "${method}\n"
            .plus("${hashedBody}\n") //body content md5
            .plus("${contentType}\n")
            .plus("${dateUtc}\n")
            .plus(resource)


        return generateHmac(textToHmacHash, apiData.keySecret)
    }

    private fun stringToMd5(text: String): String {
        val md = MessageDigest.getInstance("MD5")
        val byteArray = text.toByteArray()
        val digest = md.digest(byteArray)
        val base64String = Base64.getEncoder().encodeToString(digest)

        return base64String
    }

    private fun generateHmac(text: String, secretKey: String): String {
        val algorithm = "HmacSHA1"
        val keySpec = SecretKeySpec(secretKey.toByteArray(), algorithm)
        val mac = Mac.getInstance(algorithm)
        mac.init(keySpec)
        val hmacBytes = mac.doFinal(text.toByteArray())

        val base64String = Base64.getEncoder().encodeToString(hmacBytes)

        return base64String
        /*val hexString = StringBuilder()

        for (byte in hmacBytes) {
            val hex = String.format("%02x", byte)
            hexString.append(hex)
        }

        return hexString.toString()*/
    }

    private fun requestBodyToString(body: RequestBody?): String {
        body?.let {
            try {
                val buffer = Buffer()
                it.writeTo(buffer)
                return buffer.readUtf8()
            }
            catch (ex: Exception) {
                ex.printStackTrace()
            }
        }

        return ""
    }
}