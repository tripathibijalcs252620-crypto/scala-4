import breeze.linalg._
import breeze.plot._
import com.github.tototoshi.csv._
import java.io.File

object bijalpractical14{

  def main(args: Array[String]): Unit = {

    // Read CSV File
    val reader = CSVReader.open(new File("Mall_Customers (1).csv"))
    val data = reader.allWithHeaders()
    reader.close()

    // Extract Annual Income as a vector of doubles
    val annualIncome =
      DenseVector(data.map(_("Annual Income (k$)").toDouble).toArray)

    // Create Figure
    val fig = Figure("Histogram of Annual Income")

    // Different bin sizes
    val binSizes = List(5, 10, 20)

    for ((bins, idx) <- binSizes.zipWithIndex) {

      val plt = fig.subplot(1, binSizes.length, idx)

      plt += hist(annualIncome, bins)

      plt.title = s"Histogram with $bins bins"
      plt.xlabel = "Annual Income (k$)"
      plt.ylabel = "Frequency"
    }

    fig.refresh()
  }
}