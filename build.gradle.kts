import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.extensions.DetektExtension

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.detekt) apply false
}

tasks.withType<Detekt>().configureEach {
    reports {
        xml.required.set(true)
        html.required.set(true)
        txt.required.set(true)
        sarif.required.set(true)
        md.required.set(true)
    }
}

subprojects {
    apply(plugin = rootProject.libs.plugins.detekt.get().pluginId)

    configure<DetektExtension> {
        config.setFrom("${rootProject.projectDir}/config/detekt/detekt.yml")
        buildUponDefaultConfig = true

        source.setFrom(
            files(
                "src/main/kotlin",
                "src/test/kotlin"
            )
        )
    }

    dependencies {
        add("detektPlugins", rootProject.libs.detekt.formatting.get())
    }
}
