package cm.klg.media.adapter.rest.inbound;

import cm.klg.generated.map.adapter.rest.inbound.dto.DocumentDTO;
import cm.klg.generated.map.adapter.rest.inbound.dto.DocumentTypeDTO;
import cm.klg.generated.map.adapter.rest.inbound.dto.DocumentVisibilityDTO;
import cm.klg.media.application.usecase.CreateNewDocumentUseCase;
import cm.klg.media.application.usecase.GetDocumentUseCase;
import cm.klg.media.application.usecase.GetDocumentsUseCase;
import cm.klg.media.domain.document.Document;
import cm.klg.media.domain.document.DocumentName;
import cm.klg.media.domain.document.DocumentNameCannotBeBlankException;
import cm.klg.media.domain.document.DocumentSize;
import cm.klg.media.domain.document.DocumentType;
import cm.klg.media.domain.document.DocumentVisibility;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.web.multipart.MultipartFile;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RestMapper {

  default CreateNewDocumentUseCase.Command toCreateNewDocumentCommand(
      DocumentTypeDTO type, DocumentVisibilityDTO visibility, MultipartFile file) {
    try {
      var originalFilename = file.getOriginalFilename();
      if (originalFilename == null) {
        throw new DocumentNameCannotBeBlankException();
      }
      return new CreateNewDocumentUseCase.Command(
          DocumentName.from(originalFilename),
          DocumentSize.from(file.getSize()),
          DocumentVisibility.valueOf(visibility.name()),
          DocumentType.valueOf(type.name()),
          file.getInputStream());
    } catch (IOException _) {
      throw new CannotReadDocumentException();
    }
  }

  default DocumentDTO toDocumentDTO(GetDocumentUseCase.Response response) {
    return toDocumentDTO(response.metadata(), response.content());
  }

  default DocumentDTO toDocumentDTO(GetDocumentsUseCase.Response response) {
    return toDocumentDTO(response.metadata(), response.content());
  }

  private static DocumentDTO toDocumentDTO(Document metadata, InputStream content) {
    try {
      return new DocumentDTO()
          .id(metadata.getId().value())
          .isPublic(metadata.getVisibility() == DocumentVisibility.PUBLIC)
          .type(DocumentTypeDTO.valueOf(metadata.getType().name()))
          .createdAt(metadata.getCreatedAt().value())
          .name(metadata.getName().value())
          .size(BigDecimal.valueOf(metadata.getSize().value()))
          .content(content.readAllBytes());
    } catch (IOException _) {
      throw new CannotReadDocumentException();
    }
  }
}
