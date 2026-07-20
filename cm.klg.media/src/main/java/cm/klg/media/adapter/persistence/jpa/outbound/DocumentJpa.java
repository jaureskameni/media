package cm.klg.media.adapter.persistence.jpa.outbound;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

@SuppressWarnings({"JpaDataSourceORMInspection", "NullAway.Init"})
@FieldNameConstants
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_document")
public class DocumentJpa {
  @Id
  @Column(name = "c_id")
  @NonNull
  UUID id;

  @Column(name = "c_name")
  @NonNull
  String name;

  @Column(name = "c_size")
  long size;

  @Column(name = "c_visibility")
  @NonNull
  String visibility;

  @Column(name = "c_type")
  @NonNull
  String type;

  @Column(name = "c_status")
  @NonNull
  String status;

  @Column(name = "c_created_at")
  @NonNull
  LocalDateTime createdAt;

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    DocumentJpa that = (DocumentJpa) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}
