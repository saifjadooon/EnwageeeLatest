package com.example.envagemobileapplication.Utils

import android.os.AsyncTask
import android.os.Environment
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.security.SecureRandom
import java.util.regex.Pattern

object FileUtils {

    fun getUrlExtension(url: String): String {
        return url.split("[#?]".toRegex()).toTypedArray()[0].split("\\.".toRegex()).toTypedArray().last().trim()
    }

    fun replaceSpacesWithUnderscores(inputString: String): String {
        val hasSpecialCharacters = Pattern.compile("[^A-Za-z0-9]").matcher(inputString).find()
        return if (hasSpecialCharacters) {
            inputString.replace("[^A-Za-z0-9.]".toRegex(), "")
        } else {
            inputString + '1'
        }
    }

    fun randomString(length: Int): String {
        val characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        val charactersLength = characters.length
        val random = SecureRandom()
        val result = StringBuilder()
        for (i in 0 until length) {
            result.append(characters[random.nextInt(charactersLength)])
        }
        return result.toString()
    }


    fun downloadFile(url: String, fileName: String, ignoreCheck: Boolean) {
        val downloadDir = Environment.getExternalStorageDirectory().toString() + "/Download"

        if (!ignoreCheck) {
            // Check if the directory exists, create it if not
            val downloadDirFile = File(downloadDir)
            if (!downloadDirFile.exists()) {
                downloadDirFile.mkdirs()
            }

            // Check if the file exists and handle the download accordingly
            val filePath =
                "$downloadDir/${replaceSpacesWithUnderscores(fileName)}.${getUrlExtension(url)}"
            val file = File(filePath)
            if (file.exists()) {
                // File exists, add random string to avoid duplication
                val newFileName =
                    "${replaceSpacesWithUnderscores(fileName)}(${randomString(5)}).${getUrlExtension(url)}"
                DownloadFileTask().execute(url, newFileName)
            } else {
                // File doesn't exist, download with the original file name
                DownloadFileTask().execute(url, fileName)
            }
        } else {
            DownloadFileTask().execute(url, fileName)
        }
    }



    private class DownloadFileTask : AsyncTask<String, Void, File?>() {
        override fun doInBackground(vararg params: String): File? {
            val url = URL(params[0])
            val fileName = params[1]

            try {
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connect()

                val fileExtension = getUrlExtension(url.toString())
                val downloadDir = Environment.getExternalStorageDirectory().toString()
                val downloadDest =
                    "$downloadDir/Download/${replaceSpacesWithUnderscores(fileName)}.$fileExtension"

                val file = File(downloadDest)
                val fileOutput = FileOutputStream(file)

                val inputStream: InputStream = connection.inputStream
                val buffer = ByteArray(1024)
                var len: Int

                while (inputStream.read(buffer).also { len = it } != -1) {
                    fileOutput.write(buffer, 0, len)
                }

                fileOutput.close()
                inputStream.close()

                return file
            } catch (e: Exception) {
                Log.e("DownloadFileTask", "Error downloading file: $e")
            }

            return null
        }

        override fun onPostExecute(result: File?) {
            super.onPostExecute(result)
            if (result != null) {
                // File downloaded successfully
                Log.d("DownloadFileTask", "File downloaded to ${result.absolutePath}")
                // Handle success
            } else {
                // Error downloading file
                Log.e("DownloadFileTask", "Error downloading file")
                // Handle error
            }
        }
    }
}