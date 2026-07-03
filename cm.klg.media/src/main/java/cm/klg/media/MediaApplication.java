package cm.klg.media;

import cm.klg.media.config.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
  StorageProperties.class,
})
public class MediaApplication {

  public static void main(String[] args) {
    SpringApplication.run(MediaApplication.class, args);
  }
}
