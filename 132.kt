import org.kohsuke.github.GitHub

fun main(args: Array<String>) {
    // Ввод данных авторизации
    print("Введите имя пользователя: ")
    val username = readLine()!!

    print("Введите токен или пароль: ")
    val password = readLine()!!

    // Аутентификация в API GitHub
    val github = GitHub.connectUsingPassword(username, password)

    // Загрузка списка репозиториев организации
    val organization = github.getOrganization("имя организации")
    val repositories = organization.repositories

    // Создание списка участников организации
    val members = mutableListOf<String>()

    for (repository in repositories) {
        val contributors = repository.listContributors()
        for (contributor in contributors) {
            members.add(contributor.login)
        }
    }

    // Сортировка списка участников по количеству репозиториев
    val sortedMembers = members.groupingBy { it }.eachCount().entries.sortedByDescending { it.value }

    // Вывод отсортированного списка участников
    println("Участники организации, отсортированные по количеству репозиториев:")
    for ((index, member) in sortedMembers.withIndex()) {
        println("${index + 1}. ${member.key}: ${member.value}")
    }
}