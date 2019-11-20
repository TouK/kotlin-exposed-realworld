package io.realworld.article.infrastructure

import io.realworld.article.domain.Article
import io.realworld.article.domain.ArticleReadRepository
import io.realworld.article.domain.ArticleTable
import io.realworld.article.domain.ArticleTagsTable
import io.realworld.article.domain.ArticleWriteRepository
import io.realworld.article.domain.Slug
import io.realworld.article.domain.TagTable
import io.realworld.article.domain.from
import io.realworld.article.domain.insert
import io.realworld.article.domain.toArticleList
import io.realworld.shared.infrastructure.DatabaseExceptionTranslator
import io.realworld.shared.infrastructure.updateExactlyOne
import io.realworld.shared.refs.ArticleId
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

@Component
class SqlArticleReadRepository : ArticleReadRepository {

    companion object {
        val ArticleWithTags = (ArticleTable leftJoin ArticleTagsTable leftJoin TagTable)
    }

    override fun findAll() =
            ArticleWithTags
                    .selectAll()
                    .toArticleList()

    override fun findBy(articleId: ArticleId) =
            ArticleWithTags
                    .select { ArticleTable.id eq articleId }
                    .toArticleList()
                    .singleOrNull()

    override fun findBy(slug: Slug) =
            ArticleWithTags
                    .select { ArticleTable.slug eq slug }
                    .toArticleList()
                    .singleOrNull()
}

@Repository
class SqlArticleWriteRepository(
        private val et: DatabaseExceptionTranslator
) : ArticleWriteRepository {

    override fun create(article: Article) = et.translateExceptions {
        ArticleTable.insert(article)
    }

    override fun save(article: Article) {
        ArticleTable.updateExactlyOne({ ArticleTable.id eq article.id }) { it.from(article) }
    }

    override fun delete(articleId: ArticleId) {
        ArticleTagsTable.deleteWhere { ArticleTagsTable.articleId eq articleId }
        ArticleTable.deleteWhere { ArticleTable.id eq articleId }
    }
}
