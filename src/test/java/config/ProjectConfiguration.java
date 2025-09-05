package config;

import com.codeborne.selenide.Configuration;
import config.web.LaunchConfig;
import io.restassured.RestAssured;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;

public class ProjectConfiguration {
    private final LaunchConfig config;

    public ProjectConfiguration(LaunchConfig config) {
        this.config = config;
    }

    public void apiConfig() {
        RestAssured.baseURI = config.baseURL();
    }

    public void webConfig() {
        Configuration.baseUrl = config.baseURL();
        Configuration.browser = config.browserName().toString();
        Configuration.browserSize = config.browserSize();
        Configuration.browserVersion = config.browserVersion();
        Configuration.pageLoadStrategy = config.pageLoadStrategy();

        if (config.isRemote()) {
            Configuration.remote = config.remoteURL();

            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                    "enableVNC", true,
                    "enableVideo", true
            ));
            Configuration.browserCapabilities = capabilities;
        }
    }
}
