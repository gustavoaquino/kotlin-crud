package br.com.kotlincrud.service

import br.com.kotlincrud.domain.User
import br.com.kotlincrud.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService (private var userRepository: UserRepository)  {

    fun findAll() : Iterable<User>{
        return this.userRepository.findAll();
    }

    fun findUserById(userId : Long) : Optional<User> {
        return this.userRepository.findById(userId)
    }

    fun save(user : User) : User{
        return this.userRepository.save(user)
    }

    fun deleteUserById(userId : Long){
        return this.userRepository.deleteById(userId)
    }
}