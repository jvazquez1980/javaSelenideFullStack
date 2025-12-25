FROM gradle:7.6-jdk11

USER root

# Install Chromium and ChromeDriver (works on both ARM64 and AMD64)
RUN apt-get update && apt-get install -y \
    chromium \
    chromium-driver \
    && rm -rf /var/lib/apt/lists/*

# Set Chromium environment variables for Selenide
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
