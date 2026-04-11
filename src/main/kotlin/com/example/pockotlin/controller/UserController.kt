package com.example.pockotlin.controller

import com.example.pockotlin.model.entity.UserEntity
import com.example.pockotlin.service.UserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
) {

    @GetMapping
    fun getAllUsers(): List<UserEntity> = userService.findAll()

    @PostMapping
    fun createUser(@RequestBody user: UserEntity): UserEntity = userService.save(user)
}
