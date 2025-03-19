@echo off
echo Starting Java Music Player...

rem Check if Gradle is installed
where gradle >nul 2>nul
if %ERRORLEVEL% == 0 (
    gradle run
) else (
    echo Gradle not found. Trying with the gradle wrapper...
    if exist gradlew.bat (
        call gradlew.bat run
    ) else (
        echo Creating Gradle wrapper...
        gradle wrapper
        call gradlew.bat run
    )
)

pause
