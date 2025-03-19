#!/bin/bash

# Check if Gradle is installed
if command -v gradle &> /dev/null; then
    echo "Starting Java Music Player..."
    gradle run
else
    echo "Gradle not found. Trying with the gradle wrapper..."
    if [ -f "./gradlew" ]; then
        echo "Starting Java Music Player..."
        ./gradlew run
    else
        echo "Creating Gradle wrapper..."
        gradle wrapper
        echo "Starting Java Music Player..."
        ./gradlew run
    fi
fi
