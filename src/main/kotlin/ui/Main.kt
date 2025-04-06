import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val repo = GithubRepository(RetrofitClient.api)

    while (true) {
        println(
            """
            |Main Menu:
            |1 - Get User Info
            |2 - Show Saved User Info
            |3 - Search User by Name
            |4 - Search by Repository
            |5 - Exit
        """.trimMargin()
        )

        when (readLine()?.trim()) {
            "1" -> {
                print("🔎 Enter Username: ")
                val username = readLine()!!
                try {
                    val (user, repos) = repo.getUserWithRepos(username)
                    println("Username: ${user.login}")
                    println(" Followers: ${user.followers}, Followings: ${user.following}")
                    println(" Date: ${user.created_at}")
                    println(" Repositories:")
                    repos.forEach { println("- ${it.name}: ${it.html_url}") }
                } catch (e: Exception) {
                    println("❌ Error in finding user: ${e.message}")
                }
            }

            "2" -> {
                println(" List of saved users:")
                repo.getAllUsers().forEach {
                    println("- ${it.login}")
                }
            }

            "3" -> {
                print("Enter username to search: ")
                val username = readLine()!!
                //val user = repo.findUserByUsername(username)
                val (user, repos) = repo.getUserWithRepos(username)
                if (user != null) {
                    println("User found: ${user.login}")
                    println("Followings: ${user.following}")
                    println("Followers: ${user.followers}")
                    println("Date: ${user.created_at}")
                }
                else
                    println("User does not exist in cache memory")
            }

            "4" -> {
                print("Enter name of repository to search: ")
                val repoName = readLine()!!
                val results = repo.findUsersByRepoName(repoName)
                if (results.isEmpty()) {
                    println("Not found")
                } else {
                    println("Results:")
                    results.forEach { (user, repo) ->
                        println("- ${repo.name} (${repo.html_url}) from ${user.login}")
                    }
                }
            }

            "5" -> {
                println("Bye!")
                return@runBlocking
            }

            else -> println("Not an option!")
        }
    }
}
