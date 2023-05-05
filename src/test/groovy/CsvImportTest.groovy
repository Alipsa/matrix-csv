import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.QuoteMode
import org.junit.jupiter.api.Test
import se.alipsa.groovy.matrix.Matrix
import se.alipsa.groovy.matrixcsv.CsvImporter

import static org.junit.jupiter.api.Assertions.*

class CsvImportTest {

  @Test
  void importCsv() {
    URL url = getClass().getResource("/basic.csv")
    CSVFormat format = CSVFormat.Builder.create().setTrim(true).build()
    Matrix basic = CsvImporter.importCsv(url, format)
    assertEquals(4, basic.rowCount(), "Number of rows")
    assertEquals(['id', 'name', 'date', 'amount'], basic.columnNames(), "Column names")
    assertEquals(['4', 'Arne', '2023-07-01', '222.99'], basic.row(3), "lastRow")
  }
}
