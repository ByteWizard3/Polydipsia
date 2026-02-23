group "default" {
  targets = ["forge-dev"]
}

target "forge-dev" {
  context = "."
  dockerfile = "Dockerfile"

  tags = [
    "localhost:5000/minecraft/forge-1.20.1-dev:latest",
    "localhost:5000/minecraft/forge-1.20.1-dev:1.0"
  ]

  platforms = ["linux/amd64"]

  output = ["type=registry"]
}
