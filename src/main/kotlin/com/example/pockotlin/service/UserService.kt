package com.example.pockotlin.service

import com.example.pockotlin.model.entity.UserEntity
import com.example.pockotlin.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {

    fun findAll(): List<UserEntity> = userRepository.findAll()

    fun save(user: UserEntity): UserEntity = userRepository.save(user)
}
