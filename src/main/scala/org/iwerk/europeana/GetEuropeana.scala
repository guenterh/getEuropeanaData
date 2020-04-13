package org.iwerk.europeana


import java.io.FileOutputStream
import java.net.URL
import java.util.zip.{GZIPInputStream, GZIPOutputStream}

import scala.io.Source

  object GetEuropeana {

  def main(args: Array[String]) {


    val europeanaUrl = args(0)
    val outDir = args(1)
    for (filenumber <- 712 to 1027) {
      val filename = "part-" + "%05d".format(filenumber) + ".json.gz"
      val url = s"$europeanaUrl=$filename"

      println("fetching " + url)

      val zip = new GZIPInputStream(new URL(url).openStream())
      val it = Source.fromInputStream(zip).getLines()
      val fos = new FileOutputStream(s"$outDir$filename")
      val zos = new GZIPOutputStream(fos)

      for (line <- it) {
        zos.write((line + "\n").getBytes)
      }

      zos.flush()
      zos.close()

    }
  }
}
