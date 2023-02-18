package com.inad.dogedex.api.dto

import com.inad.dogedex.model.User

class UserDTOMapper {

    fun fromUserDTOToUserDomain(userDTO: UserDTO): User {
        return User(
            id = userDTO.id,
            email = userDTO.email,
            authenticationToken = userDTO.authenticationToken
        )
    }
}