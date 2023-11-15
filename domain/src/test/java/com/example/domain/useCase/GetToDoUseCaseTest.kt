package com.example.domain.useCase

import com.appmattus.kotlinfixture.kotlinFixture
import com.example.domain.model.TextContent
import com.example.domain.repository.ContentRepository
import com.example.domain.usecase.AddToDoUseCase
import com.example.domain.usecase.GetToDoUseCase
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
class GetToDoUseCaseTest {
    private lateinit var SUT: GetToDoUseCase

    @MockK
    private val repository: ContentRepository = mockk(relaxed = true)

    private val fixture = kotlinFixture()

    @BeforeAll
    fun setUp() {
        MockKAnnotations.init(this)
        SUT = GetToDoUseCase(repository)
    }

    @Test
    fun `_GetToDo에 성공한 경우 (해당 아이템이 있는 경우) get한 ToDo 아이템을 리턴한다`() = runBlocking {
        // given
        val id = fixture<Int>()
        val expected = TextContent(id=id, content=fixture<String>(),memo=fixture<String>())

        coEvery { repository.read(id) } returns expected

        // when
        val result = SUT.invoke(id)

        // then
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `_GetToDo에 실패한 경우 (해당 아이템이 없는 경우) null을 리턴한다`() = runBlocking {
        // given
        val id = fixture<Int>()
        val expected = null

        coEvery { repository.read(id) } returns expected

        // when
        val result = SUT.invoke(id)

        // then
        assertThat(result).isEqualTo(expected)
    }



}