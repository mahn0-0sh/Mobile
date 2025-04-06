import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val repo = GithubRepository(RetrofitClient.api)

    while (true) {
        println(
            """
            |== منوی اصلی ==
            |1️⃣ دریافت اطلاعات کاربر گیت‌هاب
            |2️⃣ نمایش لیست کاربران ذخیره شده
            |3️⃣ جستجوی کاربر بر اساس نام
            |4️⃣ جستجو بر اساس نام ریپوزیتوری
            |5️⃣ خروج
        """.trimMargin()
        )

        when (readLine()?.trim()) {
            "1" -> {
                print("🔎 نام کاربری را وارد کنید: ")
                val username = readLine()!!
                try {
                    val (user, repos) = repo.getUserWithRepos(username)
                    println("✅ نام کاربری: ${user.login}")
                    println("👥 فالوورها: ${user.followers}, فالووینگ: ${user.following}")
                    println("📅 تاریخ ساخت اکانت: ${user.created_at}")
                    println("📁 ریپوزیتوری‌ها:")
                    repos.forEach { println("- ${it.name}: ${it.html_url}") }
                } catch (e: Exception) {
                    println("❌ خطا در دریافت اطلاعات: ${e.message}")
                }
            }

            "2" -> {
                println("📄 لیست کاربران ذخیره شده:")
                repo.getAllUsers().forEach {
                    println("- ${it.login}")
                }
            }

            "3" -> {
                print("🔍 نام کاربری برای جستجو: ")
                val username = readLine()!!
                val user = repo.findUserByUsername(username)
                if (user != null)
                    println("✅ کاربر یافت شد: ${user.login}")
                else
                    println("❌ کاربر در کش وجود ندارد.")
            }

            "4" -> {
                print("🔍 نام ریپوزیتوری برای جستجو: ")
                val repoName = readLine()!!
                val results = repo.findUsersByRepoName(repoName)
                if (results.isEmpty()) {
                    println("❌ موردی یافت نشد.")
                } else {
                    println("📁 نتایج جستجو:")
                    results.forEach { (user, repo) ->
                        println("- ${repo.name} (${repo.html_url}) از کاربر ${user.login}")
                    }
                }
            }

            "5" -> {
                println("👋 خروج از برنامه.")
                return@runBlocking
            }

            else -> println("❗ گزینه نامعتبر است.")
        }
    }
}
