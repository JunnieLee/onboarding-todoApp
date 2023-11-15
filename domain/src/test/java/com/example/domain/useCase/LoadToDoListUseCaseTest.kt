package com.example.domain.useCase

import com.appmattus.kotlinfixture.kotlinFixture
import com.example.domain.model.TextContent
import com.example.domain.repository.ContentRepository
import com.example.domain.usecase.AddToDoUseCase
import com.example.domain.usecase.LoadToDoListUseCase
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExperimentalCoroutinesApi
class LoadToDoListUseCaseTest {
    private lateinit var SUT: LoadToDoListUseCase

    @MockK
    private val repository: ContentRepository = mockk(relaxed = true)

    private val fixture = kotlinFixture()

    @BeforeAll
    fun setUp() {
        MockKAnnotations.init(this)
        SUT = LoadToDoListUseCase(repository)
    }

    @Test
    fun `_ToDoList 데이터 로드에 성공한 경우 로드한 리스트 데이터를 리턴한다`() = runBlocking {
        // given
        val expected = fixture<List<TextContent>>()
        coEvery { repository.loadList() } returns expected

        // when
        val result = SUT.invoke()

        // then
        assertThat(result).isEqualTo(expected)
    }

}