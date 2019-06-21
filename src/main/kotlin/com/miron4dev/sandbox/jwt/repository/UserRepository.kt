package com.miron4dev.sandbox.jwt.repository

import com.miron4dev.sandbox.jwt.model.User
import org.springframework.data.repository.PagingAndSortingRepository

interface UserRepository : PagingAndSortingRepository<User, Long> {

    fun existsByAccountId(accountId: Long): Boolean
}
