import com.otus.otuskotlin.homelibrary.backend.repo.tests.*
import com.otus.otuskotlin.homelibrary.repo.common.EdRepoInitialized
import com.otus.otuskotlin.homelibrary.repo.inmemory.EdRepoInMemory

class EdRepoInMemoryCreateTest : RepoEdCreateTest() {
    override val repo = EdRepoInitialized(
        EdRepoInMemory(randomUuid = { lockNew.asString() }),
        initObjects = initObjects,
    )
}

class EdRepoInMemoryDeleteTest : RepoEdDeleteTest() {
    override val repo = EdRepoInitialized(
        EdRepoInMemory(),
        initObjects = initObjects,
    )
}

class EdRepoInMemoryReadTest : RepoEdReadTest() {
    override val repo = EdRepoInitialized(
        EdRepoInMemory(),
        initObjects = initObjects,
    )
}

class EdRepoInMemorySearchTest : RepoEdSearchTest() {
    override val repo = EdRepoInitialized(
        EdRepoInMemory(),
        initObjects = initObjects,
    )
}

class EdRepoInMemoryUpdateTest : RepoEdUpdateTest() {
    override val repo = EdRepoInitialized(
        EdRepoInMemory(randomUuid = { lockNew.asString() }),
        initObjects = initObjects,
    )
}
