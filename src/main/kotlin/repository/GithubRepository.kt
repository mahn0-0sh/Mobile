class GithubRepository(private val api: GithubApiService) {

    private val userCache = mutableMapOf<String, Pair<GithubUser, List<Repo>>>()

    suspend fun getUserWithRepos(username: String): Pair<GithubUser, List<Repo>> {
        return userCache[username] ?: run {
            val user = api.getUser(username)
            val repos = api.getRepos(username)
            val result = Pair(user, repos)
            userCache[username] = result
            result
        }
    }

    fun getAllUsers(): List<GithubUser> = userCache.values.map { it.first }

    fun findUserByUsername(username: String): GithubUser? = userCache[username]?.first

    fun findUsersByRepoName(repoName: String): List<Pair<GithubUser, Repo>> {
        return userCache.values.flatMap { (user, repos) ->
            repos.filter { it.name.contains(repoName, ignoreCase = true) }
                .map { repo -> user to repo }
        }
    }
}
