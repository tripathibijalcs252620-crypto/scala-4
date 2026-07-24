import breeze.linalg._
import breeze.plot._
import com.github.tototoshi.csv._
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object bijalpractical15 {

  def main(args: Array[String]): Unit = {

    // Read Apple stock data
    val reader = CSVReader.open(new File("stock market.csv"))
    val data = reader.allWithHeaders()
    reader.close()

    // Date format
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    // Parse Date and Close Price
    val parsedData = data.flatMap { row =>
      try {
        val date = LocalDate.parse(row("Date"), dateFormatter)
        val close = row("Close").toDouble
        Some((date, close))
      } catch {
        case _: Throwable => None
      }
    }.sortBy(_._1)

    // X-axis (Day Index)
    val x = DenseVector(
      (0 until parsedData.length).map(_.toDouble).toArray
    )

    // Y-axis (Close Price)
    val y = DenseVector(
      parsedData.map(_._2).toArray
    )

    // Create Figure
    val fig = Figure("Apple Stock Closing Price")

    val plt = fig.subplot(0)

    // Draw Line Plot
    plt += plot(
      x,
      y,
      name = "Close Price",
      colorcode = "blue"
    )

    // Labels
    plt.xlabel = "Time (Days)"
    plt.ylabel = "Close Price"
    plt.title = "Apple (AAPL) Closing Price Over Time"

    // Display Plot
    fig.refresh()
  }
}