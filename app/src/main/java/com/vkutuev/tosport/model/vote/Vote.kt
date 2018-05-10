package com.vkutuev.tosport.model.vote

open class Vote(open val variants: ArrayList<Pair<String, Int>>,
                open val respondingIds: MutableList<Int>)

class MapVote(variants: ArrayList<Pair<String, Int>>,
              respondingIds: MutableList<Int>,
              val points: List<Pair<Double, Double>>) : Vote(variants, respondingIds)
