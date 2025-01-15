package com.example.robotkingoftokyo.domain.model

import com.example.robotkingoftokyo.R

enum class DiceType {
    ONE,
    TWO,
    THREE,
    HEAL,
    ENERGY,
    SMASH
}

class Dice {
    private var die: Array<DiceType> = arrayOf(
        DiceType.ONE,
        DiceType.TWO,
        DiceType.THREE,
        DiceType.HEAL,
        DiceType.ENERGY,
        DiceType.SMASH
    )

    var locked: BooleanArray = booleanArrayOf(
        false, false, false,
        false, false, false,
    )

    private var dieImage: Map<DiceType, Int> = mapOf(
        DiceType.ONE to R.drawable.die_one,
        DiceType.TWO to R.drawable.die_two,
        DiceType.THREE to R.drawable.die_three,
        DiceType.HEAL to R.drawable.die_heal,
        DiceType.ENERGY to R.drawable.die_energy,
        DiceType.SMASH to R.drawable.die_smash,
    )

    fun getDie(): Array<DiceType> {
        return die
    }

    fun getDieImage(dieType: DiceType): Int {
        return dieImage[dieType]!!
    }

    fun rollDie() {
        for (i in die.indices) {
            if (!locked[i]) {
                die[i] = DiceType.entries.toTypedArray().random()
            }
        }
    }

    fun toggleLock(index: Int) {
        locked[index] = !locked[index]
    }

    fun copy(): Dice {
        val newDice = Dice()
        newDice.die = this.die.copyOf()
        newDice.locked = locked
        return newDice
    }
}