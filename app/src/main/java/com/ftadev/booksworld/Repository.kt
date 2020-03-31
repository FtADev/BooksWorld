package com.ftadev.booksworld

class Repository(private val service: ApiService) {

    private suspend fun getBookBySubject() = service.getBooksBySubject()

//    suspend fun searchUsersWithPagination(query: String, page: Int, perPage: Int, sort: String): List<User> {
//        if (query.isEmpty()) return listOf()
//
//        val users = mutableListOf<User>()
//        val request = search(query, page, perPage, sort) // Search by name
//        request.items.forEach {
//            val user = getDetail(it.login) // Fetch detail for each user
//            val repositories = getRepos(user.login) // Fetch all repos for each user
//            val followers = getFollowers(user.login) // Fetch all followers for each user
//
//            user.totalStars =  repositories.map { it.numberStars }.sum()
//            user.followers = if (followers.isNotEmpty()) followers else listOf()
//
//            users.add(user)
//        }
//        return users
//    }
}