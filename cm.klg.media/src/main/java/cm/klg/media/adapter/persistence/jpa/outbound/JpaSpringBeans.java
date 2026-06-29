package cm.klg.media.adapter.persistence.jpa.outbound;

import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackageClasses = DocumentJpa.class)
@EnableJpaRepositories(basePackageClasses = DocumentSpringRepository.class)
public class JpaSpringBeans {}
