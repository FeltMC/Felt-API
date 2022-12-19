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
    val path = File(System.getProperty("user.dir"))
    val strings: List<String> = java.nio.file.Files.lines(File(path,"excluded-folders.txt").toPath()).toList()
    for (f in path.listFiles()!!) {
        if (f.isDirectory) {
            if (!strings.contains(f.name)) {
                include(f.name)
            }
        }
    }
} catch (e: Exception) {
    println("Submodule setup failed:")
    throw e
}
