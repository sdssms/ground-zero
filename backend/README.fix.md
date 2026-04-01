# How to fix the Backend Build Error
1. Run `brew install openjdk@21`
2. Create or edit `~/.gradle/gradle.properties` to point `org.gradle.java.home` to the Java 21 installation (`/opt/homebrew/opt/openjdk@21/libexec/openjdk.jdk/Contents/Home/`).
