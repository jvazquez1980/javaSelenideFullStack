FROM gradle:8.5-jdk11

USER root

# Install Node.js for Playwright (which handles Chrome/Chromium for ARM64)
RUN apt-get update && apt-get install -y \
    curl \
    && curl -fsSL https://deb.nodesource.com/setup_18.x | bash - \
    && apt-get install -y nodejs \
    && rm -rf /var/lib/apt/lists/*

# Install Playwright and its browsers (includes ARM64-compatible Chromium)
RUN npm install -g playwright@1.40.0 \
    && playwright install chromium \
    && playwright install-deps chromium

# Find and set Chromium path from Playwright
RUN CHROMIUM_PATH=$(find /root/.cache/ms-playwright -name "chrome" -type f | head -1) && \
    ln -s "$CHROMIUM_PATH" /usr/bin/chromium && \
    chmod +x /usr/bin/chromium

# Set Chrome environment variables for Selenide
ENV CHROME_BIN=/usr/bin/chromium
ENV webdriver.chrome.driver=/usr/bin/chromedriver

# Set working directory
WORKDIR /app

# Copy project files
COPY --chown=root:root . .

# Make gradlew executable
RUN chmod +x gradlew

# Default command
CMD ["./gradlew", "test", "-Dselenide.headless=true", "--no-daemon"]
