import breeze.linalg._
import breeze.plot._
import com.github.tototoshi.csv._
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object bijalpractical15 {
  def main(args: Array[String]): Unit = {

    // Read CSV file
    val reader = CSVReader.open(new File("DailyDelhiClimateTest.csv"))
    val data = reader.allWithHeaders()
    reader.close()

    // Parse date and mean temperature
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    val parsedData = data.flatMap { row =>
      try {
        val date = LocalDate.parse(row("date"), dateFormatter)
        val meanTemp = row("meantemp").toDouble
        Some((date, meanTemp))
      } catch {
        case _: Throwable => None // Skip invalid rows
      }
    }.sortBy(_._1)

    // Prepare X (time index) and Y (mean temperature)
    val x = DenseVector((0 until parsedData.length).map(_.toDouble).toArray)
    val y = DenseVector(parsedData.map(_._2).toArray)

    // Plot graph
    val fig = Figure("Daily Climate Time Series")
    val plt = fig.subplot(0)

    plt += plot(x, y, name = "Mean Temperature", colorcode = "blue")

    plt.xlabel = "Time (Days)"
    plt.ylabel = "Mean Temperature (°C)"
    plt.title = "Daily Mean Temperature Over Time"

    fig.refresh()
  }
}