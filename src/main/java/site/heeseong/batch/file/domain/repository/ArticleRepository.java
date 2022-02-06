package site.heeseong.batch.file.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.heeseong.batch.file.domain.entity.ArticleEntity;

public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {
}
