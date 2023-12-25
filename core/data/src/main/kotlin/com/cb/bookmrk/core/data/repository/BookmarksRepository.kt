package com.cb.bookmrk.core.data.repository

import com.cb.bookmrk.core.common.network.BookmrkDispatchers.IO
import com.cb.bookmrk.core.common.network.Dispatcher
import com.cb.bookmrk.core.database.dao.BookmarkDao
import com.cb.bookmrk.core.database.model.BookmarkEntity
import com.cb.bookmrk.core.database.model.BookmarkWithCollection
import com.cb.bookmrk.core.database.model.asExternalModel
import com.cb.bookmrk.core.model.data.Bookmark
import com.cb.bookmrk.core.model.data.HomeScreenClickType
import com.cb.bookmrk.core.model.data.WebContent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import java.net.URL
import java.util.Date
import javax.inject.Inject

interface BookmarksRepository {

    fun getBookmarks(
        homeScreenClickType: HomeScreenClickType,
        collectionId: Long?
    ): Flow<List<Bookmark>>

    suspend fun createBookmark(url: String, collectionId: Long?)

    suspend fun moveBookmarksToTrash(bookmarks: List<Bookmark>)
}

class BookmarksRepositoryImpl @Inject constructor(
    private val bookmarkDao: BookmarkDao,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
) : BookmarksRepository {

    override fun getBookmarks(
        homeScreenClickType: HomeScreenClickType,
        collectionId: Long?
    ): Flow<List<Bookmark>> =
        when (homeScreenClickType) {
            HomeScreenClickType.AllBookmarks -> {
                bookmarkDao.getBookmarkEntities()
                    .map { it.map(BookmarkWithCollection::asExternalModel) }
            }

            HomeScreenClickType.Unsorted -> {
                bookmarkDao.getBookmarkEntitiesWithEmptyCollection()
                    .map { it.map(BookmarkEntity::asExternalModel) }
            }

            HomeScreenClickType.Collection -> {
                bookmarkDao.getBookmarkEntitiesByCollectionId(collectionId!!)
                    .map { it.map(BookmarkEntity::asExternalModel) }
            }
        }

    override suspend fun createBookmark(url: String, collectionId: Long?) {
        val webContent = extractWebContentFromUrl(url)
        bookmarkDao.insertOrReplaceBookmarkEntity(
            BookmarkEntity(
                title = webContent.title,
                imageUrl = webContent.iconUrl,
                collectionId = collectionId,
                link = webContent.url,
                createdDate = Date()
            )
        )
    }

    override suspend fun moveBookmarksToTrash(bookmarks: List<Bookmark>) {
        for (bookmark in bookmarks) {
            bookmarkDao.moveBookmarkEntityToTrash(
                id = bookmark.id,
                deletedDate = Date()
            )
        }
    }

    private suspend fun extractWebContentFromUrl(url: String): WebContent =
        withContext(ioDispatcher) {

            val con = Jsoup.connect(url)
            val doc = con.userAgent("Mozilla").get()
            // val ogTags = doc.select("meta[property^=og:]")

            /*ogTags.forEach { element ->
                when (element.attr("property")) {
                    //"og:image" -> webContent.favIconUrl = element.attr("content")
//                "og:description" -> webContent = element.attr("content")
                    "og:url" -> webContent.url = element.attr("content")
                    "og:title" -> webContent.title = element.attr("content")
                }
            }*/
            return@withContext WebContent(
                title = doc.title(),
                iconUrl = "https://www.google.com/s2/favicons?domain=${URL(url).host}&sz=128",
                url = url
            )
        }
}
