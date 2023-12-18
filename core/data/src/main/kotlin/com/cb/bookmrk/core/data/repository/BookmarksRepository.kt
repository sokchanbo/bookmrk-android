package com.cb.bookmrk.core.data.repository

import com.cb.bookmrk.core.common.network.BookmrkDispatchers.IO
import com.cb.bookmrk.core.common.network.Dispatcher
import com.cb.bookmrk.core.database.dao.BookmarkDao
import com.cb.bookmrk.core.database.model.BookmarkEntity
import com.cb.bookmrk.core.model.data.WebContent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import java.net.URL
import javax.inject.Inject

interface BookmarksRepository {
    suspend fun createBookmark(url: String, collectionId: Long?)
}

class BookmarksRepositoryImpl @Inject constructor(
    private val bookmarkDao: BookmarkDao,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
) : BookmarksRepository {

    override suspend fun createBookmark(url: String, collectionId: Long?) {
        val webContent = extractWebContentFromUrl(url)
        bookmarkDao.insertOrReplaceBookmarkEntity(
            BookmarkEntity(
                title = webContent.title,
                imageUrl = webContent.iconUrl,
                collectionId = collectionId,
                link = webContent.url
            )
        )
    }

    private suspend fun extractWebContentFromUrl(url: String): WebContent =
        withContext(ioDispatcher) {
            val webContent = WebContent(
                iconUrl = "https://www.google.com/s2/favicons?domain=${URL(url).host}&sz=128"
            )

            val con = Jsoup.connect(url)
            val doc = con.userAgent("Mozilla").get()
            val ogTags = doc.select("meta[property^=og:]")
            ogTags.forEach { element ->
                when (element.attr("property")) {
                    //"og:image" -> webContent.favIconUrl = element.attr("content")
//                "og:description" -> webContent = element.attr("content")
                    "og:url" -> webContent.url = element.attr("content")
                    "og:title" -> webContent.title = element.attr("content")
                }
            }
            return@withContext webContent
        }
}
