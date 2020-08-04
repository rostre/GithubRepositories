package ro.twodoors.githubrepolist.api

import retrofit2.http.GET
import ro.twodoors.githubrepolist.data.RepoResult

interface GithubService {

    //sample search
    @GET("/search/repositories?q=language:kotlin&sort=stars&order=desc&per_page=50")
    suspend fun searchRepositories(): RepoResult
}
