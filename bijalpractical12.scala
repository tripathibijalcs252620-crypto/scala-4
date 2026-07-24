import com.github.tototoshi.csv._
import java.io.File

object  bijalpractical12{

  def main(args: Array[String]): Unit = {

    // Read CSV file
    val reader = CSVReader.open(new File("Mall_customers (1).csv"))
    val data = reader.allWithHeaders()
    reader.close()

    // Get unique Gender values
    val categories = data.map(_("Gender")).distinct.sorted

    // Perform One-Hot Encoding
    val newData = data.map { row =>

      val gender = row("Gender")

      val oneHot = categories.map { cat =>
        cat -> (if (cat == gender) "1" else "0")
      }.toMap

      // Remove Gender column and add encoded columns
      (row - "Gender") ++ oneHot
    }

    // Print Header
    val headers = newData.head.keys.toList
    println(headers.mkString(", "))

    // Print Encoded Data
    newData.foreach { row =>
      println(headers.map(row).mkString(", "))
    }

    // Write Encoded CSV
    val writer = CSVWriter.open(new File("Mall_Customers_Encoded.csv"))
    writer.writeRow(headers)

    newData.foreach { row =>
      writer.writeRow(headers.map(row))
    }

    writer.close()

    println("\nOne-Hot Encoded file written to Mall_Customers_Encoded.csv")
  }
}
