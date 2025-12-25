FROM gradle:8.5-jdk11

USER root

# Install Chromium and ChromeDriver (native ARM64 support)
RUN apt-get update && apt-get install -y \
    chromium \
    chromium-driver \
    wget \
    gnupg \
    unzip \
    libnss3 \
    libgconf-2-4 \
    libfontconfig1 \
    && rm -rf /var/lib/apt/lists/*

# Set Chrome environment variables for Selenide
ENV CHROME_BIN=/usr/bin/chromium
ENV CHROMEDRIVER=/usr/bin/chromedriver
ENV webdriver.chrome.driver=/usr/bin/chromedriver

# Set working directory
WORKDIR /app

# Copy project files
COPY --chown=root:root . .

# Make gradlew executable
RUN chmod +x gradlew

# Default command
CMD ["./gradlew", "test", "-Dselenide.headless=true", "--no-daemon"]
