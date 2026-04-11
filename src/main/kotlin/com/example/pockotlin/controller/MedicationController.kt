package com.example.pockotlin.controller

import com.example.pockotlin.model.dto.MedicationDTO
import com.example.pockotlin.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/medications")
class MedicationController(private val userService: UserService) {

    @GetMapping
    fun getMedications(): List<MedicationDTO>? {
        return userService.getMedications()
    }
}
