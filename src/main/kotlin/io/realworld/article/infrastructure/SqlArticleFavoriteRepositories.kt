package io.realworld.article.infrastructure

import io.realworld.article.domain.ArticleFavoriteReadRepository
import io.realworld.article.domain.ArticleFavoriteWriteRepository
import io.realworld.article.domain.ArticleTable
import io.realworld.article.domain.article_id
import io.realworld.shared.refs.ArticleId
import io.realworld.shared.refs.UserId
import io.realworld.user.domain.UserTable
import io.realworld.user.domain.user_id
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.springframework.stereotype.Repository

object ArticleFavoriteTable : Table("article_favorites") {
    val userId = user_id("user_id").references(UserTable.id)
    val articleId = article_id("article_id").references(ArticleTable.id)
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
    override fun create(articleId: ArticleId, userId: UserId) {
        ArticleFavoriteTable.insert {
            it[ArticleFavoriteTable.articleId] = articleId
            it[ArticleFavoriteTable.userId] = userId
        }
    }

    override fun delete(articleId: ArticleId, userId: UserId) {
        ArticleFavoriteTable.deleteWhere {
            (ArticleFavoriteTable.articleId eq articleId) and
                    (ArticleFavoriteTable.userId eq userId)
        }
    }

}
