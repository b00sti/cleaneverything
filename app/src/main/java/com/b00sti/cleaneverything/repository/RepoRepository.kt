package com.b00sti.cleaneverything.repository

import com.b00sti.cleaneverything.AppExecutors
import com.b00sti.cleaneverything.vo.CategoryItem
import com.b00sti.cleaneverything.vo.Resource
import com.b00sti.cleaneverything.vo.Status
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepoRepository @Inject constructor(
    private val appExecutors: AppExecutors
/*    private val db: GithubDb,
    private val repoDao: RepoDao,
    private val githubService: GithubService*/
) {

    fun loadSomething() = false

    fun search(result: (Resource<MutableList<CategoryItem>>) -> Unit) {
        Observable
            .timer(2000, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                val res = Resource(
                    Status.SUCCESS,
                    mutableListOf(
                        CategoryItem("Clean desk"),
                        CategoryItem("Clean car"),
                        CategoryItem("Clean cell phone", true)
                    ),
                    ""
                )
                result.invoke(res)
            }
    }
}