package com.cb.bookmrk.core.data.repository

import com.cb.bookmrk.core.common.network.BookmrkDispatchers.IO
import com.cb.bookmrk.core.common.network.Dispatcher
import com.cb.bookmrk.core.database.dao.BookmarkDao
import com.cb.bookmrk.core.database.model.BookmarkEntity
import com.cb.bookmrk.core.database.model.BookmarkWithCollection
import com.cb.bookmrk.core.database.model.asExternalModel
import com.cb.bookmrk.core.model.data.Bookmark
import com.cb.bookmrk.core.model.data.HomeScreenClickType
import com.cb.bookmrk.core.model.data.UpdateBookmark
import com.cb.bookmrk.core.model.data.WebContent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
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

    suspend fun updateBookmark(updateBookmark: UpdateBookmark)

    suspend fun moveBookmarksToTrash(bookmarks: List<Bookmark>)

    suspend fun moveBookmarkToTrash(id: Long)

    fun countBookmark(collectionId: Long): Flow<Int>

    fun countAllBookmark(): Flow<Int>

    fun countUnsortedBookmark(): Flow<Int>

    fun countTrashBookmark(): Flow<Int>

    fun getBookmarkById(id: Long): Flow<Bookmark?>

    fun getBookmarkWithCollection(id: Long): Flow<Bookmark?>

    suspend fun deleteBookmark(id: Long)

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

            HomeScreenClickType.Trash -> {
                bookmarkDao.getTrashBookmarkEntities()
                    .map { it.map(BookmarkWithCollection::asExternalModel) }
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
                createdDate = Date(),
                description = webContent.description
            )
        )
    }

    override suspend fun updateBookmark(updateBookmark: UpdateBookmark) {
        val bookmark = bookmarkDao.getBookmarkById(updateBookmark.id).first()
        if (bookmark != null) {
            bookmarkDao.updateBookmarkEntity(
                BookmarkEntity(
                    id = updateBookmark.id,
                    title = updateBookmark.title,
                    imageUrl = bookmark.imageUrl,
                    link = updateBookmark.link,
                    description = updateBookmark.description,
                    note = updateBookmark.note,
                    isAddedToFavorite = bookmark.isAddedToFavorite,
                    createdDate = bookmark.createdDate,
                    collectionId = updateBookmark.collectionId,
                    deletedDate = bookmark.deletedDate
                )
            )
        }
    }

    override suspend fun moveBookmarksToTrash(bookmarks: List<Bookmark>) {
        for (bookmark in bookmarks) {
            bookmarkDao.moveBookmarkEntityToTrash(
                id = bookmark.id,
                deletedDate = Date()
            )
        }
    }

    override suspend fun moveBookmarkToTrash(id: Long) {
        bookmarkDao.moveBookmarkEntityToTrash(
            id = id,
            deletedDate = Date()
        )
    }

    override fun countBookmark(collectionId: Long): Flow<Int> =
        bookmarkDao.countBookmarkEntity(collectionId)

    override fun countAllBookmark(): Flow<Int> = bookmarkDao.countAllBookmarkEntity()

    override fun countUnsortedBookmark(): Flow<Int> = bookmarkDao.countUnsortedBookmark()

    override fun countTrashBookmark(): Flow<Int> = bookmarkDao.countTrashBookmark()

    override fun getBookmarkById(id: Long): Flow<Bookmark?> =
        bookmarkDao.getBookmarkById(id).map { it?.asExternalModel() }

    override fun getBookmarkWithCollection(id: Long): Flow<Bookmark?> =
        bookmarkDao.getBookmarkWithCollection(id)
            .map { it?.asExternalModel() }

    override suspend fun deleteBookmark(id: Long) {
        bookmarkDao.deleteBookmarkEntity(id)
    }

    private suspend fun extractWebContentFromUrl(url: String): WebContent =
        withContext(ioDispatcher) {

            val con = Jsoup.connect(url)
            val doc = con.userAgent("Mozilla").get()
            val ogTags = doc.select("meta[property^=og:]")

            val description: String? =
                ogTags.firstOrNull { it.attr("property") == "og:description" }
                    ?.attr("content")

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
                url = url,
                description = description
            )
        }
}
