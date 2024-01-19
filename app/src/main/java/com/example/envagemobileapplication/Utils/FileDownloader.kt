package com.example.envagemobileapplication.Utils

import android.content.Context
import android.os.AsyncTask
import android.os.Environment
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL


class FileDownloader(private val context: Context) {

    fun downloadFile(fileUrl: String, fileName: String) {
        DownloadFileTask(context).execute(fileUrl, fileName)
    }

    private class DownloadFileTask(private val context: Context) : AsyncTask<String, Void, File?>() {

        override fun doInBackground(vararg params: String): File? {
            val fileUrl = params[0]
            val fileName = params[1]

            try {
                val url = URL(fileUrl)
                val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
                connection.connect()

                // Create a new file in the Downloads directory
                val outputDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                val outputFile = File(outputDir, fileName)

                val inputStream = BufferedInputStream(url.openStream())
                val outputStream = FileOutputStream(outputFile)

                val data = ByteArray(1024)
                var total: Long = 0
                var count: Int

                while (inputStream.read(data).also { count = it } != -1) {
                    total += count.toLong()
                    outputStream.write(data, 0, count)
                }

                outputStream.flush()
                outputStream.close()
                inputStream.close()

                return outputFile
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return null
        }

        override fun onPostExecute(result: File?) {
            // Handle the downloaded file (result) as needed
            // For example, open it with an Intent or display a notification
        }
    }
}