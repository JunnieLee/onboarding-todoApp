package com.example.domain.useCase

import com.appmattus.kotlinfixture.kotlinFixture
import com.example.domain.model.TextContent
import com.example.domain.repository.ContentRepository
import com.example.domain.usecase.AddToDoUseCase
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
class AddToDoUseCaseTest {
    private lateinit var SUT: AddToDoUseCase

    @MockK
    private val repository: ContentRepository = mockk(relaxed = true)

    private val fixture = kotlinFixture()

    @BeforeAll
    fun setUp() {
        MockKAnnotations.init(this)
        SUT = AddToDoUseCase(repository)
    }

    @Test
    fun `_Todo 아이템 추가에 성공한 경우 추가에 성공한 TextContent 데이터를 리턴한다`() = runBlocking {
        // given
        val content = fixture<String>()
        val memo = fixture<String>()
        coEvery { repository.insert(any()) } returns Unit

        val expected = TextContent(content=content,memo=memo)

        // when
        val result = SUT.invoke(content, memo)

        // then
        assertThat(result).isEqualTo(expected)
    }

}