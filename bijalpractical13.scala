import breeze.linalg._
import breeze.plot._
import com.github.tototoshi.csv._
import java.io.File

object bijalpractical13 {

  def main(args: Array[String]): Unit = {

    // Read CSV File
    val reader = CSVReader.open(new File("Mall_Customers (1).csv"))
    val data = reader.allWithHeaders()
    reader.close()

    // Separate data by Gender
    val male = data.filter(_("Gender") == "Male")
    val female = data.filter(_("Gender") == "Female")

    // Function to extract X and Y values
    def extractXY(rows: List[Map[String, String]]) = {

      val x = DenseVector(
        rows.map(_("Annual Income (k$)").toDouble).toArray
      )

      val y = DenseVector(
        rows.map(_("Spending Score (1-100)").toDouble).toArray
      )

      (x, y)
    }

    val (xMale, yMale) = extractXY(male)
    val (xFemale, yFemale) = extractXY(female)

    // Create Plot
    val fig = Figure()
    val plt = fig.subplot(0)

    plt.title = "Mall Customers Scatter Plot"
    plt.xlabel = "Annual Income (k$)"
    plt.ylabel = "Spending Score (1-100)"

    // Male Points (Blue)
    plt += plot(
      xMale,
      yMale,
      '.',
      name = "Male",
      colorcode = "blue"
    )

    // Female Points (Red)
    plt += plot(
      xFemale,
      yFemale,
      '.',
      name = "Female",
      colorcode = "red"
    )

    fig.refresh()
  }
}