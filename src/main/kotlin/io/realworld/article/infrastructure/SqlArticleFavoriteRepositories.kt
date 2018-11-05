package io.realworld.article.infrastructure

import io.realworld.article.domain.ArticleFavoriteReadRepository
import io.realworld.article.domain.ArticleFavoriteWriteRepository
import io.realworld.shared.refs.ArticleId
import io.realworld.shared.refs.UserId
import io.realworld.user.infrastructure.UserTable
import io.realworld.user.infrastructure.userId
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.springframework.stereotype.Repository

object ArticleFavoriteTable : Table("article_favorites") {
    val userId = userId("user_id").references(UserTable.id)
    val articleId = articleId("article_id").references(ArticleTable.id)
}

@Repository
class SqlArticleFavoriteReadRepository : ArticleFavoriteReadRepository {
    override fun findBy(articleId: ArticleId): List<UserId> {
        return ArticleFavoriteTable
                .select { ArticleFavoriteTable.articleId eq articleId }
                .map { it[ArticleFavoriteTable.userId] }
    }
}

@Repository
class SqlArticleFavoriteWriteRepository : ArticleFavoriteWriteRepository {
    override fun addFor(articleId: ArticleId, userId: UserId) {
        ArticleFavoriteTable.insert {
            it[ArticleFavoriteTable.articleId] = articleId
            it[ArticleFavoriteTable.userId] = userId
        }
    }

    override fun removeFor(articleId: ArticleId, userId: UserId) {
        ArticleFavoriteTable.deleteWhere {
            (ArticleFavoriteTable.articleId eq articleId) and
                    (ArticleFavoriteTable.userId eq userId)
        }
    }

}
