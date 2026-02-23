FROM eclipse-temurin:17-jdk-jammy

# Use HTTPS mirrors (for networks blocking HTTP)
RUN sed -i 's|http://archive.ubuntu.com|https://archive.ubuntu.com|g' /etc/apt/sources.list && \
    sed -i 's|http://security.ubuntu.com|https://security.ubuntu.com|g' /etc/apt/sources.list

# Install required tools
RUN apt-get update && apt-get install -y \
    git \
    unzip \
    curl \
    wget \
    && rm -rf /var/lib/apt/lists/*

# Create jenkins user (UID 1000 recommended)
RUN useradd -m -d /home/jenkins -u 1000 -s /bin/bash jenkins

# Set Gradle cache inside jenkins home
ENV GRADLE_USER_HOME=/home/jenkins/.gradle

# Switch to jenkins user
USER jenkins
WORKDIR /home/jenkins

CMD ["bash"]
