# Sudoku

A Sudoku puzzle game built with Java and Swing, refactored to follow modern Java coding standards, SOLID principles, and clean code practices.

## Features

- Seven difficulty levels: Most Easy, Very Easy, Easy, Normal, Hard, Very Hard, Most Hard
- Interactive 9×9 grid with digit input buttons
- Score tracking based on difficulty and completion time
- Elapsed time display on game completion

## Requirements

- Java 17 or higher
- Gradle (wrapper included)

## Build & Run

### Build the project

```bash
./gradlew build
```

### Run the application

```bash
./gradlew run
```

### Run unit tests

```bash
./gradlew test
```

Test reports are generated at `build/reports/tests/test/index.html`.

## Project Structure

```
src/
  main/java/com/sudoku/
    Main.java                        # Application entry point
    model/
      SudokuBoard.java               # Board state: solution and revealed cells
    generator/
      PuzzleGenerator.java           # Interface for puzzle generation
      SudokuGenerator.java           # Backtracking Sudoku generator
    selector/
      CellSelector.java              # Interface for cell visibility selection
      RandomCellSelector.java        # Randomly selects cells to reveal
    game/
      DifficultyLevel.java           # Enum: difficulty levels with visible cells and points
      SudokuGame.java                # Game logic: guessing, scoring, timer
    gui/
      SudokuGui.java                 # Swing GUI
  test/java/com/sudoku/
    generator/SudokuGeneratorTest.java
    selector/RandomCellSelectorTest.java
    game/DifficultyLevelTest.java
    game/SudokuGameTest.java
```

## Design Principles

- **SOLID**: Each class has a single responsibility; dependencies are injected via constructors; interfaces decouple high-level modules from implementations.
- **Principle of least privilege**: Fields and methods use the most restrictive access modifier possible (`private`, `final`).
- **Null-Safety**: All packages are annotated with `@NullMarked` (JSpecify) so non-null is the default, with `@Nullable` used where needed.
- **Clean code**: No commented-out code; meaningful names; small, focused methods.

## CI/CD

GitHub Actions runs on every push and pull request:
- Builds the project with Gradle
- Runs all unit tests
- Uploads test reports as build artifacts

See `.github/workflows/ci.yml` for the workflow definition.

## Dependencies

| Dependency | Version | Purpose |
|---|---|---|
| [JSpecify](https://jspecify.dev/) | 1.0.0 | Null-safety annotations |
| [JUnit Jupiter](https://junit.org/junit5/) | 5.11.4 | Unit testing |

## License

Original code by Nawaphon Isarathanachaikul (2017). Refactored 2026.
