package com.vkutuev.tosport.model

open class Vote(open val variants: List<String>)

class MapVote(override val variants: List<String>, val points: List<Pair<Double, Double>>) : Vote(variants)
