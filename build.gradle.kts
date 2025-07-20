plugins {
    application
    id("org.openjfx.javafxplugin") version "0.0.13"
}

group = "com.ittia.gds"
version = "1.0-SNAPSHOT"

val javaFxVersion = "21"
val platform = "linux"

repositories {
    mavenCentral()
}

javafx {
    version = javaFxVersion
    modules = listOf("javafx.controls", "javafx.fxml", "javafx.graphics", "javafx.base")
}

application {
    mainModule.set("GDSittia") // ← module-info.java와 일치
    mainClass.set("com.ittia.gds.EntryittiaFX")
}

dependencies {
    implementation("org.openjfx:javafx-base:$javaFxVersion:$platform")
    implementation("org.openjfx:javafx-controls:$javaFxVersion:$platform")
    implementation("org.openjfx:javafx-fxml:$javaFxVersion:$platform")
    implementation("org.openjfx:javafx-graphics:$javaFxVersion:$platform")
}
