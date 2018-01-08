package com.github.cmonkey.morpheus

import java.io._
import java.net.{URL, URLConnection}
import java.util
import java.util.function.Predicate
import java.util.stream.Collectors
import java.util.zip.ZipInputStream

import scala.collection.mutable.ArrayBuffer;

object IOUtils {
    def toString(inputStream: InputStream, lineFilter: Predicate[String]): String = {

        val readerString = new BufferedReader(new InputStreamReader(inputStream))
                .lines().filter(lineFilter).collect(Collectors.joining("\n"));

        readerString

    }

    def filter(inputStream: InputStream, lineRegex: String): InputStream = {
        val filtered = toString(inputStream, (line) => line.matches(lineRegex))

        val byteArrayInputStream = new ByteArrayInputStream(filtered.getBytes())

        byteArrayInputStream
    }

    def get(urlAddress: String): InputStream = {
        val url = new URL(urlAddress)
        url.openStream()
    }

    def unzip(zippedInput: InputStream): Array[InputStream] = {
        val bytes = new Array[Byte](1024)
        var streams = ArrayBuffer[InputStream]()
        val zis = new ZipInputStream(zippedInput)

        var zipEntity = zis.getNextEntry

        while(zipEntity != null){
            val baos = new ByteArrayOutputStream(1024)

            var len = zis.read(bytes)
            while(len > 0){
              baos.write(bytes, 0, len)
              len = zis.read(bytes)
            }
            baos.close()

            streams += new ByteArrayInputStream(baos.toByteArray)

            zipEntity = zis.getNextEntry
        }

        streams.toArray
    }

}
