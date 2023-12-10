package com.waynils.exoapplication.helper.extension

fun<E> MutableList<E>.removeAny(predicate: (E) -> Boolean) : Boolean {
    var modified = false
    with(iterator()) {
        while (hasNext()) {
            if (predicate(next())) {
                this.remove()
                modified = true
            }
        }
    }
   return modified
}