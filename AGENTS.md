# Repository Guidelines

## Project Structure & Module Organization
`src/main/java/dev/bstk/jstatuscli` contains the CLI entrypoint (`App`, `JStatus`) and core flow. Add new user-facing commands under `src/main/java/dev/bstk/jstatuscli/commands` and integration-specific helpers under `src/main/java/dev/bstk/jstatuscli/tools` (for example `tools/codex` or `tools/gemini`). Use `src/main/resources` for runtime assets and `src/test/java` plus `src/test/resources` for tests. Treat `build/` as generated output and do not commit it.

## Build, Test, and Development Commands
Use the Gradle wrapper so contributors stay on the same toolchain.

- `./gradlew.bat test` or `./gradlew test`: run the JUnit 5 test suite.
- `./gradlew.bat shadowJar` or `./gradlew shadowJar`: build the distributable fat JAR at `build/libs/jstatus-cli.jar`.
- `./gradlew.bat clean build shadowJar`: full rebuild; this matches the flow used by `build-dev.sh`.
- `java -jar build/libs/jstatus-cli.jar`: start the CLI after building.

`build-dev.sh` and `start-dev.sh` are Bash helpers for Unix-like environments. On Windows, prefer `gradlew.bat`.

## Coding Style & Naming Conventions
This project targets Java 21. Follow the existing style: 2-space indentation, same-line braces, and concise methods. Keep package names lowercase, use PascalCase for classes/interfaces/enums, and UPPER_SNAKE_CASE for enum constants. Name command handlers `Command<Name>` and register new commands through the command enum and dispatch flow. No formatter or linter is configured, so match the surrounding code exactly.

## Testing Guidelines
Tests use JUnit 5 (`useJUnitPlatform()` in `build.gradle`). Place tests in mirrored packages under `src/test/java` and name files `*Test.java`. The repository already has the standard test directories, but no committed tests yet, so new behavior should include focused unit coverage for parsing, command dispatch, and integration helpers. No coverage threshold is enforced today.

## Commit & Pull Request Guidelines
Recent commits use short subjects such as `main - Config build`; follow the same `<scope> - <imperative summary>` pattern. Keep commits focused and buildable. Pull requests should include a short description, the commands you ran to verify the change, and sample terminal output when CLI behavior changes. Link the related task or issue when one exists.
