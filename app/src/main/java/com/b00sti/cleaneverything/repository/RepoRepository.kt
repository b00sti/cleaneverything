package com.b00sti.cleaneverything.repository

import com.b00sti.cleaneverything.AppExecutors
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
}