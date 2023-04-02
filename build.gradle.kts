import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.7.9"
	id("io.spring.dependency-management") version "1.0.15.RELEASE"
	id("com.github.johnrengelman.shadow") version "8.1.1"
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
}

group= "nicolas2lee.github.com"
version= "0.0.1-SNAPSHOT"
java.sourceCompatibility= JavaVersion.VERSION_11

repositories{
	mavenCentral()
}

dependencyManagement{
	imports{
		mavenBom("org.springframework.cloud:spring-cloud-function-dependencies:3.2.9")
	}
}

dependencies{
	implementation("org.springframework.cloud:spring-cloud-function-adapter-aws")
	implementation("org.springframework.cloud:spring-cloud-starter-function-webflux")
	implementation("org.springframework.boot:spring-boot-configuration-processor")
	compileOnly("com.amazonaws:aws-lambda-java-events:3.9.0")
	compileOnly("com.amazonaws:aws-lambda-java-core:1.1.0")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile>{
	kotlinOptions{
		freeCompilerArgs =listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test>{
	useJUnitPlatform()
}

tasks.withType<Jar>{
	enabled= true
	manifest{
		attributes["Main-Class"] = "nicolas2lee.github.com.springbootlambda.SpringbootLambdaApplication"
	}
}

tasks["assemble"].dependsOn("shadowJar")


tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>{
// classifier = "aws"
	dependencies{
		exclude(
			dependency("org.springframework.cloud:spring-cloud-function-web:3.2.9"))
	}
// Required for Spring
	mergeServiceFiles()
	append("META-INF/spring.handlers")
	append("META-INF/spring.schemas")
	append("META-INF/spring.tooling")
	transform(com.github.jengelman.gradle.plugins.shadow.transformers.PropertiesFileTransformer::class.java){
		paths=listOf("META-INF/spring.factories")
		mergeStrategy= "append"
	}
// archiveFileName.set("${project.name}-${project.version}.jar")
}