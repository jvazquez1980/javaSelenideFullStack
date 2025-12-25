FROM gradle:7.6-jdk11

USER root

# Install Chrome dependencies and download Chrome for Testing
RUN apt-get update && apt-get install -y \
    wget \
    gnupg \
    unzip \
    libnss3 \
    libgconf-2-4 \
    libfontconfig1 \
    && rm -rf /var/lib/apt/lists/*

# Download and install Chrome for Testing (supports both ARM64 and AMD64)
RUN ARCH=$(dpkg --print-architecture) && \
    if [ "$ARCH" = "arm64" ]; then \
    CHROME_URL="https://storage.googleapis.com/chrome-for-testing-public/131.0.6778.204/linux64/chrome-linux64.zip" && \
    DRIVER_URL="https://storage.googleapis.com/chrome-for-testing-public/131.0.6778.204/linux64/chromedriver-linux64.zip"; \
    else \
    CHROME_URL="https://storage.googleapis.com/chrome-for-testing-public/131.0.6778.204/linux64/chrome-linux64.zip" && \
    DRIVER_URL="https://storage.googleapis.com/chrome-for-testing-public/131.0.6778.204/linux64/chromedriver-linux64.zip"; \
    fi && \
    wget -q "$CHROME_URL" -O /tmp/chrome.zip && \
    unzip /tmp/chrome.zip -d /opt/ && \
    ln -s /opt/chrome-linux64/chrome /usr/bin/chrome && \
    wget -q "$DRIVER_URL" -O /tmp/chromedriver.zip && \
    unzip /tmp/chromedriver.zip -d /opt/ && \
    ln -s /opt/chromedriver-linux64/chromedriver /usr/bin/chromedriver && \
    chmod +x /usr/bin/chrome /usr/bin/chromedriver && \
    rm /tmp/chrome.zip /tmp/chromedriver.zip

# Set Chrome environment variables for Selenide
ENV CHROME_BIN=/usr/bin/chrome
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
