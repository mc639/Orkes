# Spreadsheet Backend

The Spreadsheet class is a simple backend for a spreadsheet application. It supports the following operations:

setCellValue(cellId: String, value: Object): Sets the value of a cell identified by its ID (e.g., "A1"). The value can be an integer or a formula in the form of a string (e.g., "=A1+B2").

getCellValue(cellId: String): int: Gets the value of a cell identified by its ID. If the cell contains a formula, the formula is evaluated and the result is returned.

```
Spreadsheet spreadsheet = new Spreadsheet();

spreadsheet.setCellValue("A1", 13);
spreadsheet.setCellValue("A2", 14);
int a1 = spreadsheet.getCellValue("A1"); // a1 == 13
spreadsheet.setCellValue("A3", "=A1+A2");
int a3 = spreadsheet.getCellValue("A3"); // a3 == 27
spreadsheet.setCellValue("A4", "=A1+A2+A3");
int a4 = spreadsheet.getCellValue("A4"); // a4 == 54

```


## Custom cell types

The Spreadsheet class can be extended to support custom cell types by implementing the Cell interface and registering a CellFactory for the custom cell type.

```
public interface Cell {
    int getValue(Set<String> evaluatedCells);
}

public interface CellFactory {
    Cell createCell(Object value);
}

```


To register a CellFactory, use the registerCellFactory method of the Spreadsheet class:

```
spreadsheet.registerCellFactory(MyCustomType.class, new MyCustomTypeCellFactory());
```

## Configuration

The Spreadsheet class can be configured using a properties file. The properties file should specify the fully-qualified class names of the CellFactory implementations for each supported cell value type.

cell value type -> cell factory class name
```
java.lang.Integer=com.example.ConstantCellFactory
java.lang.String=com.example.FormulaCellFactory
com.example.MyCustomType=com.example.MyCustomTypeCellFactory
```
To load the configuration, use the loadConfiguration method of the Spreadsheet class:

```
spreadsheet.loadConfiguration("spreadsheet.properties");
```

## Dependencies
The Spreadsheet class has the following dependencies:

Java SE 8 or later

## Assumptions

1. The cell IDs are always in the form of a capital letter followed by a number (e.g., "A1", "B2", "C3").
2. The formulas are always in the form of an equals sign followed by a series of cell references separated by + (e.g., "=A1+B2").
3. The only supported operators are addition.
4. The cell references in formulas are always valid and refer to cells that have already been set.
5. The cell values are always of a supported type (integer or string).
7. The Cell and CellFactory interfaces are correctly implemented by all custom cell types.

## Edge Cases

There are several edge cases and other situations that you should consider when implementing the Spreadsheet class:

1. Circular references: If a cell's value depends on the value of another cell, and that cell's value depends on the first cell's value, you have a circular reference. This can cause an infinite loop when trying to calculate the cell values. You should detect circular references and throw an exception or return an error value in this case.

2. Invalid cell references: If a formula contains a cell reference that does not exist, you should throw an exception or return an error value.

3. Invalid formulas: If a formula is syntactically invalid (e.g., missing an operator or operand), you should throw an exception or return an error value.

4. Missing cell values: If a cell's value depends on the value of another cell, and that cell has not been set yet, you should return an error value or throw an exception.

5. Thread safety: If your Spreadsheet class is accessed by multiple threads simultaneously, you will need to synchronize access to the cells map to prevent race conditions.

6. Scalability: If you expect the Spreadsheet class to be used by a large number of users or to handle a large number of cells, you will need to design the class for scalability. This might involve using a distributed data store or cache, or implementing optimized algorithms for calculating cell values.
