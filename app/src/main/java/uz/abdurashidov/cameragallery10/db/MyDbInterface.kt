package uz.abdurashidov.cameragallery10.db

import uz.abdurashidov.cameragallery10.models.User

interface MyDbInterface {
    fun getAllUsers():ArrayList<User>
    fun addUser(user: User)
    fun editUser(user: User)
    fun deleteUser(user: User)
}