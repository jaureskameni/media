package cm.klg.media.config;

import cm.klg.common.base.config.TransactionBeansProvider;
import cm.klg.common.base.transaction.DomainToHttpExceptionTranslator;
import cm.klg.media.adapter.configurations.ApplicationConfig;
import cm.klg.media.adapter.configurations.DefaultDomainToHttpExceptionTranslator;
import cm.klg.media.application.config.Configurations;
import cm.klg.media.application.outbound.DocumentRepository;
import cm.klg.media.application.outbound.DocumentStorageRepository;
import cm.klg.media.application.usecase.CreateNewDocumentUseCase;
import cm.klg.media.application.usecase.DeleteDocumentUseCase;
import cm.klg.media.application.usecase.GetDocumentUseCase;
import cm.klg.media.application.usecase.GetDocumentsUseCase;
import cm.klg.media.domain.document.DocumentSizePolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MediaBeans implements TransactionBeansProvider {

  @Bean
  public DomainToHttpExceptionTranslator domainToHttpExceptionTranslator() {
    return new DefaultDomainToHttpExceptionTranslator();
  }

  @Bean
  public Configurations configurations(StorageProperties storageProperties) {
    return new ApplicationConfig(storageProperties);
  }

  @Bean
  public DocumentSizePolicy documentSizePolicy(Configurations configurations) {
    return new DocumentSizePolicy(configurations.getMaxDocumentSizeInBytes());
  }

  @Bean
  public CreateNewDocumentUseCase createNewDocumentUseCase(
      DocumentRepository documentRepository,
      DocumentStorageRepository documentStorageRepository,
      DocumentSizePolicy documentSizePolicy) {
    return new CreateNewDocumentUseCase(
        documentRepository, documentStorageRepository, documentSizePolicy);
  }

  @Bean
  public GetDocumentUseCase getDocumentUseCase(
      DocumentRepository documentRepository, DocumentStorageRepository documentStorageRepository) {
    return new GetDocumentUseCase(documentRepository, documentStorageRepository);
  }

  @Bean
  public GetDocumentsUseCase getDocumentsUseCase(
      DocumentRepository documentRepository, DocumentStorageRepository documentStorageRepository) {
    return new GetDocumentsUseCase(documentRepository, documentStorageRepository);
  }

  @Bean
  public DeleteDocumentUseCase deleteDocumentUseCase(DocumentRepository documentRepository) {
    return new DeleteDocumentUseCase(documentRepository);
  }
}
