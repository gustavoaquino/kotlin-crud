package br.com.kotlincrud.controller

import br.com.kotlincrud.domain.User
import br.com.kotlincrud.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(@Autowired private val userService: UserService) {

    @GetMapping
    fun getAllUsers(): List<User> =
        userService.findAll().toList()

    @PostMapping
    fun createUser(@RequestBody user: User): ResponseEntity<User> {
        val createdUser = userService.save(user)
        return ResponseEntity(createdUser, HttpStatus.CREATED)
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable("id") userId: Long): ResponseEntity<User> {
        val user = userService.findUserById(userId).orElse(null)
        return if (user != null) ResponseEntity(user, HttpStatus.OK)
        else ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @PutMapping("/{id}")
    fun updateUserById(@PathVariable("id") userId: Long, @RequestBody user: User): ResponseEntity<User> {

        val existingUser = userService.findUserById(userId).orElse(null) ?: return ResponseEntity(HttpStatus.NOT_FOUND)

        val updatedUser = existingUser.copy(name = user.name, email = user.email)
        userService.save(updatedUser)
        return ResponseEntity(updatedUser, HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    fun deleteUserById(@PathVariable("id") userId: Long): ResponseEntity<User> {
        var user = this.userService.findUserById(userId)

        if (user.isEmpty) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }
        userService.deleteUserById(userId)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}