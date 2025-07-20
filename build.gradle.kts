plugins {
    application
}

repositories {
    mavenCentral()
}

val javaFxVersion = "21.0.1"
val platform = "linux" // Change to "win", "mac" as needed

dependencies {
    implementation("org.openjfx:javafx-base:$javaFxVersion:$platform")
    implementation("org.openjfx:javafx-controls:$javaFxVersion:$platform")
    implementation("org.openjfx:javafx-fxml:$javaFxVersion:$platform")
    implementation("org.openjfx:javafx-graphics:$javaFxVersion:$platform")
}

application {
    mainClass.set("com.ittia.gds.GDSittiaEntry")
}