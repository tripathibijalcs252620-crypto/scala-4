import breeze.linalg._
import breeze.plot._
import com.github.tototoshi.csv._
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object bijalpractical16 {
  def main(args: Array[String]): Unit = {


    val reader = CSVReader.open(new File("DailyDelhiClimateTest.csv"))
    val data = reader.allWithHeaders()
    reader.close()


    val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    val parsedData = data.flatMap { row =>
      try {
        val date = LocalDate.parse(row("date"), dateFormat)
        val meanTemp = row("meantemp").toDouble
        Some((date, meanTemp))
      } catch {
        case _: Throwable => None
      }
    }.sortBy(_._1)


    val x = DenseVector((0 until parsedData.length).map(_.toDouble).toArray)
    val y = DenseVector(parsedData.map(_._2).toArray)


    val fig = Figure("Daily Climate - Line + Scatter Plot")
    val plt = fig.subplot(0)


    plt += plot(x, y, name = "Mean Temperature Line", colorcode = "blue")


    plt += plot(x, y, '.', name = "Mean Temperature Points", colorcode = "red")

    plt.xlabel = "Time (Days)"
    plt.ylabel = "Mean Temperature (°C)"
    plt.title = "Daily Mean Temperature - Line + Scatter"

    fig.refresh()
  }
}