package cm.klg.media.adapter.persistence.jpa.outbound;

import cm.klg.media.application.outbound.DocumentRepository;
import cm.klg.media.domain.document.Document;
import cm.klg.media.domain.document.DocumentId;
import cm.klg.media.domain.document.DocumentNotFoundException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DocumentJpaRepository implements DocumentRepository {
  private final DocumentSpringRepository documentSpringRepository;
  private final JpaMapper jpaMapper;

  @Override
  public void insert(@NonNull Document document) {
    documentSpringRepository.save(jpaMapper.toDocumentJpa(document));
  }

  @Override
  public Document load(@NonNull DocumentId id) {
    return getById(id).map(jpaMapper::toDocumentDomain).orElseThrow(DocumentNotFoundException::new);
  }

  @Override
  public List<Document> loadAll() {
    return documentSpringRepository.findAll().stream().map(jpaMapper::toDocumentDomain).toList();
  }

  @Override
  public void save(@NonNull Document document) {
    documentSpringRepository.save(jpaMapper.toDocumentJpa(document));
  }

  @Override
  public void update(@NonNull Document document) {
    this.getById(document.getId())
        .ifPresent(
            documentJpa -> {
              jpaMapper.toDocumentJpa(documentJpa, document);
              documentSpringRepository.save(documentJpa);
            });
  }

  @NotNull
  private Optional<DocumentJpa> getById(@NotNull DocumentId id) {
    return documentSpringRepository.findById(id.value());
  }
}
