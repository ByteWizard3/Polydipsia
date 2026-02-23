.PHONY: help client server data build clean setup-intellij setup-eclipse setup-vscode publish

# Default target
help:
	@echo "Available commands:"
	@echo "  make client         - Run the Minecraft client"
	@echo "  make server         - Run the Minecraft server"
	@echo "  make data           - Run data generators"
	@echo "  make build          - Build the mod jar"
	@echo "  make clean          - Clean the build directory"
	@echo "  make setup-intellij - Generate IntelliJ IDE run configurations"
	@echo "  make setup-eclipse  - Generate Eclipse IDE run configurations"
	@echo "  make setup-vscode   - Generate VS Code IDE run configurations"
	@echo "  make publish        - Publish the mod (maven-publish)"
	
set-java-version:
	sudo archlinux-java set java-17-openjdk

client:
	./gradlew runClient

server:
	./gradlew runServer

data:
	./gradlew runData

build:
	./gradlew build

clean:
	./gradlew clean

setup-intellij:
	./gradlew genIntellijRuns

setup-eclipse:
	./gradlew genEclipseRuns

setup-vscode:
	./gradlew genVSCodeRuns

publish:
	./gradlew publish
