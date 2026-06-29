package cm.klg.media.adapter.persistence.jpa.outbound;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cm.klg.common.base.domain.CreatedAt;
import cm.klg.media.domain.document.Document;
import cm.klg.media.domain.document.DocumentId;
import cm.klg.media.domain.document.DocumentName;
import cm.klg.media.domain.document.DocumentNotFoundException;
import cm.klg.media.domain.document.DocumentSize;
import cm.klg.media.domain.document.DocumentStatus;
import cm.klg.media.domain.document.DocumentType;
import cm.klg.media.domain.document.DocumentVisibility;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DocumentJpaRepositoryTest {

  @Mock private DocumentSpringRepository documentSpringRepository;
  @Mock private JpaMapper jpaMapper;

  @InjectMocks private DocumentJpaRepository objectUnderTest;

  @Test
  void insert_shouldSaveMappedJpaEntity() {
    var document =
        Document.of(
            DocumentName.from("test.jpg"),
            DocumentSize.from(1024),
            DocumentVisibility.PUBLIC,
            DocumentType.IMAGE_JPEG);

    var documentJpa = new DocumentJpa();
    when(jpaMapper.toDocumentJpa(document)).thenReturn(documentJpa);

    objectUnderTest.insert(document);

    verify(jpaMapper).toDocumentJpa(document);
    verify(documentSpringRepository).save(documentJpa);
  }

  @Test
  void load_shouldReturnDocument_whenFound() {
    var documentId = DocumentId.generate();
    var documentJpa = new DocumentJpa();
    var document =
        Document.of(
            DocumentName.from("test.jpg"),
            DocumentSize.from(1024),
            DocumentVisibility.PUBLIC,
            DocumentType.IMAGE_JPEG);

    when(documentSpringRepository.findById(documentId.value()))
        .thenReturn(Optional.of(documentJpa));
    when(jpaMapper.toDocumentDomain(documentJpa)).thenReturn(document);

    var result = objectUnderTest.load(documentId);

    assertThat(result).isSameAs(document);
  }

  @Test
  void load_shouldThrowDocumentNotFoundException_whenNotFound() {
    var documentId = DocumentId.generate();

    when(documentSpringRepository.findById(documentId.value())).thenReturn(Optional.empty());

    assertThatThrownBy(() -> objectUnderTest.load(documentId))
        .isInstanceOf(DocumentNotFoundException.class);
  }

  @Test
  void loadAll_shouldReturnAllDocuments() {
    var documentJpa1 = new DocumentJpa();
    documentJpa1.setId(UUID.randomUUID());
    var documentJpa2 = new DocumentJpa();
    documentJpa2.setId(UUID.randomUUID());
    var document1 =
        Document.of(
            DocumentName.from("a.jpg"),
            DocumentSize.from(100),
            DocumentVisibility.PUBLIC,
            DocumentType.IMAGE_JPEG);
    var document2 =
        Document.of(
            DocumentName.from("b.png"),
            DocumentSize.from(200),
            DocumentVisibility.PRIVATE,
            DocumentType.IMAGE_PNG);

    when(documentSpringRepository.findAll()).thenReturn(List.of(documentJpa1, documentJpa2));
    when(jpaMapper.toDocumentDomain(documentJpa1)).thenReturn(document1);
    when(jpaMapper.toDocumentDomain(documentJpa2)).thenReturn(document2);

    var result = objectUnderTest.loadAll();

    assertThat(result).containsExactly(document1, document2);
  }

  @Test
  void save_shouldSaveMappedJpaEntity() {
    var document =
        Document.of(
            DocumentName.from("test.jpg"),
            DocumentSize.from(1024),
            DocumentVisibility.PUBLIC,
            DocumentType.IMAGE_JPEG);

    var documentJpa = new DocumentJpa();
    when(jpaMapper.toDocumentJpa(document)).thenReturn(documentJpa);

    objectUnderTest.save(document);

    verify(jpaMapper).toDocumentJpa(document);
    verify(documentSpringRepository).save(documentJpa);
  }

  @Test
  void update_shouldMapAndSave_whenDocumentExists() {
    var documentId = DocumentId.from(UUID.randomUUID());
    var document =
        new Document(
            documentId,
            DocumentName.from("test.jpg"),
            DocumentSize.from(1024),
            DocumentVisibility.PUBLIC,
            DocumentType.IMAGE_JPEG,
            DocumentStatus.ACTIVE,
            CreatedAt.from(LocalDateTime.now()));
    var existingJpa = new DocumentJpa();
    existingJpa.setId(documentId.value());

    when(documentSpringRepository.findById(documentId.value()))
        .thenReturn(Optional.of(existingJpa));

    objectUnderTest.update(document);

    verify(jpaMapper).toDocumentJpa(existingJpa, document);
    verify(documentSpringRepository).save(existingJpa);
  }

  @Test
  void update_shouldDoNothing_whenDocumentDoesNotExist() {
    var documentId = DocumentId.from(UUID.randomUUID());
    var document =
        new Document(
            documentId,
            DocumentName.from("test.jpg"),
            DocumentSize.from(1024),
            DocumentVisibility.PUBLIC,
            DocumentType.IMAGE_JPEG,
            DocumentStatus.ACTIVE,
            CreatedAt.from(LocalDateTime.now()));

    when(documentSpringRepository.findById(documentId.value())).thenReturn(Optional.empty());

    objectUnderTest.update(document);

    verify(jpaMapper, org.mockito.Mockito.never())
        .toDocumentJpa(org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any());
    verify(documentSpringRepository, org.mockito.Mockito.never())
        .save(org.mockito.ArgumentMatchers.any());
  }
}
