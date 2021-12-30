rootProject.name = "m68kplugin"
include(":amiga")
// https://github.com/gradle/gradle/issues/4823#issuecomment-839238082
project(":amiga").projectDir = File("platforms/amiga")

include(":plugin-api")
project(":plugin-api").projectDir = File("api/plugin-api")
