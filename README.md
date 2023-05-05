# matrix-csv
Comprehensive support for creating a Matrix from structured text files (CSV files) and writing a Matrix to
a CSV file in the format of choice.

Matrix-csv uses apache-commons csv to parse and create the csv file. Here is a simple example:

To use it in your project, add the following dependencies to your code
```groovy
implementation 'se.alipsa.groovy:matrix:1.1.1'
implementation 'se.alipsa.groovy:matrix-csv:1.0.0' 
```

```groovy
import org.apache.commons.csv.CSVFormat
import se.alipsa.groovy.matrix.Matrix
import se.alipsa.groovy.matrixcsv.CsvImporter

URL url = getClass().getResource("/basic.csv")
CSVFormat format = CSVFormat.Builder.create().setTrim(true).build()
Matrix basic = CsvImporter.importCsv(url, format)
```

For more advanced cases see [the apache commons csv user guid](https://commons.apache.org/proper/commons-csv/user-guide.html)

A slightly more complicated example:
give the following text file:
```
1;"Per";"2023-Apr-30";234,12
2;"Karin";"2023-May-10";345,22

3;"Tage";"2023-Jun-20";3489,01
4;"Arne";"2023-Jul-01";222,99
```

...we can parse as follows:
```groovy
import org.apache.commons.csv.CSVFormat
import se.alipsa.groovy.matrix.Matrix
import se.alipsa.groovy.matrixcsv.CsvImporter

URL url = getClass().getResource("/colonQuotesEmptyLine.csv")
CSVFormat format = CSVFormat.Builder.create()
    .setTrim(true)
    .setDelimiter(';')
    .setIgnoreEmptyLines(true)
    .setQuote('"' as Character)
    .setHeader('id', 'name', 'date', 'amount')
    .build()
Matrix matrix = CsvImporter.importCsv(url, format)
```

The resulting Matrix will be all string. To convert the content to the appropriate type, use the `convert` method e.g.
```groovy
Matrix table = matrix.convert(
  [
      "id": Integer, 
      "name": String, 
      "date": LocalDate, 
      "amount": BigDecimal
  ],
  DateTimeFormatter.ofPattern("yyyy-MMM-dd"),
  NumberFormat.getInstance(Locale.GERMANY)
)
//the following assertions then applies
assert 4 == table.rowCount() // Number of rows
assert ['id', 'name', 'date', 'amount'] == table.columnNames() // Column names
assert [4, 'Arne', LocalDate.parse('2023-07-01'), 222.99] == table.row(3) // last row
```

## Exporting a Matrix to a CSV file

```groovy
import se.alipsa.groovy.datasets.Dataset
import se.alipsa.groovy.matrixcsv.CsvExporter
import org.apache.commons.csv.CSVFormat

File file = File.createTempFile('mtcars', '.csv')
CsvExporter.exportToCsv(Dataset.mtcars(), CSVFormat.DEFAULT, file)
```
