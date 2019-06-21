package com.miron4dev.sandbox.jwt.model

import javax.persistence.*

@Entity(name = "USER_ACCOUNT")
class User(
        @Column(nullable = false)
        var accountId: Long,

        @Column(nullable = false)
        var name: String) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}

