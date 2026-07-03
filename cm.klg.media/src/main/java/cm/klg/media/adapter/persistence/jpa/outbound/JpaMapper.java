package cm.klg.media.adapter.persistence.jpa.outbound;

import cm.klg.media.domain.document.Document;
import org.jspecify.annotations.NonNull;
import org.mapstruct.BeanMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface JpaMapper {

  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "id", source = "id.value")
  @Mapping(target = "size", source = "size.value")
  @Mapping(target = "name", source = "name.value")
  @Mapping(target = "status", source = "status")
  @Mapping(target = "visibility", source = "visibility")
  @Mapping(target = "type", source = "type")
  @Mapping(target = "createdAt", source = "createdAt.value")
  DocumentJpa toDocumentJpa(Document document);

  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "id.value", source = "id")
  @Mapping(target = "name.value", source = "name")
  @Mapping(target = "size.value", source = "size")
  @Mapping(target = "status", source = "status")
  @Mapping(target = "visibility", source = "visibility")
  @Mapping(target = "type", source = "type")
  @Mapping(target = "createdAt.value", source = "createdAt")
  Document toDocumentDomain(DocumentJpa jpa);

  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "id", source = "id.value")
  @Mapping(target = "size", source = "size.value")
  @Mapping(target = "name", source = "name.value")
  @Mapping(target = "status", source = "status")
  @Mapping(target = "visibility", source = "visibility")
  @Mapping(target = "type", source = "type")
  @Mapping(target = "createdAt", source = "createdAt.value")
  void toDocumentJpa(@MappingTarget DocumentJpa documentJpa, @NonNull Document document);
}
