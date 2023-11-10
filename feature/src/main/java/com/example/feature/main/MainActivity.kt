package com.example.feature.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.ScriptGroup.Input
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.common.adapter.AutoBindHolderFactory
import com.example.common.adapter.AutoBindListAdapter
import com.example.common.adapter.buildAdapter
import com.example.common.observe
import com.example.domain.model.Content
import com.example.domain.model.TextContent
import com.example.domain.model.filterIsDone
import com.example.feature.databinding.ActivityMainBinding
import com.example.feature.input.InputActivity
import com.example.feature.main.holder.TextContentViewHolder
import com.example.feature.main.holder.ToDoHolderEvent
import com.example.feature.main.model.TextContentUI
import com.example.feature.main.model.filterIsChecked
import com.example.feature.main.model.toTextContent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private val adapter by lazy { getContentListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUi()
        observeState()
        observeEffect()
    }

    override fun onStart() {
        super.onStart()
        viewModel.action(MainAction.OnStart)
    }

    private fun getContentListAdapter() : AutoBindListAdapter<TextContentUI> {
        return AutoBindHolderFactory<TextContentUI>().add(
            TextContentUI::class,
            TextContentViewHolder.DIFF,
            ToDoHolderEventImpl(),
            TextContentViewHolder.CREATOR,
        ).buildAdapter()
    }

    private fun setupUi() = with(binding) {
        recyclerView.adapter = adapter
        floatingButton.setOnClickListener{
            viewModel.action(com.example.feature.main.MainAction.OnClickAdd)
        }
    }

    private fun observeState() = observe(viewModel.state) { state->
        binding.title.text = state.title
        state.contentList.let { list->
            renderUIOnListDataChange(list)
            adapter.submitList(list)
        }
    }

    private fun renderUIOnListDataChange(list: List<TextContentUI>) {
        with(binding) {
            emptyTextView.isVisible = list.isEmpty()
            recyclerView.isVisible = list.isNotEmpty()
            val listOfItemsToDelete = list.filterIsChecked()
            groupForDeleteMultiple.isVisible = listOfItemsToDelete.isNotEmpty()
            deleteMultipleButton.setOnClickListener {
                viewModel.action(MainAction.OnClickMultipleDelete(listOfItemsToDelete))
            }
        }
    }

    private fun observeEffect() = observe(viewModel.effect) {effect->
        when (effect) {
            is MainEffect.NavigateToInputWithAddMode -> InputActivity.start(this@MainActivity)
            is MainEffect.NavigateToInputWithModifyMode -> InputActivity.start(this@MainActivity, effect.item.id)
            is MainEffect.ShowToast -> Toast.makeText(this@MainActivity, effect.text, Toast.LENGTH_SHORT).show()
        }
    }


    inner class ToDoHolderEventImpl: ToDoHolderEvent {
        override fun onClickEditButton(item:TextContentUI) {
            viewModel.action(MainAction.OnClickEdit(item))
        }

        override fun onClickDeleteButton(item: TextContentUI): Boolean {
            viewModel.action(MainAction.OnClickDelete(item))
            return false
        }

        override fun onItemCheck(item: TextContentUI) {
            viewModel.action(MainAction.OnItemCheck(item)) // item check 내역 수정
        }

        override fun onClickToggleIsDoneButton(item: TextContentUI) {
            viewModel.action(MainAction.OnClickToggleIsDoneButton(item))
        }
    }

}
