pluginManagement {
    repositories {
        maven {
            name = "Fabric"
            url = uri("https://maven.fabricmc.net/")
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "felt-api"
try {
    val strings: List<String> = java.nio.file.Files.lines(java.nio.file.Paths.get("excluded-folders.txt")).toList()
    val file = File(".")
    for (f in file.listFiles()!!) {
        if (f.isDirectory) {
            if (!strings.contains(f.name)) {
                include(f.name)
            }
        }
    }
} catch (e: Exception) {
    println("Submodule setup failed:")
    e.printStackTrace()
}
