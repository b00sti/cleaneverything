package com.b00sti.cleaneverything.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.b00sti.cleaneverything.AppExecutors
import com.b00sti.cleaneverything.binding.FragmentDataBindingComponent
import com.b00sti.cleaneverything.databinding.SearchFragmentBinding
import com.b00sti.cleaneverything.di.Injectable
import com.b00sti.cleaneverything.ui.common.RetryCallback
import com.b00sti.cleaneverything.util.autoCleared
import com.b00sti.cleaneverything.vo.CategoryItem
import javax.inject.Inject
import com.b00sti.cleaneverything.R
import java.util.*

class SearchFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var appExecutors: AppExecutors

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<SearchFragmentBinding>()

    var adapter by autoCleared<CategoryListAdapter>()

    lateinit var searchViewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.search_fragment,
            container,
            false,
            dataBindingComponent
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        searchViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(SearchViewModel::class.java)
        binding.setLifecycleOwner(viewLifecycleOwner)
        val rvAdapter = CategoryListAdapter(
            dataBindingComponent = dataBindingComponent,
            appExecutors = appExecutors
        ) { repo ->
            val list = mutableListOf<CategoryItem>()
            searchViewModel.results.value?.data?.forEach {
                list.add(it.copy())
            }
            //list.addAll(searchViewModel.results.value?.data ?: listOf())
            list.find { it.title == repo.title }?.let {
                it.isDone = !repo.isDone
            }
            adapter.submitList(list)
            //adapter.notifyDataSetChanged()
            //navController().navigate(
            //SearchFragmentDirections.showRepo(repo.owner.login, repo.name)
            //)
        }
        initRecyclerView()

        //binding.query = searchViewModel.query
        binding.rvCategoryList.adapter = rvAdapter
        adapter = rvAdapter
        binding.items = searchViewModel.results

        //initSearchInputListener()

        binding.callback = object : RetryCallback {
            override fun retry() {
                //searchViewModel.refresh()
            }
        }
        //searchViewModel.searchLoc()

    }

    private fun initRecyclerView() {

        binding.rvCategoryList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
/*                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastPosition = layoutManager.findLastVisibleItemPosition()
                if (lastPosition == adapter.itemCount - 1) {
                    searchViewModel.loadNextPage()
                }*/
            }
        })
        //binding.searchResult = searchViewModel.results
        searchViewModel.results.observe(viewLifecycleOwner, Observer { result ->
            adapter.submitList(result?.data)
        })

/*        searchViewModel.loadMoreStatus.observe(viewLifecycleOwner, Observer { loadingMore ->
            if (loadingMore == null) {
                binding.loadingMore = false
            } else {
                binding.loadingMore = loadingMore.isRunning
                val error = loadingMore.errorMessageIfNotHandled
                if (error != null) {
                    //Snackbar.make(binding.loadMoreBar, error, Snackbar.LENGTH_LONG).show()
                }
            }
        })*/
    }

/*    private fun initSearchInputListener() {
        binding.input.setOnEditorActionListener { view: View, actionId: Int, _: KeyEvent? ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                doSearch(view)
                true
            } else {
                false
            }
        }
        binding.input.setOnKeyListener { view: View, keyCode: Int, event: KeyEvent ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                doSearch(view)
                true
            } else {
                false
            }
        }
    }

    private fun doSearch(v: View) {
        val query = binding.input.text.toString()
        // Dismiss keyboard
        dismissKeyboard(v.windowToken)
        searchViewModel.setQuery(query)
    }



    private fun dismissKeyboard(windowToken: IBinder) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(windowToken, 0)
    }*/

    /**
     * Created to be able to override in tests
     */
    //fun navController() = findNavController()
}