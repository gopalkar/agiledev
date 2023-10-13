package controllers

import models.User
class UserStore {

    var users = ArrayList<User>()
    private var lastId = 0
    private fun getId() = lastId++

    fun findAll(): List<User> {
        return users
    }

    fun create(user: User) {
        user.id = getId()
        users.add(user)
    }

    fun findOne(id: Int): User? {
        return users.find { p -> p.id == id }
    }

    fun findByGender(gender: Char): List<User> {
        return users.filter { p -> p.gender == gender }
    }
    fun delete(user: User?): Boolean {
        return users.remove(user)
    }

    fun updateUser(user: User): Boolean {
        val foundUser = findOne(user.id)
        if ( foundUser != null ) {
            foundUser.name = user.name
            foundUser.email = user.email
            foundUser.weight = user.weight
            foundUser.height = user.height
            foundUser.gender = user.gender
            return true
        }
        else
            return false
    }
}