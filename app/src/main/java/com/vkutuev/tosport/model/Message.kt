package com.vkutuev.tosport.model

import com.vkutuev.tosport.model.vote.Vote

class Message(val text: String,
              val vote: Vote?,
              val sender: User) {
}